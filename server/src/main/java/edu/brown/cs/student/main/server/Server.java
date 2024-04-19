package edu.brown.cs.student.main.server;

import static spark.Spark.after;

import edu.brown.cs.student.main.server.Endpoints.AddDislikedRecipeHandler;
import edu.brown.cs.student.main.server.Endpoints.AddLikedRecipeHandler;
import edu.brown.cs.student.main.server.Endpoints.AddMealPlanHandler;
import edu.brown.cs.student.main.server.Endpoints.SpoonacularHandler;
import edu.brown.cs.student.main.server.RecipeParsing.Recipe.Recipe;
import edu.brown.cs.student.main.server.storage.FirebaseUtilities;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import java.io.IOException;
import spark.Filter;
import spark.Spark;

/** Top Level class for our project, utilizes spark to create and maintain our server. */
public class Server {

  public static Recipe currRecipe;

  public static void setUpServer() {
    int port = 3232;

    Spark.port(port);

    after(
        (Filter)
            (request, response) -> {
              response.header("Access-Control-Allow-Origin", "*");
              response.header("Access-Control-Allow-Methods", "*");
            });

    StorageInterface firebaseUtils;
    try {
      firebaseUtils = new FirebaseUtilities();

      Spark.get("add-liked-recipe", new AddLikedRecipeHandler(firebaseUtils));
      Spark.get("add-disliked-recipe", new AddDislikedRecipeHandler(firebaseUtils));
      Spark.get("add-mealplan", new AddMealPlanHandler(firebaseUtils));
      Spark.get("spoonacular", new SpoonacularHandler());


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
   */
  public static void main(String[] args) {
    setUpServer();
  }
}
