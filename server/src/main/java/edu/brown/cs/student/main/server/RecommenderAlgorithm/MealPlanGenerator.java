package edu.brown.cs.student.main.server.RecommenderAlgorithm;

import static org.testng.AssertJUnit.assertEquals;

import edu.brown.cs.student.main.server.RecipeData.Datasource.DatasourceException;
import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeDatasource;
import edu.brown.cs.student.main.server.RecipeData.MealPlan;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.MealPlanGeneratorUtilities.GeneratorUtilities;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.MealPlanGeneratorUtilities.RecipeFrequencyPair;
import edu.brown.cs.student.main.server.storage.FirebaseUtilities;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutionException;

/** A class describing a MealPlanGenerator and its associated algorithmic methods. */
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
  private List<Recipe> likedRecipes = null;
  private List<Recipe> dislikedRecipes = null;
  private final StorageInterface FIREBASE_DATA;
  private final String UID;

  /**
   * Constructor for the MealPlanGenerator class to initialize private instance variables for the
   * class
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
  public MealPlanGenerator(
      RecipeDatasource recipeSource,
      Mode mode,
      String daysOfWeek,
      int servings,
      String cuisine,
      String excludeCuisine,
      String diet,
      String intolerances,
      int maxReadyTime,
      StorageInterface firebaseData,
      String uid)
      throws ExecutionException, InterruptedException, IOException {
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
    if (this.FIREBASE_DATA != null) {
      this.dislikedRecipes =
          FirebaseUtilities.convertLikedOrDislikedRecipes(
              firebaseData.getCollection(uid, "disliked recipes"));
      this.likedRecipes =
          FirebaseUtilities.convertLikedOrDislikedRecipes(
              firebaseData.getCollection(uid, "liked recipes"));
    }
  }

  /**
   * Generates a MealPlan based on the criteria and mode this MealPlanGenerator was instantiated
   * with.
   *
   * @return a MealPlan fitting this MealPlanGenerator's criteria recommended in its mode
   * @throws DatasourceException if unsuccessful in querying the recipe datasource for any recipes
   * @throws RecipeVolumeException if unable to find NUM_DAYS_TO_PLAN quality recipes fitting this
   *     generator's criteria
   */
  public MealPlan generatePlan() throws DatasourceException, RecipeVolumeException {
    List<Recipe> weekOfRecipes;
    if (this.recipes == Mode.MINIMIZE_FOOD_WASTE) {
      weekOfRecipes = this.minimizeFoodWaste();
    } else {
      weekOfRecipes = this.personalized();
    }
    List<Recipe> weekOfRecipesScaledServings = new ArrayList<>(weekOfRecipes);
    for (Recipe recipe : weekOfRecipesScaledServings) {
      recipe.scaleRecipe(this.REQUESTED_SERVINGS);
    }
    return this.createMealPlanFromRecipeList(weekOfRecipesScaledServings);
  }

  /**
   * Method to create a meal plan object from a list of recipes.
   *
   * @param recipes
   * @return
   */
  private MealPlan createMealPlanFromRecipeList(List<Recipe> recipes) {
    Recipe[] orderedWeekOfRecipes = {null, null, null, null, null, null, null}; // index 0 is Sunday
    for (int i = 0; i < 7; i++) {
      if (this.DAYS_TO_PLAN[i]) {
        orderedWeekOfRecipes[i] = recipes.remove(0);
      }
    }
    return new MealPlan(
        orderedWeekOfRecipes[0],
        orderedWeekOfRecipes[1],
        orderedWeekOfRecipes[2],
        orderedWeekOfRecipes[3],
        orderedWeekOfRecipes[4],
        orderedWeekOfRecipes[5],
        orderedWeekOfRecipes[6]);
  }

  /**
   * Method to parse the intolerances and allergens from a cs string
   *
   * @param fullString
   */
  private void setIntolerancesAndAllergens(String fullString) {
    if (fullString == null || fullString.isEmpty()) {
      return;
    }
    String[] supportedIntolerances = {
      "dairy",
      "egg",
      "gluten",
      "peanut",
      "sesame",
      "seafood",
      "shellfish",
      "soy",
      "sulfite",
      "tree%20nut",
      "wheat"
    };
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
      this.EXCLUDE_INGREDIENTS = excludeIngredients.substring(0, excludeIngredients.length() - 1);
    }
  }

  /**
   * Method to parse the given CSV string into a boolean array representing which days of the week
   * have recipes.
   *
   * @param daysOfWeek
   * @return
   */
  private boolean[] parseDays(String daysOfWeek) {
    boolean[] booleanArray = {false, false, false, false, false, false, false};
    String[] daysOfWeekArray = daysOfWeek.toLowerCase().split(",");
    for (int i = 0; i < daysOfWeekArray.length; i++) {
      switch (daysOfWeekArray[i]) {
        case "sunday":
          booleanArray[0] = true;
          break;
        case "monday":
          booleanArray[1] = true;
          break;
        case "tuesday":
          booleanArray[2] = true;
          break;
        case "wednesday":
          booleanArray[3] = true;
          break;
        case "thursday":
          booleanArray[4] = true;
          break;
        case "friday":
          booleanArray[5] = true;
          break;
        case "saturday":
          booleanArray[6] = true;
          break;
        default:
          throw new IllegalArgumentException(
              "Provided unexpected day of week String. Expected un-abbreviated String name.");
      }
    }
    this.NUM_DAYS_TO_PLAN = daysOfWeekArray.length;
    return booleanArray;
  }

  /**
   * Queries this Generator's data source for a List of Recipes fitting this Generator's base
   * criteria and filters results for quality Recipes based on Spoonacular scores.
   *
   * @param n the number of Recipes to initially query
   * @param minResultSize the minimum number of Recipes in an acceptable result
   * @param includeIngredients a String describing optional extra specification of ingredients to
   *     require in query results
   * @returns returns a List of at least this.NUM_DAYS_TO_PLAN quality Recipes
   * @throws DatasourceException if unsuccessful in querying Spoonacular for any recipes
   * @throws RecipeVolumeException if unable to find NUM_DAYS_TO_PLAN quality recipes fitting this
   *     generator's criteria
   */
  private List<Recipe> queryQualitySearchResults(
      int n, int minResultSize, String includeIngredients)
      throws DatasourceException, RecipeVolumeException {
    List<Recipe> searchResults =
        this.DATASOURCE.queryRecipes(
            n,
            this.CUISINE,
            this.EXCLUDE_CUISINE,
            this.DIET,
            this.INTOLERANCES,
            this.EXCLUDE_INGREDIENTS,
            includeIngredients,
            this.MAX_READY_TIME);
    List<Recipe> goodResults = GeneratorUtilities.filterGoodRatings(searchResults);
    int numGoodResults = goodResults.size();
    if (numGoodResults < minResultSize) {
      throw new RecipeVolumeException(
          "Caller requested "
              + minResultSize
              + " recipes, but only "
              + numGoodResults
              + " quality recipes fitting their needs "
              + "could be found.");
    } else {
      return goodResults;
    }
  }

  /**
   * Generates a list of Recipes fitting user criteria this MealPlanGenerator was constructed with,
   * prioritizing minimizing food waste in the synthesized list of Recipes returned.
   *
   * @return a list of NUM_DAYS_TO_PLAN Recipes fitting the criteria this was instantiated with
   * @throws DatasourceException if unsuccessful in querying Spoonacular for any recipes
   * @throws RecipeVolumeException if unable to find NUM_DAYS_TO_PLAN quality recipes fitting this
   *     generator's criteria
   */
  public List<Recipe> minimizeFoodWaste() throws DatasourceException, RecipeVolumeException {
    // PART 1 - get a starting recipe to base the rest of the food-waste-minimizing recipes on
    List<Recipe> goodResults =
        this.queryQualitySearchResults(this.NUM_DAYS_TO_PLAN * 3, this.NUM_DAYS_TO_PLAN, null);
    Recipe firstRecipe = goodResults.get(0);
    List<Recipe> algorithmResults = new ArrayList<>();
    algorithmResults.add(firstRecipe);

    if (this.NUM_DAYS_TO_PLAN > 1) {
      // PART 2 - fill in the rest of the week's recipes sharing the main ingredient of the first
      String mainIngredient = GeneratorUtilities.findMostAbundantIngredients(firstRecipe, 1).get(0);
      int remainingRecipesNeeded = this.NUM_DAYS_TO_PLAN - 1;
      goodResults =
          this.queryQualitySearchResults(
              remainingRecipesNeeded * 3, remainingRecipesNeeded, mainIngredient);
      int resultsLength = goodResults.size();

      // taking from the end of results is mostly for testing with mocked [same] query results
      algorithmResults.addAll(
          goodResults.subList(resultsLength - remainingRecipesNeeded, resultsLength));
    }
    assert (algorithmResults.size() == this.NUM_DAYS_TO_PLAN);
    return algorithmResults;
  }

  /**
   * Generates a list of Recipes fitting user criteria this MealPlanGenerator was constructed with,
   * prioritizing minimizing Recipes similar to the user's disliked recipes and maximizing Recipes
   * similar to the user's liked recipes.
   *
   * @return a list of NUM_DAYS_TO_PLAN Recipes fitting the criteria this was instantiated with
   * @throws DatasourceException if unsuccessful in querying Spoonacular for any recipes
   * @throws RecipeVolumeException if unable to find NUM_DAYS_TO_PLAN quality recipes fitting this
   *     generator's criteria
   */
  public List<Recipe> personalized() throws DatasourceException, RecipeVolumeException {
    // PART 1 - get a starting list of quality recipes fitting user needs
    List<Recipe> goodResults =
        this.queryQualitySearchResults(this.NUM_DAYS_TO_PLAN * 6, this.NUM_DAYS_TO_PLAN * 3, null);

    // TODO: if the above throws a RecipeVolumeException, should I check if just NUM_DAYS_TO_PLAN
    // recipes are available, and if so return that?

    if (this.dislikedRecipes.size() > 0) {
      // PART 2 - eliminate Recipes most similar to user's disliked Recipes
      PriorityQueue<RecipeFrequencyPair> badQueue =
          GeneratorUtilities.getNearestNeighborsToListRecipes(
              goodResults, this.dislikedRecipes, goodResults.size() / 3);

      // ELIMINATE these nearest neighbors
      for (RecipeFrequencyPair recipeWithFrequency : badQueue) {
        goodResults.remove(recipeWithFrequency.recipe());
      }
      assert (goodResults.size() >= this.NUM_DAYS_TO_PLAN);
    }

    List<Recipe> bestRecipes = new ArrayList<>();
    if (this.likedRecipes.size() > 0) {
      // PART 3 - get the top NUM_DAYS_TO_PLAN Recipes most similar to the most liked Recipes
      PriorityQueue<RecipeFrequencyPair> goodQueue =
          GeneratorUtilities.getNearestNeighborsToListRecipes(
              goodResults, this.likedRecipes, this.NUM_DAYS_TO_PLAN);

      // SAVE these nearest neighbors
      for (RecipeFrequencyPair recipeWithFrequency : goodQueue) {
        bestRecipes.add(recipeWithFrequency.recipe());
      }
    } else {
      for (int i = 0; i < this.NUM_DAYS_TO_PLAN; i++) {
        // take first NUM_DAYS_TO_PLAN Recipes in remaining Recipes in goodResults
        bestRecipes.add(goodResults.get(i));
      }
    }

    assertEquals(this.NUM_DAYS_TO_PLAN, bestRecipes.size());
    return bestRecipes;
  }
}
