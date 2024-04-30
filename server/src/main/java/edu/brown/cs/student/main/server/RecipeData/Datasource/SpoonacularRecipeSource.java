package edu.brown.cs.student.main.server.RecipeData.Datasource;

import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class SpoonacularRecipeSource implements RecipeDatasource {

  /** TEMPORARILY HOLDING API KEYS HERE, DELETE BEFORE PUSHING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
  private String SPOONACULAR_API_KEY = "0a62b4c389mshb21117420450909p1ec0ddjsna24820635fcb";

  /** DELETE BEFORE PUSHING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
  private final String SPOONACULAR_HOST = "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com";

  /**
   * Queries Spoonacular recipe database for a certain number of recipes fitting the criteria
   * specified by parameters.
   *
   * @param numRecipes the number of recipes to return, between 1 and 100
   * @param cuisine a comma-separated String of the cuisine(s) to query for (options:
   *     https://spoonacular.com/food-api/docs#Cuisines)
   * @param excludeCuisine a comma-separated String of the cuisines to exclude from queries, a
   *     String of comma-separated cuisines (see https://spoonacular.com/food-api/docs#Cuisines)
   * @param diet a comma-separated String of the diets to restrict results to (options:
   *     https://spoonacular.com/food-api/docs#Diets)
   * @param intolerances a comma-separated String of the intolerances to ban from results (options:
   *     https://spoonacular.com/food-api/docs#Intolerances)
   * @param excludeIngredients a comma-separated String of specific ingredients to ban from results
   * @param includeIngredients a comma-separated String of specific ingredients to look for in
   *     recipes
   * @param maxReadyTime the maximum prep plus cooking time to filter results with; 0 will be
   *     interpreted as having no preference
   * @return a randomly ordered List of numRecipes Recipes of main courses fitting the criteria
   *     outlined by these parameters
   * @throws IllegalArgumentException if numRecipes is less than 1 or greater than 100
   */
  public List<Recipe> queryRecipes(
      int numRecipes,
      String cuisine,
      String excludeCuisine,
      String diet,
      String intolerances,
      String excludeIngredients,
      String includeIngredients,
      int maxReadyTime)
      throws IllegalArgumentException, DatasourceException {
    if ((numRecipes < 1) || (numRecipes > 100)) {
      throw new IllegalArgumentException(
          "Query error: Attempted to query <1 or >100 recipes. numRecipes: " + numRecipes);
    }

    //    Dotenv dotenv = Dotenv.configure().load();
    //    this.SPOONACULAR_API_KEY = dotenv.get("SPOONACULAR_API_KEY");

    String parameters =
        generateUserParameters(
            numRecipes,
            cuisine,
            excludeCuisine,
            diet,
            intolerances,
            excludeIngredients,
            includeIngredients,
            maxReadyTime);
    // adding other constant, non-user-set parameters
    parameters += "type=main%20course&";
    parameters += "instructionsRequired=true&";
    parameters += "addRecipeInformation=true&";
    parameters += "fillIngredients=true&";
    parameters += "addRecipeInstructions=true&";
    parameters += "sort=random";

    try {
      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(
                  URI.create(
                      "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/complexSearch?"
                          + "query=%20&"
                          + parameters))
              .header("X-RapidAPI-Key", SPOONACULAR_API_KEY)
              .header("X-RapidAPI-Host", SPOONACULAR_HOST)
              .method("GET", HttpRequest.BodyPublishers.noBody())
              .build();
      HttpResponse<String> response =
          HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      SearchResult recipeResults = RecipeUtilities.deserializeSearchResult(response.body());
      return recipeResults.getResults();

    } catch (IOException | IllegalArgumentException | InterruptedException e) {
      throw new DatasourceException(e.getMessage());
    }
  }

  /**
   * Generates user parameter string to add to API endpoint request. Case-insensitive.
   *
   * @param numRecipes the number of recipes to return, between 1 and 100
   * @param cuisine a comma-separated String of the cuisine(s) to query for (options:
   *     https://spoonacular.com/food-api/docs#Cuisines)
   * @param excludeCuisine a comma-separated String of the cuisines to exclude from queries, a
   *     String of comma-separated cuisines (see https://spoonacular.com/food-api/docs#Cuisines)
   * @param diet a comma-separated String of the diets to restrict results to (options:
   *     https://spoonacular.com/food-api/docs#Diets)
   * @param intolerances a comma-separated String of the intolerances to ban from results (options:
   *     https://spoonacular.com/food-api/docs#Intolerances)
   * @param excludeIngredients a comma-separated String of specific ingredients to ban from results
   * @param includeIngredients a comma-separated String of specific ingredients to look for in
   *     recipes
   * @param maxReadyTime the maximum prep plus cooking time to filter results with
   * @return the formatted, lowercase concatenation of the parameters for an API endpoint request
   */
  private static String generateUserParameters(
      int numRecipes,
      String cuisine,
      String excludeCuisine,
      String diet,
      String intolerances,
      String excludeIngredients,
      String includeIngredients,
      int maxReadyTime) {
    String parameters = "number=" + numRecipes + "&";
    if ((cuisine != null) && !cuisine.isEmpty()) {
      parameters += ("cuisine=" + cuisine.toLowerCase().replaceAll(" ", "%20") + "&");
    }
    if ((excludeCuisine != null) && !excludeCuisine.isEmpty()) {
      parameters += ("excludeCuisine=" + excludeCuisine.toLowerCase().replaceAll(" ", "%20") + "&");
    }
    if ((diet != null) && !diet.isEmpty()) {
      parameters += ("diet=" + diet.toLowerCase().replaceAll(" ", "%20") + "&");
    }
    if ((intolerances != null) && !intolerances.isEmpty()) {
      parameters += ("intolerances=" + intolerances.toLowerCase().replaceAll(" ", "%20") + "&");
    }
    if ((excludeIngredients != null) && !excludeIngredients.isEmpty()) {
      parameters +=
          ("excludeIngredients=" + excludeIngredients.toLowerCase().replaceAll(" ", "%20") + "&");
    }
    if ((includeIngredients != null) && !includeIngredients.isEmpty()) {
      parameters +=
          ("includeIngredients=" + includeIngredients.toLowerCase().replaceAll(" ", "%20") + "&");
    }
    if (maxReadyTime != 0) {
      parameters += ("maxReadyTime=" + maxReadyTime + "&");
    }
    return parameters;
  }
}
