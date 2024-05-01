package edu.brown.cs.student.main.server;

import static spark.Spark.after;

import edu.brown.cs.student.main.server.EndpointHandlers.*;
import edu.brown.cs.student.main.server.RecipeData.MealPlan;
import edu.brown.cs.student.main.server.storage.FirebaseUtilities;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import spark.Filter;
import spark.Spark;

/** Top Level class for our project, utilizes spark to create and maintain our server. */
public class Server {
  public static Map<String, MealPlan> userCurrPlan;

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
    Server.userCurrPlan = new HashMap<>();
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
      Spark.get("generate-mealplan", new GenerateMealPlanHandler(firebaseUtils));
      Spark.get("get-mealplan", new GetMealPlanHandler(firebaseUtils));
      Spark.get("save-mealplan", new SaveMealPlanHandler(firebaseUtils));
      Spark.get("clear-liked-recipes", new ClearLikedRecipesHandler(firebaseUtils));
      Spark.get("clear-disliked-recipes", new ClearDislikedRecipesHandler(firebaseUtils));

      //      String uid = "test_user_liked_only";
      //      RecipeDatasource datasource = new SpoonacularRecipeSource();
      //      MealPlanGenerator planGenerator =
      //          new MealPlanGenerator(
      //              datasource,
      //              Mode.MINIMIZE_FOOD_WASTE,
      //              "sunday,monday",
      //              4,
      //              null,
      //              null,
      //              null,
      //              null,
      //              60,
      //              firebaseUtils,
      //              uid);
      //      RecipeDatasource datasource1 = new SpoonacularRecipeSource();
      //      MealPlanGenerator planGenerator1 =
      //          new MealPlanGenerator(
      //              datasource1,
      //              Mode.MINIMIZE_FOOD_WASTE,
      //              "null,monday,tuesday,null,null,null,saturday",
      //              5,
      //              null,
      //              null,
      //              null,
      //              null,
      //              80,
      //              firebaseUtils,
      //              "test-2");
      //      try {
      //        MealPlan recipeList = planGenerator.generatePlan();
      //        GeneratorUtilities.addToFirebase(uid, firebaseUtils, recipeList);
      //        MealPlan recipeList1 = planGenerator1.generatePlan();
      //        GeneratorUtilities.addToFirebase("test-2", firebaseUtils, recipeList1);
      //
      //      } catch (DatasourceException | RecipeVolumeException e) {
      //        System.out.println(e.getMessage());
      //      }

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
