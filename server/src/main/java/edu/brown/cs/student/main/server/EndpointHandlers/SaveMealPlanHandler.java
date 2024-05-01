package edu.brown.cs.student.main.server.EndpointHandlers;

import edu.brown.cs.student.main.server.Server;
import edu.brown.cs.student.main.server.storage.FirebaseUtilities;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import spark.Request;
import spark.Response;
import spark.Route;

public class SaveMealPlanHandler implements Route {

  public StorageInterface storageHandler;

  public SaveMealPlanHandler(StorageInterface storageHandler) {
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
      String sundayDate = request.queryParams("dateOfSunday");
      responseMap.put(Server.userCurrPlan.get(uid).getDates().get(0), Server.userCurrPlan.get(uid));

      this.storageHandler.addDocument(uid, "Mealplans", sundayDate, responseMap);
      responseMap.put("response_type", "success");

    } catch (Exception e) {
      // error likely occurred in the storage handler
      e.printStackTrace();
      responseMap.put("response_type", "failure");
      responseMap.put("error", e.getMessage());
    }

    return FirebaseUtilities.MAP_STRING_OBJECT_JSON_ADAPTER.toJson(responseMap);
  }

  private List<Date> parseDates(String dayOfSunday) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    ArrayList<Date> dateList = new ArrayList<>();

    try {
      Date date = dateFormat.parse(dayOfSunday);
      Calendar calendar = Calendar.getInstance();
      dateList.add(date);
      for (int i = 0; i < 7; i++) {
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1); // Add one day

        Date nextDay = calendar.getTime();

        System.out.println("Today:" + date);
        System.out.println("Next Day: " + nextDay);
        date = nextDay;
        dateList.add(date);
      }
    } catch (ParseException e) {
      System.out.println("Error parsing date: " + e.getMessage());
    }
    return dateList;
  }
}
