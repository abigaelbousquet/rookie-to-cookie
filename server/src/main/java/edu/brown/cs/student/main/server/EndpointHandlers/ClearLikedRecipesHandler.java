package edu.brown.cs.student.main.server.EndpointHandlers;

import edu.brown.cs.student.main.server.storage.FirebaseUtilities;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

/** Class to delete a recipe from the liked recipes in Firebase datastore */
public class ClearLikedRecipesHandler implements Route {

  public StorageInterface storageHandler;

  public ClearLikedRecipesHandler(StorageInterface storageHandler) {
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

      // Remove the user from the database
      System.out.println("clearing liked recipes for user: " + uid);
      this.storageHandler.clearRecipes(uid, "liked recipes", recipeIdString);

      responseMap.put("response_type", "success");
    } catch (Exception e) {
      // error likely occurred in the storage handler
      e.printStackTrace();
      responseMap.put("response_type", "failure");
      responseMap.put("error", e.getMessage());
    }

    return FirebaseUtilities.MAP_STRING_OBJECT_JSON_ADAPTER.toJson(responseMap);
  }
}
