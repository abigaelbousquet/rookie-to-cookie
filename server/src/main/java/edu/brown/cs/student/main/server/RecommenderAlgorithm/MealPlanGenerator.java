package edu.brown.cs.student.main.server.RecommenderAlgorithm;

import edu.brown.cs.student.main.server.RecipeData.MealPlan;
import edu.brown.cs.student.main.server.RecipeData.Datasource.DatasourceException;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Ingredient;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeDatasource;
import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeUtilities;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import edu.brown.cs.student.main.server.storage.Utils;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.*;
public class MealPlanGenerator {
  private final RecipeDatasource DATASOURCE;
  private final Mode recipes;
  private final boolean[] DAYS_TO_PLAN; // Note: 0 is sunday
  private int NUM_DAYS_TO_PLAN;
  private final String CUISINE;
  private final String EXCLUDE_CUISINE;
  private final String DIET;
  private String INTOLERANCES;
  private String EXCLUDE_INGREDIENTS;
  private final int MAX_READY_TIME;
  private final List<Recipe> likedRecipes;
  private final List<Recipe> dislikedRecipes;
  private final StorageInterface FIREBASE_DATA;
  private final String UID;

  /**
   * Constructor for the MealPlanGenerator class to initialize private
   * instance variables for the class
   * @param recipeSource
   * @param mode
   * @param daysOfWeek
   * @param cuisine
   * @param excludeCuisine
   * @param diet
   * @param intolerances
   * @param maxReadyTime
   * @param firebaseData
   * @param uid
   * @throws ExecutionException
   * @throws InterruptedException
   * @throws IOException
   */
  public MealPlanGenerator(RecipeDatasource recipeSource, Mode mode, String daysOfWeek,
      String cuisine,
      String excludeCuisine,
      String diet,
      String intolerances,
      int maxReadyTime,
      StorageInterface firebaseData,
      String uid) throws ExecutionException, InterruptedException, IOException {
    this.recipes = mode;
    this.CUISINE = cuisine;
    this.EXCLUDE_CUISINE = excludeCuisine;
    this.DIET = diet;
    this.MAX_READY_TIME = maxReadyTime;
    this.FIREBASE_DATA = firebaseData;
    this.UID = uid;
    this.DAYS_TO_PLAN = parseDays(daysOfWeek);
    setIntolerancesAndAllergens(intolerances);
    this.DATASOURCE = recipeSource;
    this.dislikedRecipes = this.convertFirebaseData(firebaseData.getCollection(uid, "liked recipes"));
    this.likedRecipes = this.convertFirebaseData(firebaseData.getCollection(uid, "liked recipes"));
  }

  public MealPlan generatePlan() throws DatasourceException, RecipeVolumeException {
    List<Recipe> weekOfRecipes;
    if (this.recipes == Mode.MINIMIZE_FOOD_WASTE) {
      weekOfRecipes = this.minimizeFoodWaste();
    }
    else {
      weekOfRecipes = this.personalize();
    }
    return this.createMealPlan(weekOfRecipes);
  }

  /**
   * Method to create a meal plan object from a list of recipes.
   *
   * @param recipes
   * @return
   */
  private MealPlan createMealPlan(List<Recipe> recipes) {
    Recipe[] orderedWeekOfRecipes = {null, null, null, null, null, null, null}; // index 0 is Sunday
    for (int i = 0; i < 7; i++) {
      if (this.DAYS_TO_PLAN[i]) {
        orderedWeekOfRecipes[i] = recipes.remove(0);
      }
    }
    return new MealPlan(orderedWeekOfRecipes[0], orderedWeekOfRecipes[1],
        orderedWeekOfRecipes[2], orderedWeekOfRecipes[3], orderedWeekOfRecipes[4],
        orderedWeekOfRecipes[5], orderedWeekOfRecipes[6]);
  }

  /**
   * Method to parse the intolerances and allergens from a cs string
   * @param fullString
   */
  private void setIntolerancesAndAllergens(String fullString) {
    String[] supportedIntolerances = {"dairy", "egg", "gluten", "peanut", "sesame",
        "seafood", "shellfish", "soy", "sulfite", "tree%20nut", "wheat"};
    List<String> supportedIntolerancesList = Arrays.stream(supportedIntolerances).toList();
    String[] args = fullString.split(",");
    String intolerances = "";
    String excludeIngredients = "";

    for (String avoid : args) {
      String avoidLower = avoid.toLowerCase();
      if (supportedIntolerancesList.contains(avoidLower)) {
        intolerances += (avoidLower + ",");
      } else {
        excludeIngredients += (avoidLower + ",");
      }
    }

    if (intolerances.isEmpty()) {
      this.INTOLERANCES = null;
    } else {
      this.INTOLERANCES = intolerances.substring(0, -1);
    }

    if (excludeIngredients.isEmpty()) {
      this.EXCLUDE_INGREDIENTS = null;
    } else {
      this.EXCLUDE_INGREDIENTS = excludeIngredients.substring(0, excludeIngredients.length()-1);
    }
  }

  /**
   * Method to parse the given CSV string into a boolean array representing which
   * days of the week have recipes
   * @param daysOfWeek
   * @return
   */
  private boolean[] parseDays(String daysOfWeek){
    int count = 0;
    boolean[] booleanArray = new boolean[7];
    String[] dayofWeekArray = daysOfWeek.split(",");
    for (int i = 0; i < 7; i++) {
      booleanArray[i] = false;
      String day = dayofWeekArray[i];
      if (!day.equals("null")) {
        booleanArray[i] = true;
        count++;
      }
    }
    this.NUM_DAYS_TO_PLAN = count;
    return booleanArray;
  }

  /**
   * Method to pick the top n elements from the list of options
   * @param sortedOptions
   * @param numRecommendations
   * @return
   */
  private List<Recipe> pickTop(List<Recipe> sortedOptions, int numRecommendations) {
    // select top n from this.allRecipes
    return sortedOptions.subList(0, Math.min(numRecommendations, sortedOptions.size()));
  }

  /**
   * Method to convert the given firebase data in a list of recipes</>
   * @param firebaseData
   * @return
   * @throws IllegalArgumentException
   * @throws IOException
   */
  private List<Recipe> convertFirebaseData(List<Map<String, Object>> firebaseData)
      throws IllegalArgumentException, IOException {
    List<Recipe> recipes = new ArrayList<>();
    for (Map<String, Object> recipeData : firebaseData) {
      // Deserialize each map entry into a Recipe object
      String recipeJson = Utils.toMoshiJson(recipeData);

      Recipe recipe = RecipeUtilities.deserializeRecipe(recipeJson);
      if (recipe != null) {
        recipes.add(recipe);
      }
    }
    return recipes;
  }

  /**
   * Method to add the given meal plan to the firestore database
   * @param uid
   * @param firebaseData
   * @param plan
   */
  private void addToFirebase(String uid, StorageInterface firebaseData, MealPlan plan) {
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

  /**
   * Filters a List of Recipes to return only Recipes with a Spoonacular score above 70.
   *
   * @param allRecipes the unfiltered List of Recipes to filter for good ones
   * @return a List of the Recipes in allRecipes with Spoonacular scores above 70
   */
  private static List<Recipe> filterGoodRatings(List<Recipe> allRecipes) {
    List<Recipe> goodRecipes = new ArrayList<>();
    for (Recipe r : allRecipes) {
      if (r.getSpoonacularScore() > 70) {
        goodRecipes.add(r);
      }
    }
    return goodRecipes;
  }

  /**
   * Generates a list of Recipes fitting user criteria this MealPlanGenerator was constructed with,
   * prioritizing minimizing food waste in the synthesized list of Recipes returned.
   *
   * @return a list of NUM_DAYS_TO_PLAN Recipes fitting the criteria this was instantiated with
   * @throws DatasourceException if unsuccessful in querying Spoonacular for any recipes
   * @throws RecipeVolumeException if unable to find NUM_DAYS_TO_PLAN quality recipes fitting t
   * his generator's criteria
   */
  public List<Recipe> minimizeFoodWaste() throws DatasourceException, RecipeVolumeException {
    // PART 1 - get a starting recipe to base the rest of the food-waste-minimizing recipes on
    List<Recipe> searchResults = this.DATASOURCE.queryRecipes(5, this.CUISINE,
        this.EXCLUDE_CUISINE, this.DIET, this.INTOLERANCES, this.EXCLUDE_INGREDIENTS,
        null, this.MAX_READY_TIME);
    List<Recipe> goodResults = filterGoodRatings(searchResults);
    int numGoodResults = goodResults.size();
    if (numGoodResults < this.NUM_DAYS_TO_PLAN) {
      throw new RecipeVolumeException("Caller requested " + this.NUM_DAYS_TO_PLAN +
          " recipes, but only " + numGoodResults + " quality recipes fitting their needs "
          + "could be found.");
    }

    Recipe firstRecipe = goodResults.get(0);
    List<Recipe> algorithmResults = new ArrayList<>();
    algorithmResults.add(firstRecipe);

    if (this.NUM_DAYS_TO_PLAN > 1) {
      // PART 2 - fill in the rest of the week's recipes sharing the main ingredient of the first
      String mainIngredient = findMostAbundantIngredients(firstRecipe,
              1).get(0);
      searchResults = this.DATASOURCE.queryRecipes(30, this.CUISINE,
              this.EXCLUDE_CUISINE,
          this.DIET, this.INTOLERANCES, this.EXCLUDE_INGREDIENTS, mainIngredient,
              this.MAX_READY_TIME);
      goodResults = filterGoodRatings(searchResults);
      numGoodResults = goodResults.size();
      if (numGoodResults < (this.NUM_DAYS_TO_PLAN-1)) {
        throw new RecipeVolumeException("Caller requested " + this.NUM_DAYS_TO_PLAN +
            " recipes, but only " + (numGoodResults+1) + " quality recipes fitting their "
            + "needs could be found.");
      }
      algorithmResults.addAll(goodResults.subList(0, this.NUM_DAYS_TO_PLAN-1));
    }
    assert(algorithmResults.size() == this.NUM_DAYS_TO_PLAN);
    return algorithmResults;
  }

  /**
   * Method to take in a recipe and find the most important ingredients
   * in the recipe based on the passed in number of ingredients
   * @param recipe
   * @param numberOfIngredients
   * @return
   */
  private List<String> findMostAbundantIngredients(Recipe recipe,
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
   * Method to calculate the similarity between two recipes
   * @param recipe1
   * @param recipe2
   * @return
   */
  private double calculateSimilarity(Recipe recipe1, Recipe recipe2) {
    // Method to calculate dissimilarity based on ingredient overlap
    List<Ingredient> ingredients1 = recipe1.getExtendedIngredients();
    List<Ingredient> ingredients2 = recipe2.getExtendedIngredients();

    // Calculate the intersection (overlap) of ingredients
    Set<Ingredient> intersection = new HashSet<>(ingredients1);
    intersection.retainAll(ingredients2);

    // Calculate the Jaccard similarity coefficient
    double similarity = (double) intersection.size()
            / (ingredients1.size() + ingredients2.size() - intersection.size());
    return similarity;
  }

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

  public List<Recipe> personalize() throws DatasourceException {
    //TODO: implement
    for (Recipe recipe : this.likedRecipes) {
      List<String> mostImportantLiked = this.findMostAbundantIngredients(recipe, 3);
      List<Recipe> searchResults = this.DATASOURCE.queryRecipes(30, this.CUISINE,
              this.EXCLUDE_CUISINE,
              this.DIET, this.INTOLERANCES, this.EXCLUDE_INGREDIENTS, mostImportantLiked.get(0),
              this.MAX_READY_TIME);
    }
    for (Recipe recipe : this.dislikedRecipes) {
      List<String> mostImportantLiked = this.findMostAbundantIngredients(recipe, 3);
    }
    return new ArrayList<>();
  }


}
