package edu.brown.cs.student.main.server.EndpointHandlers;

import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeUtilities;
import edu.brown.cs.student.main.server.storage.FirebaseUtilities;
import edu.brown.cs.student.main.server.storage.StorageInterface;

import java.util.*;

import spark.Request;
import spark.Response;
import spark.Route;

public class ListDislikedRecipesHandler implements Route {

  public StorageInterface storageHandler;

  public ListDislikedRecipesHandler(StorageInterface storageHandler) {
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
  public Object handle(Request request, Response response) {
    Map<String, Object> responseMap = new HashMap<>();
    try {
      String uid = request.queryParams("uid");

      System.out.println("listing liked recipes for user: " + uid);

      // get all the words for the user
      List<Map<String, Object>> vals = this.storageHandler.getCollection(uid, "disliked recipes");
      ArrayList<Recipe> recipes = new ArrayList<>();
      // convert the key,value map to just a list of the words.
      for (Map<String, Object> recipeMap : vals) {
        Set<String> keys = recipeMap.keySet();
        for (String key : keys)
        {
          String recipeJson = FirebaseUtilities.MAP_STRING_OBJECT_JSON_ADAPTER.toJson((Map<String, Object>) recipeMap.get(key));

          Recipe recipe = RecipeUtilities.deserializeRecipe(recipeJson);
          recipes.add(recipe);
        }
      }

      responseMap.put("response_type", "success");
      responseMap.put("Recipes", recipes);
    } catch (Exception e) {
      // error likely occurred in the storage handler
      e.printStackTrace();
      responseMap.put("response_type", "failure");
      responseMap.put("error", e.getMessage());
    }

    return FirebaseUtilities.MAP_STRING_OBJECT_JSON_ADAPTER.toJson(responseMap);
  }
}
