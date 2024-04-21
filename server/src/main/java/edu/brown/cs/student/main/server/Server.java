package edu.brown.cs.student.main.server;

import static spark.Spark.after;

import edu.brown.cs.student.main.server.Endpoints.AddDislikedRecipeHandler;
import edu.brown.cs.student.main.server.Endpoints.AddLikedRecipeHandler;
import edu.brown.cs.student.main.server.Endpoints.AddUserHandler;
import edu.brown.cs.student.main.server.Parsing.Recipe.SpoonacularRecipeUtilities;
import edu.brown.cs.student.main.server.Parsing.Recipe.SpoonacularRecipeUtilities.Ingredient;
import edu.brown.cs.student.main.server.Parsing.Recipe.SpoonacularRecipeUtilities.Measurement;
import edu.brown.cs.student.main.server.Parsing.Recipe.SpoonacularRecipeUtilities.Recipe;
import edu.brown.cs.student.main.server.Parsing.Recipe.SpoonacularRecipeUtilities.RecipeInstructions;
import edu.brown.cs.student.main.server.Parsing.Recipe.SpoonacularRecipeUtilities.Step;
import edu.brown.cs.student.main.server.Parsing.Recipe.SpoonacularRecipeUtilities.USMeasurement;
import edu.brown.cs.student.main.server.algorithm.RecipeRecommendationSystem;
import edu.brown.cs.student.main.server.storage.FirebaseUtilities;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

import spark.Filter;
import spark.Spark;

/**
 * Top Level class for our project, utilizes spark to create and maintain our
 * server.
 */
public class Server {

  public static void setUpServer() throws IllegalArgumentException, InterruptedException, ExecutionException {
    int port = 3232;

    Spark.port(port);

    after(
        (Filter) (request, response) -> {
          response.header("Access-Control-Allow-Origin", "*");
          response.header("Access-Control-Allow-Methods", "*");
        });

    StorageInterface firebaseUtils;
    try {
      firebaseUtils = new FirebaseUtilities();

      Spark.get("add-liked-recipe", new AddLikedRecipeHandler(firebaseUtils));
      Spark.get("add-disliked-recipe", new AddDislikedRecipeHandler(firebaseUtils));
      Spark.get("add-user", new AddUserHandler(firebaseUtils));

      /**
       * TESTING THE RECIPE RECCOMENDATION SYSTEM DELETE AFTER
       */
      List<Recipe> recipes = new ArrayList<>();

      // Recipe 1: Lemon Garlic Roasted Chicken
      Recipe recipe1 = new Recipe(
          1,
          "Chef John",
          "Lemon Garlic Roasted Chicken",
          "https://example.com/lemon_garlic_roasted_chicken.jpg",
          4,
          60,
          8.5,
          new HashSet<>(Arrays.asList("American")),
          new HashSet<>(Arrays.asList("Paleo")),
          true,
          true,
          false,
          false,
          new HashSet<>(Arrays.asList(
              new Ingredient(new Measurement(new USMeasurement(1, "whole")), Collections.emptyList(), "chicken"),
              new Ingredient(new Measurement(new USMeasurement(2, "whole")), Collections.emptyList(), "lemon"),
              new Ingredient(new Measurement(new USMeasurement(4, "cloves")), Collections.emptyList(), "garlic"),
              new Ingredient(new Measurement(new USMeasurement(2, "tablespoons")), Collections.emptyList(),
                  "olive oil"),
              new Ingredient(new Measurement(new USMeasurement(2, "sprigs")), Collections.emptyList(), "rosemary"),
              new Ingredient(new Measurement(new USMeasurement(1, "teaspoon")), Collections.emptyList(), "salt"),
              new Ingredient(new Measurement(new USMeasurement(1, "teaspoon")), Collections.emptyList(),
                  "black pepper"))),
          Arrays.asList(
              new RecipeInstructions("Main Steps", Arrays.asList(
                  new Step(1, "Preheat oven to 375°F (190°C)."),
                  new Step(2,
                      "In a small bowl, mix together olive oil, lemon juice, minced garlic, salt, and black pepper."),
                  new Step(3, "Rub the mixture over the chicken and inside the cavity."),
                  new Step(4, "Place lemon halves and rosemary sprigs inside the chicken cavity."),
                  new Step(5,
                      "Roast in the preheated oven for about 1 hour, or until juices run clear and chicken is cooked through."),
                  new Step(6, "Let the chicken rest for 10 minutes before carving.")))));
      recipes.add(recipe1);

      // Recipe 2: Creamy Tomato Basil Soup
      Recipe recipe2 = new Recipe(
          2,
          "Taste of Home",
          "Creamy Tomato Basil Soup",
          "https://example.com/creamy_tomato_basil_soup.jpg",
          6,
          30,
          9.2,
          new HashSet<>(Arrays.asList("Italian")),
          new HashSet<>(Arrays.asList("Vegetarian")),
          false,
          true,
          false,
          true,
          new HashSet<>(Arrays.asList(
              new Ingredient(new Measurement(new USMeasurement(6, "large")), Collections.emptyList(), "tomatoes"),
              new Ingredient(new Measurement(new USMeasurement(1, "medium")), Collections.emptyList(), "onion"),
              new Ingredient(new Measurement(new USMeasurement(4, "cloves")), Collections.emptyList(), "garlic"),
              new Ingredient(new Measurement(new USMeasurement(4, "cups")), Collections.emptyList(), "vegetable broth"),
              new Ingredient(new Measurement(new USMeasurement(1, "cup")), Collections.emptyList(), "heavy cream"),
              new Ingredient(new Measurement(new USMeasurement(0.25, "cup")), Collections.emptyList(), "fresh basil"),
              new Ingredient(new Measurement(new USMeasurement(2, "tablespoons")), Collections.emptyList(), "butter"),
              new Ingredient(new Measurement(new USMeasurement(1, "teaspoon")), Collections.emptyList(), "salt"),
              new Ingredient(new Measurement(new USMeasurement(0.5, "teaspoon")), Collections.emptyList(),
                  "black pepper"))),
          Arrays.asList(
              new RecipeInstructions("Main Steps", Arrays.asList(
                  new Step(1, "In a large saucepan, sauté onion and garlic in butter until tender."),
                  new Step(2, "Add tomatoes, vegetable broth, salt, and pepper. Bring to a boil."),
                  new Step(3, "Reduce heat and simmer, uncovered, for 20 minutes."),
                  new Step(4, "Use an immersion blender to puree the soup until smooth."),
                  new Step(5, "Stir in heavy cream and fresh basil. Heat through but do not boil."),
                  new Step(6, "Serve hot, garnished with additional basil if desired.")))));
      recipes.add(recipe2);
      new RecipeRecommendationSystem(recipes, firebaseUtils, "test", 2);

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
  public static void main(String[] args) throws IllegalArgumentException, InterruptedException, ExecutionException {
    setUpServer();
  }
}
