package edu.brown.cs.student.main.server.EndpointHandlers;

import edu.brown.cs.student.main.server.RecipeData.MealPlan;
import edu.brown.cs.student.main.server.storage.FirebaseUtilities;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import spark.Request;
import spark.Response;
import spark.Route;

/** Class to save the mealplan associated with the user to the firebase datastore */
public class SaveMealPlanHandler implements Route {

  public final StorageInterface storageHandler;
  private final Map<String, MealPlan> userCurrPlan;

  public SaveMealPlanHandler(StorageInterface storageHandler, Map<String, MealPlan> userCurrPlan) {
    this.storageHandler = storageHandler;
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
      String mondayDate = request.queryParams("dateOfMonday");
      List<String> dateList = this.parseDates(mondayDate);
      this.userCurrPlan.get(uid).setDates(dateList);

      if (this.userCurrPlan.get(uid) == null) {
        throw new RuntimeException("No meal plans have been generated for the current user");
      }

      responseMap.put(dateList.get(0), this.userCurrPlan.get(uid));

      this.storageHandler.addDocument(uid, "Mealplans", dateList.get(0), responseMap);
      responseMap.put("response_type", "success");

    } catch (Exception e) {
      // error likely occurred in the storage handler
      e.printStackTrace();
      responseMap.put("response_type", "failure");
      responseMap.put("error", e.getMessage());
    }

    return FirebaseUtilities.MAP_STRING_OBJECT_JSON_ADAPTER.toJson(responseMap);
  }

  /**
   * Class to parse the given String of MM/DD/YYYY into all the dates of the week in the format
   * MM-DD-YYYY
   *
   * @param dayOfMonday is the String of the day
   * @return a list of all of the dates
   */
  private List<String> parseDates(String dayOfMonday) {
    System.out.println("dayOfMonday: " + dayOfMonday);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat dateFormatDash = new SimpleDateFormat("MM-dd-yyyy");

    ArrayList<String> dateList = new ArrayList<>();

    try {
      Date date = dateFormat.parse(dayOfMonday);
      Calendar calendar = Calendar.getInstance();
      dateList.add(dateFormatDash.format(date));
      //      dateList.add(String.valueOf(date));
      for (int i = 0; i < 6; i++) {
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1); // Add one day

        Date nextDay = calendar.getTime();

        System.out.println("Today:" + date);
        System.out.println("Next Day: " + nextDay);
        date = nextDay;
        dateList.add(dateFormatDash.format(date));
      }
    } catch (ParseException e) {
      System.out.println("Error parsing date: " + e.getMessage());
    }
    return dateList;
  }
}
