package edu.brown.cs.student.main.server.EndpointHandlers;

import edu.brown.cs.student.main.server.UserData.Profile;
import edu.brown.cs.student.main.server.storage.FirebaseUtilities;
import edu.brown.cs.student.main.server.storage.StorageInterface;
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
      // remove bracket filtering
      String uid = request.queryParams("uid");
      String name = request.queryParams("name");
      String exp = request.queryParams("exp");
      String diet = request.queryParams("diet");
      String intolerances = request.queryParams("intolerances");
      String familySizesString = request.queryParams("familySize");

      int experience = 0;
      int familySize = 0;
      ArrayList<String> intoleranceArray = new ArrayList<>();

      // convert exp to a num
      try {
        experience = Integer.parseInt(exp);
        familySize = Integer.parseInt(familySizesString);
      } catch (Exception e) {
        System.out.println("experience is not a num");
      }

      // convert intolerance string to array
      try {
        // Remove square brackets
        // Split the remaining string by commas
        String[] parts = intolerances.split(",");

        // Trim leading and trailing spaces from each element and add them to ArrayList
        for (String part : parts) {
          if (part.equals("") || part.equals(" ") || part.equals("null")) {
            break;
          }
          intoleranceArray.add(part.trim());
        }
      } catch (Exception e) {
        System.out.println("Intolerance array could not be parsed");
      }

      Map<String, Object> data = new HashMap<>();
      Profile user = new Profile(name, experience, intoleranceArray, diet, familySize);

      data.put("User", user);

      System.out.println("adding profile: for user: " + uid);

      // use the storage handler to add the document to the database
      this.storageHandler.addDocument(uid, "Profile", name, data);

      responseMap.put("response_type", "success");
      responseMap.put("name", user.getName());
      responseMap.put("exp", user.getExp());
      responseMap.put("intoleranceArray", user.getIntolerances());
      responseMap.put("dietArray", user.getDiet());
    } catch (Exception e) {
      // error likely occurred in the storage handler
      e.printStackTrace();
      responseMap.put("response_type", "failure");
      responseMap.put("error", e.getMessage());
    }

    return FirebaseUtilities.MAP_STRING_OBJECT_JSON_ADAPTER.toJson(responseMap);
  }
}
