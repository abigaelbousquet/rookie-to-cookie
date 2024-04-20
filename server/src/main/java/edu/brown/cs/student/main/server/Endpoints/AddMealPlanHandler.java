//package edu.brown.cs.student.main.server.Endpoints;
//
//import edu.brown.cs.student.main.server.storage.StorageInterface;
//import edu.brown.cs.student.main.server.storage.Utils;
//import java.util.HashMap;
//import java.util.Map;
//import spark.Request;
//import spark.Response;
//import spark.Route;
//
//public class AddMealPlanHandler implements Route {
//
//  public StorageInterface storageHandler;
//
//  public AddMealPlanHandler(StorageInterface storageHandler) {
//    this.storageHandler = storageHandler;
//  }
//
//  /**
//   * Invoked when a request is made on this route's corresponding path e.g. '/hello'
//   *
//   * @param request The request object providing information about the HTTP request
//   * @param response The response object providing functionality for modifying the response
//   * @return The content to be set in the response
//   */
//  @Override
//  public Object handle(Request request, Response response) {
//    Map<String, Object> responseMap = new HashMap<>();
//    try {
//      // collect parameters from the request
//      String uid = request.queryParams("uid");
//      String recipe = request.queryParams("recipeId");
//      int recipeId = 0;
//
//      try {
//        recipeId = Integer.parseInt(recipe);
//      } catch (Exception e) {
//        System.out.println("Recipe ID not properly entered");
//      }
//
//      Map<String, Object> data = new HashMap<>();
//      data.put("recipeId", recipeId);
//
//      System.out.println("adding recipeId: " + recipeId + " for user: " + uid);
//
//      // use the storage handler to add the document to the database
//      this.storageHandler.addDocument(uid, "recipes", recipe, data);
//
//      responseMap.put("response_type", "success");
//      responseMap.put("recipe", recipe);
//    } catch (Exception e) {
//      // error likely occurred in the storage handler
//      e.printStackTrace();
//      responseMap.put("response_type", "failure");
//      responseMap.put("error", e.getMessage());
//    }
//
//    return Utils.toMoshiJson(responseMap);
//  }
//}
