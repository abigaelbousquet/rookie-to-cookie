package edu.brown.cs.student.main.server.EndpointHandlers;

import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeUtilities;
import edu.brown.cs.student.main.server.RecipeData.MealPlan;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.RecipeVolumeException;
import edu.brown.cs.student.main.server.UserData.Profile;
import edu.brown.cs.student.main.server.UserData.ProfileUtilities;
import edu.brown.cs.student.main.server.storage.FirebaseUtilities;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.*;

public class GetRecipeHandler implements Route {

  public StorageInterface storageHandler;

  public GetRecipeHandler(StorageInterface storageHandler) {
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
      String recipeIdString = request.queryParams("recipeId");
      int recipeId = Integer.parseInt(recipeIdString);

      Map<String, Object> data = new HashMap<>();
      List<Map<String, Object>> mealPlans = this.storageHandler.getCollection(uid, "Mealplans");
      Recipe targetRecipe = null;

      // Check each meal plan for the recipe
      for (Map<String, Object> mealPlan : mealPlans) {
        Set<String> mealNames = mealPlan.keySet();
        assert mealNames.size() == 1;
        String mealName = String.valueOf(mealNames.toArray()[0]);
        String mealJson = FirebaseUtilities.MAP_STRING_OBJECT_JSON_ADAPTER.toJson(mealPlan);

        MealPlan plan = RecipeUtilities.deserializePlan(mealName, mealJson);
        List<Recipe> recipeList = plan.getRecipes();
        for (Recipe recipe : recipeList) {
          if (recipe != null && recipe.getId() == recipeId) {
            targetRecipe = recipe;
            data.put(String.valueOf(recipeId), recipe);
            break;
          }
        }
      }

      if (data.size() == 0) {
        throw new RecipeVolumeException("Recipe with given recipeId could not be found");
      }
      System.out.println("adding recipeId: " + recipeId + " for user: " + uid);

      responseMap.put("response_type", "success");
      responseMap.put("Recipe", targetRecipe);
    } catch (Exception e) {
      // error likely occurred in the storage handler
      e.printStackTrace();
      responseMap.put("response_type", "failure");
      responseMap.put("error", e.getMessage());
    }

    return FirebaseUtilities.MAP_STRING_OBJECT_JSON_ADAPTER.toJson(responseMap);
  }
}
