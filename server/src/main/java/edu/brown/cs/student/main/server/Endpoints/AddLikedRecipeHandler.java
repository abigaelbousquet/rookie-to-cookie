package edu.brown.cs.student.main.server.Endpoints;

import edu.brown.cs.student.main.server.Server;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import edu.brown.cs.student.main.server.storage.Utils;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class AddLikedRecipeHandler implements Route {

  public StorageInterface storageHandler;

  public AddLikedRecipeHandler(StorageInterface storageHandler) {
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
      String recipeId = request.queryParams("recipeId");

      /**
       * essentially make a call to another endpoint with this data and then put the data into the
       * firebase as an object; means spoonacular call should always happen
       */
      Map<String, Object> data = new HashMap<>();
      data.put(recipeId, Server.currRecipe); // puts recipeId

      System.out.println("adding recipeId: " + recipeId + " for user: " + uid);

      // use the storage handler to add the document to the database
      this.storageHandler.addDocument(uid, "liked recipes", recipeId, data);

      responseMap.put("response_type", "success");
      responseMap.put(recipeId, Server.currRecipe);
    } catch (Exception e) {
      // error likely occurred in the storage handler
      e.printStackTrace();
      responseMap.put("response_type", "failure");
      responseMap.put("error", e.getMessage());
    }

    return Utils.toMoshiJson(responseMap);
  }
}
