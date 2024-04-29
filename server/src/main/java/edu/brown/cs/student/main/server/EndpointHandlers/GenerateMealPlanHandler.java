package edu.brown.cs.student.main.server.EndpointHandlers;

import edu.brown.cs.student.main.server.UserData.Profile;
import edu.brown.cs.student.main.server.UserData.ProfileUtilities;
import edu.brown.cs.student.main.server.storage.FirebaseUtilities;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
      String dayOfSunday = request.queryParams("dayOfSunday");
      String saveString = request.queryParams("save"); //parse for true or false

      boolean save = Boolean.parseBoolean(saveString);



      System.out.println("listing profile for user: " + uid);

      // get all the words for the user
      List<Map<String, Object>> vals = this.storageHandler.getCollection(uid, "Profile");
      ArrayList<Profile> users = new ArrayList<>();
      // convert the key,value map to just a list of the words.
      for (Map<String, Object> profileMap : vals) {
        String profileJson =
            FirebaseUtilities.MAP_STRING_OBJECT_JSON_ADAPTER.toJson(
                (Map<String, Object>) profileMap.get("User"));

        Profile user = ProfileUtilities.deserializeProfile(profileJson);
        users.add(user);
      }

      responseMap.put("response_type", "success");
      responseMap.put("User", users);
    } catch (Exception e) {
      // error likely occurred in the storage handler
      e.printStackTrace();
      responseMap.put("response_type", "failure");
      responseMap.put("error", e.getMessage());
    }

    return FirebaseUtilities.MAP_STRING_OBJECT_JSON_ADAPTER.toJson(responseMap);
  }
}
