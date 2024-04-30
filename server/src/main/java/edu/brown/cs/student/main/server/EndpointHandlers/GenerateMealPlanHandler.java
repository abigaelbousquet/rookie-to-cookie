package edu.brown.cs.student.main.server.EndpointHandlers;

import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeDatasource;
import edu.brown.cs.student.main.server.RecipeData.Datasource.SpoonacularRecipeSource;
import edu.brown.cs.student.main.server.RecipeData.MealPlan;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.MealPlanGenerator;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.Mode;
import edu.brown.cs.student.main.server.Server;
import edu.brown.cs.student.main.server.storage.FirebaseUtilities;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import spark.Request;
import spark.Response;
import spark.Route;

public class GenerateMealPlanHandler implements Route {

  public StorageInterface storageHandler;

  public GenerateMealPlanHandler(StorageInterface storageHandler) {
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
      // date of the Sunday of the week of the mealplan
      String dayOfSunday = request.queryParams("dayOfSunday");
      String daysOfWeekString = request.queryParams("daysOfWeek"); // array of days of week
      String modeString = request.queryParams("mode"); // pass in minimize or personalize
      String dietString = request.queryParams("diet");
      String intoleranceString = request.queryParams("intolerances");
      String expString = request.queryParams("exp");
      String servingString = request.queryParams("servings");
      String cuisineString = request.queryParams("cuisine");
      String maxReadyTimeString = request.queryParams("max_time");
      String excludeCuisineString = request.queryParams("exclude_cuisine");

      List<String> dateList = this.parseDates(dayOfSunday);

      Mode mode = null;

      int exp = Integer.parseInt(expString);
      int maxReadyTime = Integer.parseInt(maxReadyTimeString);
      int servings = Integer.parseInt(servingString);

      if (modeString.equals("minimize")) {
        mode = Mode.MINIMIZE_FOOD_WASTE;
      } else {
        mode = Mode.PERSONALIZED;
      }

      RecipeDatasource source = new SpoonacularRecipeSource();
      MealPlanGenerator planGenerator =
          new MealPlanGenerator(
              source,
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
              dateList);
      MealPlan plan = planGenerator.generatePlan();
      Server.userCurrPlan.put(uid, plan); // stores plan under uid inServer variable
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

  private List<String> parseDates(String dayOfSunday) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    ArrayList<String> dateList = new ArrayList<>();

    try {
      Date date = dateFormat.parse(dayOfSunday);
      Calendar calendar = Calendar.getInstance();
      dateList.add(String.valueOf(date));
      for (int i = 0; i < 6; i++) {
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1); // Add one day

        Date nextDay = calendar.getTime();

        System.out.println("Today:" + date);
        System.out.println("Next Day: " + nextDay);
        date = nextDay;
        dateList.add(String.valueOf(date));
      }
    } catch (ParseException e) {
      System.out.println("Error parsing date: " + e.getMessage());
    }
    return dateList;
  }
}
