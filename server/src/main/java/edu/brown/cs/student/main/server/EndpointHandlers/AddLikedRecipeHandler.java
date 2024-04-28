package edu.brown.cs.student.main.server.EndpointHandlers;

import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeUtilities;
import edu.brown.cs.student.main.server.RecipeData.MealPlan;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.RecipeVolumeException;
import edu.brown.cs.student.main.server.storage.FirebaseUtilities;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import java.util.*;
import java.util.concurrent.ExecutionException;
import spark.Request;
import spark.Response;
import spark.Route;

public class AddLikedRecipeHandler implements Route {

  public StorageInterface storageHandler;

  public AddLikedRecipeHandler(StorageInterface storageHandler) {
    this.storageHandler = storageHandler;
  }

  /**
   * Invoked when a request is made on this route's corresponding path e.g. '/hello'
   *
   * @param request The request object providing information about the HTTP request
   * @param response The response object providing functionality for modifying the response
   * @return The content to be set in the response
   */
  @Override
  public Object handle(Request request, Response response)
      throws ExecutionException, InterruptedException {
    Map<String, Object> responseMap = new HashMap<>();
    try {
      // collect parameters from the request
      String uid = request.queryParams("uid");
      String recipeId = request.queryParams("recipeId");

      int recipeIdInt = Integer.parseInt(recipeId);

      /**
       * essentially access the firebase datastore and add the recipe with the given id to the liked
       * recipe list
       */
      Map<String, Object> data = new HashMap<>();
      List<Map<String, Object>> mealPlans = this.storageHandler.getCollection(uid, "Mealplans");

      // Check each meal plan for the recipe
      for (Map<String, Object> mealPlan : mealPlans) {
        Set<String> mealNames = mealPlan.keySet();
        assert mealNames.size() == 1;
        String mealName = String.valueOf(mealNames.toArray()[0]);
        String mealJson = FirebaseUtilities.MAP_STRING_OBJECT_JSON_ADAPTER.toJson(mealPlan);

        MealPlan plan = RecipeUtilities.deserializePlan(mealName, mealJson);
        List<Recipe> recipeList = plan.getRecipes();
        for (Recipe recipe : recipeList) {
          if (recipe != null && recipe.getId() == recipeIdInt) {
            //
            data.put(recipeId, recipe);
            break;
          }
        }
      }

      if (data.size() == 0) {
        throw new RecipeVolumeException("No recipe in past mealplans found");
      }
      System.out.println("adding recipeId: " + recipeId + " for user: " + uid);

      // use the storage handler to add the document to the database
      this.storageHandler.addDocument(uid, "liked recipes", recipeId, data);

      responseMap.put("response_type", "success");

    } catch (Exception e) {
      // error likely occurred in the storage handler
      e.printStackTrace();
      responseMap.put("response_type", "failure");
      responseMap.put("error", e.getMessage());
    }

    return FirebaseUtilities.MAP_STRING_OBJECT_JSON_ADAPTER.toJson(responseMap);
  }
}
