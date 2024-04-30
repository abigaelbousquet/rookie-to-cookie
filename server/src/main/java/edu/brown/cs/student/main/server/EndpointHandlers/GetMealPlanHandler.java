package edu.brown.cs.student.main.server.EndpointHandlers;

import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeUtilities;
import edu.brown.cs.student.main.server.RecipeData.MealPlan;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import edu.brown.cs.student.main.server.UserData.Profile;
import edu.brown.cs.student.main.server.UserData.ProfileUtilities;
import edu.brown.cs.student.main.server.storage.FirebaseUtilities;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import spark.Request;
import spark.Response;
import spark.Route;

import java.text.SimpleDateFormat;
import java.util.*;

public class GetMealPlanHandler implements Route {

  public StorageInterface storageHandler;

  public GetMealPlanHandler(StorageInterface storageHandler) {
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
      String dayOfSunday = request.queryParams("dayOfSunday"); //should be MM/DD/YYYY

      SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
      Date date = dateFormat.parse(dayOfSunday);
      String dateString = String.valueOf(date);

      Map<String, Object> data = new HashMap<>();
      List<Map<String, Object>> mealPlans = this.storageHandler.getCollection(uid, "Mealplans");

      for (Map<String, Object> mealPlan : mealPlans) {
        Set<String> mealNames = mealPlan.keySet();
        assert mealNames.size() == 1;
        String mealName = String.valueOf(mealNames.toArray()[0]);
        String mealJson = FirebaseUtilities.MAP_STRING_OBJECT_JSON_ADAPTER.toJson(mealPlan);

        MealPlan plan = RecipeUtilities.deserializePlan(mealName, mealJson);
        List<String> dateList = plan.getDates();
        for (String day : dateList) {
          if (day != null && day.equals(dateString)) {
            data.put(dateString, plan);
            break;
          }
        }
      }

      System.out.println("getting meals plans for user: " + uid);

      responseMap.put("response_type", "success");
      responseMap.put("Mealplan", data);
    } catch (Exception e) {
      // error likely occurred in the storage handler
      e.printStackTrace();
      responseMap.put("response_type", "failure");
      responseMap.put("error", e.getMessage());
    }

    return FirebaseUtilities.MAP_STRING_OBJECT_JSON_ADAPTER.toJson(responseMap);
  }
}
