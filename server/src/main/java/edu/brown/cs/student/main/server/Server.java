package edu.brown.cs.student.main.server;

import static spark.Spark.after;

import edu.brown.cs.student.main.server.EndpointHandlers.AddDislikedRecipeHandler;
import edu.brown.cs.student.main.server.EndpointHandlers.AddLikedRecipeHandler;
import edu.brown.cs.student.main.server.EndpointHandlers.AddUserHandler;
import edu.brown.cs.student.main.server.EndpointHandlers.ClearUserHandler;
import edu.brown.cs.student.main.server.EndpointHandlers.ListLikedRecipesHandler;
import edu.brown.cs.student.main.server.RecipeData.Datasource.DatasourceException;
import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeDatasource;
import edu.brown.cs.student.main.server.RecipeData.Datasource.SpoonacularRecipeSource;
import edu.brown.cs.student.main.server.RecipeData.MealPlan;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.MealPlanGenerator;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.MealPlanGeneratorUtilities.GeneratorUtilities;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.Mode;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.RecipeVolumeException;
import edu.brown.cs.student.main.server.storage.FirebaseUtilities;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import spark.Filter;
import spark.Spark;

/** Top Level class for our project, utilizes spark to create and maintain our server. */
public class Server {

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

    StorageInterface firebaseUtils;
    try {
      firebaseUtils = new FirebaseUtilities();

      Spark.get("add-liked-recipe", new AddLikedRecipeHandler(firebaseUtils));
      Spark.get("add-disliked-recipe", new AddDislikedRecipeHandler(firebaseUtils));
      Spark.get("add-user", new AddUserHandler(firebaseUtils));
      Spark.get("get-liked-recipes", new ListLikedRecipesHandler(firebaseUtils));
      Spark.get("get-disliked-recipes", new ListLikedRecipesHandler(firebaseUtils));
      Spark.get("clear-user", new ClearUserHandler(firebaseUtils));

      RecipeDatasource datasource = new SpoonacularRecipeSource();
      MealPlanGenerator planGenerator =
          new MealPlanGenerator(
              datasource,
              Mode.MINIMIZE_FOOD_WASTE,
              "sunday,monday,tuesday,null,null,null,null",
              4,
              null,
              null,
              null,
              null,
              60,
              firebaseUtils,
              "test-1");
      RecipeDatasource datasource1 = new SpoonacularRecipeSource();
      MealPlanGenerator planGenerator1 =
          new MealPlanGenerator(
              datasource1,
              Mode.MINIMIZE_FOOD_WASTE,
              "null,monday,tuesday,null,null,null,saturday",
              5,
              null,
              null,
              null,
              null,
              80,
              firebaseUtils,
              "test-2");
      try {
        MealPlan recipeList = planGenerator.generatePlan();
        GeneratorUtilities.addToFirebase("test-1", firebaseUtils, recipeList);
        MealPlan recipeList1 = planGenerator1.generatePlan();
        GeneratorUtilities.addToFirebase("test2", firebaseUtils, recipeList1);

      } catch (DatasourceException | RecipeVolumeException e) {
        System.out.println(e.getMessage());
      }

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
