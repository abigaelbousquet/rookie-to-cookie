package edu.brown.cs.student.main.server.Endpoints;

import edu.brown.cs.student.main.server.storage.StorageInterface;
import edu.brown.cs.student.main.server.storage.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class AddUserHandler implements Route {

  public StorageInterface storageHandler;

  public AddUserHandler(StorageInterface storageHandler) {
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
      // collect parameters from the request
      String uid = request.queryParams("uid");
      String name = request.queryParams("name");
      String exp = request.queryParams("exp");
      String diet = request.queryParams("diet");
      String intolerances = request.queryParams("intolerances");
      int experience = 0;
      ArrayList<String> intoleranceArray = new ArrayList<>();
      ArrayList<String> dietArray = new ArrayList<>();

      // convert exp to a num
      try {
        experience = Integer.parseInt(exp);
      } catch (Exception e) {
        System.out.println("experience is not a num");
      }

      // convert intolerance string to array
      try {
        // Remove square brackets
        String content = intolerances.substring(1, intolerances.length() - 1);

        // Split the remaining string by commas
        String[] parts = content.split(",");

        // Trim leading and trailing spaces from each element and add them to ArrayList
        for (String part : parts) {
          intoleranceArray.add(part.trim());
        }
      } catch (Exception e) {
        System.out.println("Intolerance array could not be parsed");
      }

      // convert diet string to array
      try {
        String content = diet.substring(1, diet.length() - 1);

        // Split the remaining string by commas
        String[] parts = content.split(",");

        // Trim leading and trailing spaces from each element and add them to ArrayList
        for (String part : parts) {
          dietArray.add(part.trim());
        }
      } catch (Exception e) {
        System.out.println("diet array could not be parsed");
      }

      Map<String, Object> data = new HashMap<>();
      Profile user = new Profile(name, experience, intoleranceArray, dietArray);

      data.put("User", user);

      System.out.println("adding profile: for user: " + uid);

      // use the storage handler to add the document to the database
      this.storageHandler.addDocument(uid, "Profile", name, data);

      responseMap.put("response_type", "success");
      responseMap.put("name", user.getName());
      responseMap.put("exp", user.getExp());
      responseMap.put("intoleranceArray", user.getIntolerances());
      responseMap.put("dietArray", user.getDiet());
      //      responseMap.put("User", user);
    } catch (Exception e) {
      // error likely occurred in the storage handler
      e.printStackTrace();
      responseMap.put("response_type", "failure");
      responseMap.put("error", e.getMessage());
    }

    return Utils.toMoshiJson(responseMap);
  }
}
