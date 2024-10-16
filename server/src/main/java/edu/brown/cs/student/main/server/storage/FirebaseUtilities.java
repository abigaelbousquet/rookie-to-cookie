package edu.brown.cs.student.main.server.storage;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeUtilities;
import edu.brown.cs.student.main.server.RecipeData.MealPlan;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirebaseUtilities implements StorageInterface {
  private static final Moshi MOSHI = new Moshi.Builder().build();
  private static final Type MAP_STRING_OBJECT =
      Types.newParameterizedType(Map.class, String.class, Object.class);
  public static final JsonAdapter<Map<String, Object>> MAP_STRING_OBJECT_JSON_ADAPTER =
      MOSHI.adapter(MAP_STRING_OBJECT);

  public FirebaseUtilities() throws IOException {
    // Create /resources/ folder with firebase_config.json and
    // add your admin SDK from Firebase. see:
    // https://docs.google.com/document/d/10HuDtBWjkUoCaVj_A53IFm5torB_ws06fW3KYFZqKjc/edit?usp=sharing
    String workingDirectory = System.getProperty("user.dir");
    Path firebaseConfigPath =
        Paths.get(workingDirectory, "src", "main", "resources", "firebase_config.json");
    // ^-- if your /resources/firebase_config.json exists but is not found,
    // try printing workingDirectory and messing around with this path.

    FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath.toString());

    FirebaseOptions options =
        new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

    FirebaseApp.initializeApp(options);
  }

  @Override
  public List<Map<String, Object>> getCollection(String uid, String collection_id)
      throws InterruptedException, ExecutionException, IllegalArgumentException {
    if (uid == null || collection_id == null) {
      throw new IllegalArgumentException("getCollection: uid and/or collection_id cannot be null");
    }
    // gets all documents in the collection 'collection_id' for user 'uid'

    Firestore db = FirestoreClient.getFirestore();
    // 1: Make the data payload to add to your collection
    CollectionReference dataRef = db.collection("users").document(uid).collection(collection_id);

    // 2: Get pin documents
    QuerySnapshot dataQuery = dataRef.get().get();

    // 3: Get data from document queries
    List<Map<String, Object>> data = new ArrayList<>();
    for (QueryDocumentSnapshot doc : dataQuery.getDocuments()) {
      data.add(doc.getData());
    }

    return data;
  }

  /**
   * A method to add a document with the given parameters to Firebase datastore
   *
   * @param uid the unique user identifer
   * @param collection_id the name of the collection to add the document too
   * @param doc_id the name of the document
   * @param data the data to add to the datastore
   * @throws IllegalArgumentException
   */
  @Override
  public void addDocument(String uid, String collection_id, String doc_id, Map<String, Object> data)
      throws IllegalArgumentException {
    if (uid == null || collection_id == null || doc_id == null || data == null) {
      throw new IllegalArgumentException(
          "addDocument: uid, collection_id, doc_id, or data cannot be null");
    }
    // adds a new document 'doc_name' to colleciton 'collection_id' for user 'uid'
    // with data payload 'data'.

    Firestore db = FirestoreClient.getFirestore();
    // 1: Get a ref to the collection that you created
    CollectionReference collectionRef =
        db.collection("users").document(uid).collection(collection_id);

    // 2: Write data to the collection ref
    collectionRef.document(doc_id).set(data);
  }

  /**
   * Clears all of the collections for a given user
   *
   * @param uid unique user identifier
   * @throws IllegalArgumentException
   */
  @Override
  public void clearUser(String uid) throws IllegalArgumentException {
    if (uid == null) {
      throw new IllegalArgumentException("removeUser: uid cannot be null");
    }
    try {
      // removes all data for user 'uid'
      Firestore db = FirestoreClient.getFirestore();
      // 1: Get a ref to the user document
      DocumentReference userDoc = db.collection("users").document(uid);
      // 2: Delete the user document
      deleteDocument(userDoc);
    } catch (Exception e) {
      System.err.println("Error removing user : " + uid);
      System.err.println(e.getMessage());
    }
  }

  /**
   * Clears the recipe of a given recipe id from the given collection
   *
   * @param uid user identifier
   * @param path path of the collection id
   * @param recipeId id of the recipe to delete
   * @throws IllegalArgumentException
   */
  @Override
  public void clearRecipes(String uid, String path, String recipeId)
      throws IllegalArgumentException {
    if (uid == null) {
      throw new IllegalArgumentException("removeUser: uid cannot be null");
    }
    try {
      // removes all data for user 'uid'
      Firestore db = FirestoreClient.getFirestore();

      DocumentReference dislikedRecipesRef =
          db.collection("users").document(uid).collection(path).document(recipeId);

      deleteDocument(dislikedRecipesRef);
    } catch (Exception e) {
      System.err.println("Error removing user : " + uid);
      System.err.println(e.getMessage());
    }
  }

  /**
   * Method to delete the passed in document from firestore
   *
   * @param doc to delete
   */
  private void deleteDocument(DocumentReference doc) {
    // for each subcollection, run deleteCollection()
    Iterable<CollectionReference> collections = doc.listCollections();
    for (CollectionReference collection : collections) {
      deleteCollection(collection);
    }
    // then delete the document
    doc.delete();
  }

  /**
   * Method to delete a collection from firebase
   *
   * @param collection to delete
   */
  private void deleteCollection(CollectionReference collection) {
    try {

      // get all documents in the collection
      ApiFuture<QuerySnapshot> future = collection.get();
      List<QueryDocumentSnapshot> documents = future.get().getDocuments();

      // delete each document
      for (QueryDocumentSnapshot doc : documents) {
        doc.getReference().delete();
      }

    } catch (Exception e) {
      System.err.println("Error deleting collection : " + e.getMessage());
    }
  }

  /**
   * Deserializes a user's liked or disliked recipes from firebase into the equivalent List of
   * Recipe objects.
   *
   * @param firebaseData the raw data from firebase
   * @return the List of Recipes corresponding to the deserialized version of firebaseData
   * @throws IllegalArgumentException if not passed
   * @throws IOException
   */
  public static List<Recipe> convertLikedOrDislikedRecipes(List<Map<String, Object>> firebaseData)
      throws IllegalArgumentException, IOException {
    List<Recipe> recipes = new ArrayList<>();
    for (Map<String, Object> rawRecipes : firebaseData) {
      for (Object recipeData : rawRecipes.values()) {
        if (!(recipeData instanceof Map)) {
          throw new IllegalArgumentException(
              "Attempted to convert non-map into a map. "
                  + "Liked or disliked recipes from firebase should be nested maps. Rawjson: "
                  + firebaseData);
        }
        Map<String, Object> recipeDataAsMap = (Map<String, Object>) recipeData;

        // Deserialize each map entry into a Recipe object
        String recipeJson =
            FirebaseUtilities.MAP_STRING_OBJECT_JSON_ADAPTER.toJson(recipeDataAsMap);
        Recipe recipe = RecipeUtilities.deserializeRecipe(recipeJson);
        if (recipe != null) {
          recipes.add(recipe);
        }
      }
    }
    return recipes;
  }

  /**
   * Method to add the given meal plan to the firestore database.
   *
   * @param uid
   * @param firebaseData
   * @param plan
   */
  public static void addToFirebase(String uid, StorageInterface firebaseData, MealPlan plan) {
    Map<String, Object> data = new HashMap<>();
    // also need a way to find date range, for now just gonna call mealplan-1, etc
    int mealCount = 0;
    String planId = "default";
    try {
      mealCount = firebaseData.getCollection(uid, "Mealplans").size();
      planId = "mealplan-" + mealCount;
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    data.put(planId, plan);

    firebaseData.addDocument(uid, "Mealplans", planId, data);
  }
}
