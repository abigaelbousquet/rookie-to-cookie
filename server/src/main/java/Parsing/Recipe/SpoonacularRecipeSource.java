package Parsing.Recipe;

import Parsing.Recipe.SpoonacularRecipeUtilities.Recipe;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import okio.Buffer;
import java.util.List;
import java.util.Set;

public class SpoonacularRecipeSource {

  /**
   * Queries Spoonacular recipe database for a certain number of recipes fitting the criteria specified by parameters.
   *
   * @param numRecipes the number of recipes to return, between 1 and 100
   * @param cuisine a comma-separated String of the cuisine(s) to query for (options: https://spoonacular.com/food-api/docs#Cuisines)
   * @param excludeCuisine a comma-separated String of the cuisines to exclude from queries, a String of comma-separated cuisines (see https://spoonacular.com/food-api/docs#Cuisines)
   * @param diet a comma-separated String of the diets to restrict results to (options: https://spoonacular.com/food-api/docs#Diets)
   * @param intolerances a comma-separated String of the intolerances to ban from results (options: https://spoonacular.com/food-api/docs#Intolerances)
   * @param excludeIngredients a comma-separated String of specific ingredients to ban from results
   * @param maxReadyTime the maximum prep plus cooking time to filter results with
   * @return a randomly ordered List of numRecipes Recipes of main courses fitting the criteria outlined by these parameters
   * @throws IllegalArgumentException if numRecipes is less than 1 or greater than 100
   */
  public List<Recipe> queryRecipes(int numRecipes, String cuisine, String excludeCuisine, String diet, String intolerances, String excludeIngredients, int maxReadyTime) throws IllegalArgumentException, DatasourceException {
    if ((numRecipes < 1) || (numRecipes > 100)) {
      throw new IllegalArgumentException("Query error: Attempted to query <1 or >100 recipes. numRecipes: " + numRecipes);
    }

    String parameters = generateUserParameters(numRecipes, cuisine, excludeCuisine, diet, intolerances, excludeIngredients, maxReadyTime);
    // adding other constant, non-user-set parameters
    parameters += "type=main%20course&";
    parameters += "instructionsRequired=true&";
    parameters += "addRecipeInformation=true&";
    parameters += "addRecipeInstructions=true&";
    parameters += "sort=random";
    // TODO: any other constant params?

//    try {
//      // TODO: fill in to integrate with spoonacular!
//
//      URL requestURL = new URL("https", "api.weather.gov", "/points/"+lat+","+lon);
//      HttpURLConnection clientConnection = connect(requestURL);
//
//      Set<Recipe> recipeResults = SpoonacularRecipeUtilities.deserializeRecipeList(new Buffer().readFrom(clientConnection.getInputStream()));
//      clientConnection.disconnect();
//    } catch (IOException | IllegalArgumentException e) {
//      throw new DatasourceException(e.getMessage());
//    }
    return null;
  }

  /**
   * Generates user parameter string to add to API endpoint request. Case-insensitive.
   *
   * @param numRecipes the number of recipes to return, between 1 and 100
   * @param cuisine a comma-separated String of the cuisine(s) to query for (options: https://spoonacular.com/food-api/docs#Cuisines)
   * @param excludeCuisine a comma-separated String of the cuisines to exclude from queries, a String of comma-separated cuisines (see https://spoonacular.com/food-api/docs#Cuisines)
   * @param diet a comma-separated String of the diets to restrict results to (options: https://spoonacular.com/food-api/docs#Diets)
   * @param intolerances a comma-separated String of the intolerances to ban from results (options: https://spoonacular.com/food-api/docs#Intolerances)
   * @param excludeIngredients a comma-separated String of specific ingredients to ban from results
   * @param maxReadyTime the maximum prep plus cooking time to filter results with
   * @return the formatted, lowercase concatenation of the parameters for an API endpoint request
   */
  private static String generateUserParameters(int numRecipes, String cuisine, String excludeCuisine, String diet, String intolerances, String excludeIngredients, int maxReadyTime) {
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
      parameters += ("excludeIngredients=" + excludeIngredients.toLowerCase().replaceAll(" ", "%20") + "&");
    }
    parameters += ("maxReadyTime=" + maxReadyTime);

    if (parameters.endsWith("&")) {
      // shouldn't happen since maxReadyTime is always last, but just in case
      return parameters.substring(0, parameters.length()-1);
    } else {
      return parameters;
    }
  }
}
