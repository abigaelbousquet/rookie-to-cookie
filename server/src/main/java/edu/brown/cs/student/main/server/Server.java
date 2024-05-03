package edu.brown.cs.student.main.server;

import static spark.Spark.after;

import edu.brown.cs.student.main.server.EndpointHandlers.*;
import edu.brown.cs.student.main.server.RecipeData.Datasource.SpoonacularRecipeSource;
import edu.brown.cs.student.main.server.RecipeData.MealPlan;
import edu.brown.cs.student.main.server.storage.FirebaseUtilities;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import spark.Filter;
import spark.Spark;

/** Top Level class for our project, utilizes spark to create and maintain our server. */
public class Server {
  private static Map<String, MealPlan> userCurrPlan;

  public static void setUpServer()
      throws IllegalArgumentException, InterruptedException, ExecutionException {
    int port = 3232;

    Spark.port(port);

    after(
        (Filter)
            (request, response) -> {
              response.header("Access-Control-Allow-Origin", "*");
              response.header("Access-Control-Allow-Methods", "*");
            });
    userCurrPlan = new ConcurrentHashMap<>();
    StorageInterface firebaseUtils;
    try {
      firebaseUtils = new FirebaseUtilities();

      Spark.get("add-liked-recipe", new AddLikedRecipeHandler(firebaseUtils));
      Spark.get("add-disliked-recipe", new AddDislikedRecipeHandler(firebaseUtils));
      Spark.get("add-user", new AddUserHandler(firebaseUtils));
      Spark.get("get-liked-recipes", new ListLikedRecipesHandler(firebaseUtils));
      Spark.get("get-disliked-recipes", new ListDislikedRecipesHandler(firebaseUtils));
      Spark.get("clear-user", new ClearUserHandler(firebaseUtils));
      Spark.get("get-user", new GetUserHandler(firebaseUtils));
      Spark.get(
          "generate-mealplan",
          new GenerateMealPlanHandler(firebaseUtils, new SpoonacularRecipeSource(), userCurrPlan));
      Spark.get("get-mealplan", new GetMealPlanHandler(firebaseUtils));
      Spark.get("save-mealplan", new SaveMealPlanHandler(firebaseUtils, userCurrPlan));
      Spark.get("clear-liked-recipes", new ClearLikedRecipesHandler(firebaseUtils));
      Spark.get("clear-disliked-recipes", new ClearDislikedRecipesHandler(firebaseUtils));

      Spark.notFound(
          (request, response) -> {
            response.status(404); // Not Found
            System.out.println("ERROR");
            return "404 Not Found - The requested endpoint does not exist.";
          });
      Spark.init();
      Spark.awaitInitialization();

      System.out.println("Server started at http://localhost:" + port);
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println(
          "Error: Could not initialize Firebase. Likely due to firebase_config.json not being found. Exiting.");
      System.exit(1);
    }
  }

  /**
   * Runs Endpoints.Server.
   *
   * @param args none
   * @throws ExecutionException
   * @throws InterruptedException
   * @throws IllegalArgumentException
   */
  public static void main(String[] args)
      throws IllegalArgumentException, InterruptedException, ExecutionException {
    setUpServer();
  }
}
