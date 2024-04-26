package edu.brown.cs.student.main.server.RecommenderAlgorithm;

import edu.brown.cs.student.main.server.RecipeData.MealPlan;
import edu.brown.cs.student.main.server.RecipeData.Datasource.DatasourceException;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeDatasource;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A class describing a MealPlanGenerator and its associated algorithmic methods.
 */
public class MealPlanGenerator {
  private final RecipeDatasource DATASOURCE;
  private final Mode recipes;
  private final boolean[] DAYS_TO_PLAN; // Note: 0 is sunday
  private int NUM_DAYS_TO_PLAN;
  private final int REQUESTED_SERVINGS;
  private final String CUISINE;
  private final String EXCLUDE_CUISINE;
  private final String DIET;
  private String INTOLERANCES = null;
  private String EXCLUDE_INGREDIENTS = null;
  private final int MAX_READY_TIME;
  private final List<Recipe> likedRecipes;
  private final List<Recipe> dislikedRecipes;
  private final StorageInterface FIREBASE_DATA;
  private final String UID;

  /**
   * Constructor for the MealPlanGenerator class to initialize private
   * instance variables for the class
   *
   * @param recipeSource
   * @param mode
   * @param daysOfWeek
   * @param servings
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
  public MealPlanGenerator(RecipeDatasource recipeSource, Mode mode, String daysOfWeek, int servings,
      String cuisine,
      String excludeCuisine,
      String diet,
      String intolerances,
      int maxReadyTime,
      StorageInterface firebaseData,
      String uid) throws ExecutionException, InterruptedException, IOException {
    this.recipes = mode;
    this.REQUESTED_SERVINGS = servings;
    this.CUISINE = cuisine;
    this.EXCLUDE_CUISINE = excludeCuisine;
    this.DIET = diet;
    this.MAX_READY_TIME = maxReadyTime;
    this.FIREBASE_DATA = firebaseData;
    this.UID = uid;
    this.DAYS_TO_PLAN = parseDays(daysOfWeek);
    setIntolerancesAndAllergens(intolerances);
    this.DATASOURCE = recipeSource;
    this.dislikedRecipes = GeneratorUtilities.convertFirebaseData(firebaseData.getCollection(uid, "liked recipes"));
    this.likedRecipes = GeneratorUtilities.convertFirebaseData(firebaseData.getCollection(uid, "liked recipes"));
  }

  /**
   * Generates a MealPlan based on the criteria and mode this MealPlanGenerator was instantiated with.
   *
   * @return a MealPlan fitting this MealPlanGenerator's criteria recommended in its mode
   * @throws DatasourceException if unsuccessful in querying the recipe datasource for any recipes
   * @throws RecipeVolumeException if unable to find NUM_DAYS_TO_PLAN quality recipes fitting
   * this generator's criteria
   */
  public MealPlan generatePlan() throws DatasourceException, RecipeVolumeException {
    List<Recipe> weekOfRecipes;
    if (this.recipes == Mode.MINIMIZE_FOOD_WASTE) {
      weekOfRecipes = this.minimizeFoodWaste();
    }
    else {
      weekOfRecipes = this.personalize();
    }
    List<Recipe> weekOfRecipesScaledServings = new ArrayList<>(weekOfRecipes);
    for (Recipe recipe : weekOfRecipesScaledServings) {
      recipe.scaleRecipe(this.REQUESTED_SERVINGS);
    }
    return this.createMealPlan(weekOfRecipesScaledServings);
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
    if (fullString == null || fullString.isEmpty()) {
      return;
    }
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
   * days of the week have recipes.
   *
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
   * Generates a list of Recipes fitting user criteria this MealPlanGenerator was constructed with,
   * prioritizing minimizing food waste in the synthesized list of Recipes returned.
   *
   * @return a list of NUM_DAYS_TO_PLAN Recipes fitting the criteria this was instantiated with
   * @throws DatasourceException if unsuccessful in querying Spoonacular for any recipes
   * @throws RecipeVolumeException if unable to find NUM_DAYS_TO_PLAN quality recipes fitting
   * this generator's criteria
   */
  public List<Recipe> minimizeFoodWaste() throws DatasourceException, RecipeVolumeException {
    // PART 1 - get a starting recipe to base the rest of the food-waste-minimizing recipes on
    List<Recipe> searchResults = this.DATASOURCE.queryRecipes(15, this.CUISINE,
        this.EXCLUDE_CUISINE, this.DIET, this.INTOLERANCES, this.EXCLUDE_INGREDIENTS,
        null, this.MAX_READY_TIME);
    List<Recipe> goodResults = GeneratorUtilities.filterGoodRatings(searchResults);
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
      String mainIngredient = GeneratorUtilities.findMostAbundantIngredients(firstRecipe,
              1).get(0);
      searchResults = this.DATASOURCE.queryRecipes(30, this.CUISINE,
              this.EXCLUDE_CUISINE,
          this.DIET, this.INTOLERANCES, this.EXCLUDE_INGREDIENTS, mainIngredient,
              this.MAX_READY_TIME);
      goodResults = GeneratorUtilities.filterGoodRatings(searchResults);
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

//  /**
//   * Method to calculate the similarity between two recipes
//   * @param recipe1
//   * @param recipe2
//   * @return
//   */
//  private double calculateSimilarity(Recipe recipe1, Recipe recipe2) {
//    // Method to calculate dissimilarity based on ingredient overlap
//    List<Ingredient> ingredients1 = recipe1.getExtendedIngredients();
//    List<Ingredient> ingredients2 = recipe2.getExtendedIngredients();
//
//    // Calculate the intersection (overlap) of ingredients
//    Set<Ingredient> intersection = new HashSet<>(ingredients1);
//    intersection.retainAll(ingredients2);
//
//    // Calculate the Jaccard similarity coefficient
//    double similarity = (double) intersection.size()
//            / (ingredients1.size() + ingredients2.size() - intersection.size());
//    return similarity;
//  }

  public List<Recipe> personalize() throws DatasourceException {
    //TODO: implement
//    for (Recipe recipe : this.likedRecipes) {
//      List<String> mostImportantLiked = GeneratorUtilities.findMostAbundantIngredients(recipe, 3);
//      List<Recipe> searchResults = this.DATASOURCE.queryRecipes(30, this.CUISINE,
//              this.EXCLUDE_CUISINE,
//              this.DIET, this.INTOLERANCES, this.EXCLUDE_INGREDIENTS, mostImportantLiked.get(0),
//              this.MAX_READY_TIME);
//    }
//    for (Recipe recipe : this.dislikedRecipes) {
//      List<String> mostImportantLiked = GeneratorUtilities.findMostAbundantIngredients(recipe, 3);
//    }
    return new ArrayList<>();
  }


}
