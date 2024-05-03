package edu.brown.cs.student.main.server.EndpointHandlers;

import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeDatasource;
import edu.brown.cs.student.main.server.RecipeData.MealPlan;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.MealPlanGenerator;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.Mode;
import edu.brown.cs.student.main.server.storage.FirebaseUtilities;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import java.util.*;
import spark.Request;
import spark.Response;
import spark.Route;

/** Class to generate a mealplan and store it in the server based on the queried params */
public class GenerateMealPlanHandler implements Route {

  private final StorageInterface storageHandler;
  private final RecipeDatasource datasource;
  private final Map<String, MealPlan> userCurrPlan;

  public GenerateMealPlanHandler(
      StorageInterface storageHandler,
      RecipeDatasource datasource,
      Map<String, MealPlan> userCurrPlan) {
    this.storageHandler = storageHandler;
    this.datasource = datasource;
    this.userCurrPlan = userCurrPlan;
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
      String daysOfWeekString = request.queryParams("daysOfWeek"); // example (monday, tuesday)
      String modeString = request.queryParams("mode"); // pass in minimize or personalize
      String dietString = request.queryParams("diet");
      String intoleranceString = request.queryParams("intolerances");
      String servingString = request.queryParams("servings");
      String cuisineString = request.queryParams("cuisine");
      String maxReadyTimeString = request.queryParams("max_time");
      String excludeCuisineString = request.queryParams("exclude_cuisine");

      Mode mode = null;

      int maxReadyTime = Integer.parseInt(maxReadyTimeString);
      int servings = Integer.parseInt(servingString);

      if (modeString.equals("minimize")) {
        mode = Mode.MINIMIZE_FOOD_WASTE;
      } else {
        mode = Mode.PERSONALIZED;
      }

      MealPlanGenerator planGenerator =
          new MealPlanGenerator(
              this.datasource,
              mode,
              daysOfWeekString,
              servings,
              cuisineString,
              excludeCuisineString,
              dietString,
              intoleranceString,
              maxReadyTime,
              this.storageHandler,
              uid,
              null);
      MealPlan plan = planGenerator.generatePlan();
      this.userCurrPlan.put(uid, plan); // stores plan under uid inServer variable
      responseMap.put("response_type", "success");
      responseMap.put("Mealplan", plan);
    } catch (Exception e) {
      // error likely occurred in the storage handler
      e.printStackTrace();
      responseMap.put("response_type", "failure");
      responseMap.put("error", e.getMessage());
    }

    return FirebaseUtilities.MAP_STRING_OBJECT_JSON_ADAPTER.toJson(responseMap);
  }
}
