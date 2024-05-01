package edu.brown.cs.student.main.server.RecommenderAlgorithm.MealPlanGeneratorUtilities;

import edu.brown.cs.student.main.server.RecipeData.Recipe.Ingredient;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.KDTree.RecipeRecommendationKDTree;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/** A class for utility methods associated with a MealPlanGenerator. */
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
   * Filters a List of Recipes to return only Recipes with a Spoonacular score above 60.
   *
   * @param allRecipes the unfiltered List of Recipes to filter for good ones
   * @return a List of the Recipes in allRecipes with Spoonacular scores above 60
   */
  public static List<Recipe> filterGoodRatings(List<Recipe> allRecipes) {
    List<Recipe> goodRecipes = new ArrayList<>();
    for (Recipe r : allRecipes) {
      if (r.getSpoonacularScore() > 60) {
        goodRecipes.add(r);
      }
    }
    return goodRecipes;
  }

  /**
   * Finds the n nearest neighbors of a candidate list of recipes closest to the most recipes in a
   * history list of recipes.
   *
   * @param candidates the list of candidate Recipes
   * @param history the list of historical Recipes to find the nearest neighbors of in candidates
   * @param n the number of nearest neighbors to produce
   * @return a Priority Queue of n RecipeFrequencyPairs corresponding to the n overall nearest
   *     neighbors of candidates to the Recipes in history
   */
  public static PriorityQueue<RecipeFrequencyPair> getNearestNeighborsToListRecipes(
      List<Recipe> candidates, List<Recipe> history, int n) {
    // create a K-D tree for finding nearest neighbors with
    RecipeRecommendationKDTree tree = new RecipeRecommendationKDTree();
    for (Recipe r : candidates) {
      tree.insert(r);
    }

    // find the Recipes in candidates most similar to each of the history's Recipes
    Map<Recipe, Integer> similarRecipesWithFrequencies = new HashMap<>();
    for (Recipe historicalRecipe : history) {
      List<Recipe> nearestNeighbors = tree.nearestNeighbors(historicalRecipe, n);
      for (Recipe neighbor : nearestNeighbors) {
        if (similarRecipesWithFrequencies.containsKey(neighbor)) {
          // increase frequency count
          similarRecipesWithFrequencies.put(
              neighbor, similarRecipesWithFrequencies.get(neighbor) + 1);
        } else {
          // add to map with initial frequency of 1
          similarRecipesWithFrequencies.put(neighbor, 1);
        }
      }
    }

    // capture the top n Recipes in candidates most similar to the most Recipes in history
    PriorityQueue<RecipeFrequencyPair> queue =
        new PriorityQueue<>(new RecipeFrequencyPairComparator());
    for (Recipe r : similarRecipesWithFrequencies.keySet()) {
      addAndTrimQueue(queue, new RecipeFrequencyPair(r, similarRecipesWithFrequencies.get(r)), n);
    }
    return queue;
  }

  /**
   * Converts a quantity and unit to base unit of grams for apples to apples comparisons.
   *
   * @param quantity the amount of the substance
   * @param unit the unit of quantity, a String
   * @return the conversion of quantity unit to grams, or 0 if encountered an unknown unit
   */
  private static double convertToGrams(double quantity, String unit) {
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
      case "gram":
      case "grams":
        return quantity; // grams require no conversion
      default:
        return 0; // If unit is unknown, return 0 as to not skew results
    }
  }

  /**
   * Method to take in a recipe and find the most important ingredients in the recipe based on the
   * passed in number of ingredients
   *
   * @param recipe
   * @param numberOfIngredients
   * @return
   */
  public static List<String> findMostAbundantIngredients(Recipe recipe, int numberOfIngredients) {
    List<Ingredient> extendedIngredients = recipe.getExtendedIngredients();
    Map<String, Double> ingredientQuantities =
        new HashMap<>(); // Map to store aggregated quantities

    // Iterate through each ingredient
    for (Ingredient ingredient : extendedIngredients) {
      String name = ingredient.getName();
      double quantity = ingredient.getMeasures().getUs().getAmount();
      String unit = ingredient.getMeasures().getUs().getUnitLong();

      // Convert quantity to a common unit (e.g., grams)
      double commonQuantity = convertToGrams(quantity, unit);

      // Aggregate quantities for each ingredient
      ingredientQuantities.put(name, ingredientQuantities.getOrDefault(name, 0.0) + commonQuantity);
    }

    // Sort the ingredients by their aggregated quantities in descending order
    List<Map.Entry<String, Double>> sortedIngredients =
        new ArrayList<>(ingredientQuantities.entrySet());
    sortedIngredients.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

    // Extract the most abundant ingredients up to the specified number
    List<String> mostAbundantIngredients = new ArrayList<>();
    for (int i = 0; i < Math.min(numberOfIngredients, sortedIngredients.size()); i++) {
      mostAbundantIngredients.add(sortedIngredients.get(i).getKey());
    }

    return mostAbundantIngredients;
  }

  /**
   * Adds a new RecipeFrequencyPair to a PriorityQueue of RecipeFrequencyPairs and trims the queue
   * to a max length.
   *
   * @param queue the queue to add to and trim
   * @param newPair the new DistanceRecipePair to add to queue
   * @param n the number of DistanceRecipePairs to restrict the final length of queue to
   */
  public static void addAndTrimQueue(
      PriorityQueue<RecipeFrequencyPair> queue, RecipeFrequencyPair newPair, int n) {
    queue.add(newPair);
    while (queue.size() > n) {
      queue.remove();
    }
  }
}
