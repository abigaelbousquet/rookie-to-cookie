package edu.brown.cs.student.main.server.RecommenderAlgorithm;

import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeUtilities;
import edu.brown.cs.student.main.server.RecipeData.MealPlan;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Ingredient;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import edu.brown.cs.student.main.server.storage.FirebaseUtilities;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * A class for utility methods associated with a MealPlanGenerator.
 */
public class GeneratorUtilities {

  /**
   * Method to pick the top n elements from the list of options.
   *
   * @param sortedOptions
   * @param numRecommendations
   * @return
   */
  public static List<Recipe> pickTop(List<Recipe> sortedOptions, int numRecommendations) {
    // select top n from this.allRecipes
    return sortedOptions.subList(0, Math.min(numRecommendations, sortedOptions.size()));
  }

  /**
   * Filters a List of Recipes to return only Recipes with a Spoonacular score above 70.
   *
   * @param allRecipes the unfiltered List of Recipes to filter for good ones
   * @return a List of the Recipes in allRecipes with Spoonacular scores above 70
   */
  public static List<Recipe> filterGoodRatings(List<Recipe> allRecipes) {
    List<Recipe> goodRecipes = new ArrayList<>();
    for (Recipe r : allRecipes) {
      if (r.getSpoonacularScore() > 70) {
        goodRecipes.add(r);
      }
    }
    return goodRecipes;
  }

  /**
   * TODO: javadoc
   *
   * @param quantity
   * @param unit
   * @return
   */
  private static double convertToGrams(double quantity, String unit) {
    // Implement conversion logic here based on different units
    // For simplicity, let's assume we're converting everything to grams
    // You may need to add more cases for different units
    switch (unit.toLowerCase()) {
      case "tablespoon":
      case "tablespoons":
        return quantity * 14.3; // Conversion factor for tablespoons to grams
      case "ounce":
      case "ounces":
        return quantity * 28.3495; // Conversion factor for tablespoons to grams
      case "kilogram":
      case "kilograms":
        return quantity * 1000; // Conversion factor for tablespoons to grams
      case "pound":
      case "pounds":
        return quantity * 453.592; // Conversion factor for tablespoons to grams
      case "teaspoon":
      case "teaspoons":
        return quantity * 6; // Conversion factor for tablespoons to grams
      case "pint":
      case "pints":
        return quantity * 470; // Conversion factor for tablespoons to grams
      case "quart":
      case "quarts":
        return quantity * 950; // Conversion factor for tablespoons to grams
      case "gallon":
      case "gallons":
        return quantity * 3800; // Conversion factor for tablespoons to grams
      case "cup":
      case "cups":
        return quantity * 236.6; // Conversion factor for cups to grams
      default:
        return quantity; // If unit is already in grams or unknown, return as is
    }
  }

  /**
   * Method to take in a recipe and find the most important ingredients
   * in the recipe based on the passed in number of ingredients
   * @param recipe
   * @param numberOfIngredients
   * @return
   */
  public static List<String> findMostAbundantIngredients(Recipe recipe,
      int numberOfIngredients) {
    List<Ingredient> extendedIngredients = recipe.getExtendedIngredients();
    Map<String, Double> ingredientQuantities = new HashMap<>(); // Map to store aggregated quantities

    // Iterate through each ingredient
    for (Ingredient ingredient : extendedIngredients) {
      String name = ingredient.getName();
      double quantity = ingredient.getMeasures().getUs().getAmount();
      String unit = ingredient.getMeasures().getUs().getUnitLong();

      // Convert quantity to a common unit (e.g., grams)
      double commonQuantity = convertToGrams(quantity, unit);

      // Aggregate quantities for each ingredient
      ingredientQuantities.put(name, ingredientQuantities.getOrDefault(name,
          0.0) + commonQuantity);
    }

    // Sort the ingredients by their aggregated quantities in descending order
    List<Map.Entry<String, Double>> sortedIngredients = new ArrayList<>
        (ingredientQuantities.entrySet());
    sortedIngredients.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

    // Extract the most abundant ingredients up to the specified number
    List<String> mostAbundantIngredients = new ArrayList<>();
    for (int i = 0; i < Math.min(numberOfIngredients, sortedIngredients.size()); i++) {
      mostAbundantIngredients.add(sortedIngredients.get(i).getKey());
    }

    return mostAbundantIngredients;
  }

//  private String findMostAbundantIngredient(Recipe recipe) {
//    List<Ingredient> extendedIngredients = recipe.getExtendedIngredients();
//    Map<String, Double> ingredientQuantities = new HashMap<>(); // Map to store aggregated quantities
//
//    // Iterate through each ingredient
//    for (Ingredient ingredient : extendedIngredients) {
//      String name = ingredient.getName();
//      double quantity = ingredient.getMeasures().getUs().getAmount();
//      String unit = ingredient.getMeasures().getUs().getUnitLong();
//
//      // Convert quantity to a common unit (e.g., grams)
//      double commonQuantity = convertToGrams(quantity, unit);
//
//      // Aggregate quantities for each ingredient
//      ingredientQuantities.put(name, ingredientQuantities.getOrDefault(name, 0.0)
//          + commonQuantity);
//    }
//
//    // Find the ingredient with the highest aggregated quantity
//    String mostAbundantIngredient = Collections.max(ingredientQuantities.entrySet(),
//        Map.Entry.comparingByValue()).getKey();
//    return mostAbundantIngredient;
//  }

  /**
   * Method to convert the given firebase data into a list of recipes.
   *
   * @param firebaseData
   * @return
   * @throws IllegalArgumentException
   * @throws IOException
   */
  public static List<Recipe> convertFirebaseData(List<Map<String, Object>> firebaseData)
      throws IllegalArgumentException, IOException {
    List<Recipe> recipes = new ArrayList<>();
    for (Map<String, Object> recipeData : firebaseData) {
      // Deserialize each map entry into a Recipe object
      String recipeJson = FirebaseUtilities.MAP_STRING_OBJECT_JSON_ADAPTER.toJson(recipeData);
      Recipe recipe = RecipeUtilities.deserializeRecipe(recipeJson);
      if (recipe != null) {
        recipes.add(recipe);
      }
    }
    return recipes;
  }

  /**
   * Method to add the given meal plan to the firestore database.
   *
   * @param uid
   * @param firebaseData
   * @param plan
   */
  public static void addToFirebase(String uid, StorageInterface firebaseData, MealPlan plan) {
    Map<String, Object> data = new HashMap<>();
    // also need a way to find date range, for now just gonna call mealplan-1, etc
    int mealCount =0;
    String planId="default";
    try {
      mealCount = firebaseData.getCollection(uid, "Mealplans").size();
      planId = "mealplan-" + mealCount;
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ExecutionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    data.put(planId, plan);

    firebaseData.addDocument(uid, "Mealplans", planId, data);
  }

}
