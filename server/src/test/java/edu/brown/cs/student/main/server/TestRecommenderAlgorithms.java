package edu.brown.cs.student.main.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import edu.brown.cs.student.main.server.RecipeData.Datasource.DatasourceException;
import edu.brown.cs.student.main.server.RecipeData.Datasource.MockedRecipeSource;
import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeUtilities;
import edu.brown.cs.student.main.server.RecipeData.Datasource.SearchResult;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Ingredient;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.MealPlanGenerator;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.MealPlanGeneratorUtilities.GeneratorUtilities;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.Mode;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.RecipeVolumeException;
import edu.brown.cs.student.main.server.storage.FirebaseUtilities;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestRecommenderAlgorithms {

  private static StorageInterface firebaseUtils;

  /** Sets up access to Firebase for each test. */
  @BeforeAll
  public static void setupFirebase() {
    try {
      firebaseUtils = new FirebaseUtilities();
    } catch (IOException e) {
      System.out.println("Unable to connect to firebase: " + e);
      fail();
    }
  }

  /**
   * Helper method for deserializing a mocked SearchResult from a filepath.
   *
   * @param fileName the file name of the mocked, serialized SearchResult json in data folder
   * @return the deserialized SearchResult of what is at data/filename
   */
  private static SearchResult deserializedMockedSearchResults(String fileName) {
    SearchResult deserializedResult = null;
    try {
      // ***************** READING THE FILE *****************
      FileReader jsonReader = new FileReader("data/" + fileName);
      BufferedReader br = new BufferedReader(jsonReader);
      String fileString = "";

      String line = br.readLine();
      while (line != null) {
        fileString = fileString + line;
        line = br.readLine();
      }
      jsonReader.close();

      // ****************** DESERIALIZING *******************
      deserializedResult = RecipeUtilities.deserializeSearchResult(fileString);
    } catch (IOException e) {
      System.out.println(e);
      fail();
    }
    return deserializedResult;
  }

  /** Tests that the most important ingredients are computed as expected for a mocked Recipe. */
  @Test
  public void testMostImportantIngredient() {
    SearchResult mockedResult =
        deserializedMockedSearchResults("exampleSearchResultsWithKale.json");
    assertEquals(16, mockedResult.getResults().size());

    Recipe firstRecipe = mockedResult.getResults().get(0);
    assertEquals("Superfood Salad with Pan-Seared Salmon", firstRecipe.getTitle());

    List<String> mostImportantIngredients =
        GeneratorUtilities.findMostAbundantIngredients(firstRecipe, 3);
    assertEquals(3, mostImportantIngredients.size());
    assertEquals("kale", mostImportantIngredients.get(0));
    assertEquals("extra virgin olive oil", mostImportantIngredients.get(1));
    assertEquals("quinoa", mostImportantIngredients.get(2));
  }

  /**
   * Tests minimizeFoodWaste algorithm with mocked data. Ensures that Recipes after the first of the
   * results share the same ingredient of the most important ingredient to the first Recipe of the
   * results.
   */
  @Test
  public void testMinimizeFoodWasteMocked() {
    SearchResult mockedResult =
        deserializedMockedSearchResults("exampleSearchResultsWithKale.json");
    assertEquals(16, mockedResult.getResults().size());

    MealPlanGenerator generator = null;
    try {
      generator =
          new MealPlanGenerator(
              new MockedRecipeSource(mockedResult.getResults()),
              Mode.MINIMIZE_FOOD_WASTE,
              "Monday,wednesday,Sunday",
              4,
              null,
              null,
              null,
              null,
              60,
              null,
              null,
              null);
    } catch (ExecutionException | InterruptedException | IOException e) {
      System.out.println(e);
      fail();
    }

    try {
      List<Recipe> mealPlanRecipes = generator.minimizeFoodWaste();
      assertEquals(3, mealPlanRecipes.size());
      Recipe firstRecipe = mealPlanRecipes.get(0);
      assertEquals("Superfood Salad with Pan-Seared Salmon", firstRecipe.getTitle());

      for (int i = 1; i < 3; i++) {
        Recipe nthRecipe = mealPlanRecipes.get(i);

        boolean kaleIsIngredient = false;
        for (Ingredient ingredient : nthRecipe.getExtendedIngredients()) {
          if (ingredient.getName().toLowerCase().contains("kale")) {
            kaleIsIngredient = true;
            break;
          }
        }
        assert (kaleIsIngredient);
      }

    } catch (DatasourceException | RecipeVolumeException e) {
      // should never happen given that I'm supplying long enough mocked results
      fail();
    }
  }

  /**
   * Tests minimizeFoodWaste algorithm with real data. Ensures that Recipes after the first of the
   * results share the same ingredient of the most important ingredient to the first Recipe of the
   * results.
   *
   * <p>This is non-deterministic in specifics because the real data source queries a new first
   * recipe every time, but given that we are doing a broad search for only 3 final Recipes, it
   * should pass almost every time.
   *
   * <p>NOTE: this uses 6/5000 of our daily Spoonacular requests, so this should be commented out
   * unless truly necessary.
   */
  //    @Test
  //    public void testMinimizeFoodWasteReal() {
  //      MealPlanGenerator generator = null;
  //      try {
  //        generator =
  //            new MealPlanGenerator(
  //                new SpoonacularRecipeSource(),
  //                Mode.MINIMIZE_FOOD_WASTE,
  //                "Monday,wednesday,Sunday",
  //                4,
  //                null,
  //                null,
  //                null,
  //                null,
  //                60,
  //                null,
  //                null,
  //                null,
  //                null);
  //      } catch (ExecutionException | InterruptedException | IOException e) {
  //        System.out.println(e);
  //        fail();
  //      }
  //
  //      try {
  //        List<Recipe> mealPlanRecipes = generator.minimizeFoodWaste();
  //        assertEquals(3, mealPlanRecipes.size());
  //
  //        List<String> mostImportantIngredientList =
  //            GeneratorUtilities.findMostAbundantIngredients(mealPlanRecipes.get(0), 1);
  //        assertEquals(1, mostImportantIngredientList.size());
  //        String mostImportantIngredient = mostImportantIngredientList.get(0);
  //
  //        for (int i = 1; i < 3; i++) {
  //          Recipe nthRecipe = mealPlanRecipes.get(i);
  //
  //          boolean ingredientPresent = false;
  //          for (Ingredient ingredient : nthRecipe.getExtendedIngredients()) {
  //            if (ingredient.getName().toLowerCase().contains(mostImportantIngredient)) {
  //              ingredientPresent = true;
  //              break;
  //            }
  //          }
  //          assert(ingredientPresent);
  //        }
  //
  //      } catch (DatasourceException | RecipeVolumeException e) {
  //        fail();
  //      }
  //    }

  /**
   * Tests that the correct number of Recipes are generated for a user without any liked or disliked
   * Recipes (and inherently checks that this situation doesn't cause any errors).
   */
  @Test
  public void testPersonalizedMockedNoLikedNoDisliked() {
    SearchResult mockedResult =
        deserializedMockedSearchResults("exampleSearchResultsWithKale.json");
    assertEquals(16, mockedResult.getResults().size());

    MealPlanGenerator generator = null;
    try {
      generator =
          new MealPlanGenerator(
              new MockedRecipeSource(mockedResult.getResults()),
              Mode.PERSONALIZED,
              "Monday,tuesday",
              2,
              null,
              null,
              null,
              null,
              60,
              firebaseUtils,
              "test_user_0liked_0disliked",
              null);
    } catch (ExecutionException | InterruptedException | IOException e) {
      System.out.println(e);
      fail();
    }

    List<Recipe> results = null;
    try {
      results = generator.personalized();
    } catch (DatasourceException | RecipeVolumeException e) {
      // should never happen given we are providing successful mocked data
      System.out.println(e);
      fail();
    }

    assertEquals(2, results.size());
  }

  /**
   * Test personalized algorithm with a mocked query result and user who only has disliked Recipes
   * associated with their uid. Tests that the nearest neighbors to the disliked Recipe are not
   * returned in results.
   */
  @Test
  public void testPersonalizedMockedDislikesOnly() {
    SearchResult mockedResult = deserializedMockedSearchResults("exampleQualityResultLength6.json");
    assertEquals(6, mockedResult.getResults().size());

    MealPlanGenerator generator = null;
    try {
      generator =
          new MealPlanGenerator(
              new MockedRecipeSource(mockedResult.getResults()),
              Mode.PERSONALIZED,
              "monday,tuesday",
              4,
              null,
              null,
              null,
              null,
              60,
              firebaseUtils,
              "test_user_disliked_only",
              null);
    } catch (ExecutionException | InterruptedException | IOException e) {
      System.out.println(e);
      fail();
    }

    List<Recipe> results = null;
    try {
      results = generator.personalized();
    } catch (DatasourceException | RecipeVolumeException e) {
      // should never happen given we are providing successful mocked data
      System.out.println(e);
      fail();
    }

    assertEquals(2, results.size());

    for (Recipe recipe : results) {
      if ((recipe.getId() == 1154142) || (recipe.getId() == 487780)) {
        fail();
      }
    }
  }

  /**
   * Test personalized algorithm with a mocked query result and user who only has disliked Recipes
   * associated with their uid.
   *
   * <p>Tests that the nearest neighbors to the disliked Recipe are never returned in results with
   * SEVERAL DIFFERENT ORDERS OF CANDIDATE RECIPES (different trees) to verify consistent results.
   */
  @Test
  public void testPersonalizedMockedDislikesOnlyManyTrees() {
    SearchResult mockedResult = deserializedMockedSearchResults("exampleQualityResultLength6.json");
    assertEquals(6, mockedResult.getResults().size());

    List<Recipe> mockedResultsList = mockedResult.getResults();
    ;
    for (int i = 0; i < 6; i++) {
      // change order of the mocked results every time past the first
      if (i > 0) {
        // move first Recipe in the list to the end of the list
        Recipe firstRecipe = mockedResultsList.remove(0);
        mockedResultsList.add(firstRecipe);
      }

      MealPlanGenerator generator = null;
      try {
        generator =
            new MealPlanGenerator(
                new MockedRecipeSource(mockedResultsList),
                Mode.PERSONALIZED,
                "monday,tuesday",
                4,
                null,
                null,
                null,
                null,
                60,
                firebaseUtils,
                "test_user_disliked_only",
                null);
      } catch (ExecutionException | InterruptedException | IOException e) {
        System.out.println(e);
        fail();
      }

      List<Recipe> results = null;
      try {
        results = generator.personalized();
      } catch (DatasourceException | RecipeVolumeException e) {
        // should never happen given we are providing successful mocked data
        System.out.println(e);
        fail();
      }

      assertEquals(2, results.size());

      for (Recipe recipe : results) {
        if ((recipe.getId() == 1154142) || (recipe.getId() == 487780)) {
          System.out.println("Recommendation included one of nearest neighbors to disliked recipe");
          System.out.println("Mocked search results tree was made from: " + mockedResultsList);
          fail();
        }
      }
    }
  }

  /**
   * Test personalized algorithm with a mocked query result and user who only has liked Recipes
   * associated with their uid. Tests that the nearest neighbors to the liked Recipe are returned as
   * results.
   */
  @Test
  public void testPersonalizedMockedLikesOnly() {
    SearchResult mockedResult = deserializedMockedSearchResults("exampleQualityResultLength6.json");
    assertEquals(6, mockedResult.getResults().size());

    MealPlanGenerator generator = null;
    try {
      generator =
          new MealPlanGenerator(
              new MockedRecipeSource(mockedResult.getResults()),
              Mode.PERSONALIZED,
              "monday,tuesday",
              4,
              null,
              null,
              null,
              null,
              60,
              firebaseUtils,
              "test_user_liked_only",
              null);
    } catch (ExecutionException | InterruptedException | IOException e) {
      System.out.println(e);
      fail();
    }

    List<Recipe> results = null;
    try {
      results = generator.personalized();
    } catch (DatasourceException | RecipeVolumeException e) {
      // should never happen given we are providing successful mocked data
      System.out.println(e);
      fail();
    }

    assertEquals(2, results.size());

    boolean expectedRecipeFound = false;
    for (Recipe recipe : results) {
      if (recipe.getId() == 580817) {
        expectedRecipeFound = true;
        break;
      }
    }
    assert (expectedRecipeFound);
  }

  /**
   * Test personalized algorithm with a mocked query result and user who only has liked Recipes
   * associated with their uid.
   *
   * <p>Tests that the nearest overall neighbor to the liked Recipes are ALWAYS returned in results
   * with SEVERAL DIFFERENT ORDERS OF CANDIDATE RECIPES (different trees) to verify consistency.
   */
  @Test
  public void testPersonalizedMockedLikesOnlyManyTrees() {
    SearchResult mockedResult = deserializedMockedSearchResults("exampleQualityResultLength6.json");
    assertEquals(6, mockedResult.getResults().size());

    List<Recipe> mockedResultsList = mockedResult.getResults();
    ;
    for (int i = 0; i < 6; i++) {
      // change order of the mocked results every time past the first
      if (i > 0) {
        // move first Recipe in the list to the end of the list
        Recipe firstRecipe = mockedResultsList.remove(0);
        mockedResultsList.add(firstRecipe);
      }

      MealPlanGenerator generator = null;
      try {
        generator =
            new MealPlanGenerator(
                new MockedRecipeSource(mockedResult.getResults()),
                Mode.PERSONALIZED,
                "monday,tuesday",
                4,
                null,
                null,
                null,
                null,
                60,
                firebaseUtils,
                "test_user_liked_only",
                null);
      } catch (ExecutionException | InterruptedException | IOException e) {
        System.out.println(e);
        fail();
      }

      List<Recipe> results = null;
      try {
        results = generator.personalized();
      } catch (DatasourceException | RecipeVolumeException e) {
        // should never happen given we are providing successful mocked data
        System.out.println(e);
        fail();
      }

      assertEquals(2, results.size());

      boolean expectedRecipeFound = false;
      for (Recipe recipe : results) {
        if (recipe.getId() == 580817) {
          expectedRecipeFound = true;
          break;
        }
      }
      assert (expectedRecipeFound);
    }
  }

  /**
   * Tests that the personalized algorithm still behaves as expected when good results is not
   * exactly NUM_DAYS_TO_PLAN * 3.
   */
  @Test
  public void testPersonalized7Results() {
    SearchResult mockedResult = deserializedMockedSearchResults("exampleQualityResultLength7.json");
    assertEquals(7, mockedResult.getResults().size());

    MealPlanGenerator generator = null;
    try {
      generator =
          new MealPlanGenerator(
              new MockedRecipeSource(mockedResult.getResults()),
              Mode.PERSONALIZED,
              "monday,tuesday",
              4,
              null,
              null,
              null,
              null,
              60,
              firebaseUtils,
              "test_user_disliked_only",
              null);
    } catch (ExecutionException | InterruptedException | IOException e) {
      System.out.println(e);
      fail();
    }

    List<Recipe> results = null;
    try {
      results = generator.personalized();
    } catch (DatasourceException | RecipeVolumeException e) {
      // should never happen given we are providing successful mocked data
      System.out.println(e);
      fail();
    }

    assertEquals(2, results.size());

    for (Recipe recipe : results) {
      if ((recipe.getId() == 1154142) || (recipe.getId() == 487780)) {
        fail();
      }
    }
  }

  /**
   * Tests that RecipeVolumeException is thrown if not enough Recipes can be queried for personalize
   * method.
   */
  @Test
  public void testInsufficientResultsPersonalized() throws DatasourceException {
    SearchResult mockedResult = deserializedMockedSearchResults("exampleQualityResultLength7.json");
    assertEquals(7, mockedResult.getResults().size());

    try {
      MealPlanGenerator generator =
          new MealPlanGenerator(
              new MockedRecipeSource(mockedResult.getResults()),
              Mode.PERSONALIZED,
              "monday,tuesday,wednesday,thursday,friday,saturday,sunday",
              4,
              null,
              null,
              null,
              null,
              60,
              firebaseUtils,
              "test_user_disliked_only",
              null);

      assertThrows(RecipeVolumeException.class, () -> generator.personalized());
    } catch (ExecutionException | InterruptedException | IOException e) {
      System.out.println(e);
      fail();
    }
  }

  /**
   * Tests that RecipeVolumeException is thrown if not enough Recipes can be queried for
   * minimizeFoodWaste method.
   */
  @Test
  public void testInsufficientResultsMinimizeFoodWaste() throws DatasourceException {
    SearchResult mockedResult = deserializedMockedSearchResults("exampleQualityResultLength6.json");
    assertEquals(6, mockedResult.getResults().size());

    try {
      MealPlanGenerator generator =
          new MealPlanGenerator(
              new MockedRecipeSource(mockedResult.getResults()),
              Mode.MINIMIZE_FOOD_WASTE,
              "monday,tuesday,wednesday,thursday,friday,saturday,sunday",
              4,
              null,
              null,
              null,
              null,
              60,
              firebaseUtils,
              "test_user_disliked_only",
              null);

      assertThrows(RecipeVolumeException.class, () -> generator.minimizeFoodWaste());
    } catch (ExecutionException | InterruptedException | IOException e) {
      System.out.println(e);
      fail();
    }
  }

  /**
   * Tests that expected Recipes are recommended by the personalized method for a mocked user with
   * both a liked and disliked Recipe associated with their uid.
   */
  @Test
  public void testPersonalizedMockedLikesAndDislikes() {
    SearchResult mockedResult = deserializedMockedSearchResults("exampleQualityResultLength6.json");
    assertEquals(6, mockedResult.getResults().size());

    MealPlanGenerator generator = null;
    try {
      generator =
          new MealPlanGenerator(
              new MockedRecipeSource(mockedResult.getResults()),
              Mode.PERSONALIZED,
              "monday,wednesday",
              4,
              null,
              null,
              null,
              null,
              60,
              firebaseUtils,
              "test_user_1liked_1disliked",
              null);
    } catch (ExecutionException | InterruptedException | IOException e) {
      System.out.println(e);
      fail();
    }

    List<Recipe> results = null;
    try {
      results = generator.personalized();
    } catch (DatasourceException | RecipeVolumeException e) {
      // should never happen given we are providing successful mocked data
      System.out.println(e);
      fail();
    }

    assertEquals(2, results.size());

    for (Recipe recipe : results) {
      if (!((recipe.getId() == 1154142) || (recipe.getId() == 487780))) {
        fail();
      }
    }
  }

  /**
   * Tests that expected Recipes are recommended by the personalized method for a mocked user with
   * both a liked and disliked Recipe associated with their uid.
   *
   * <p>Tests that the expected Recipes (determined by hand) are returned by the recommender with
   * SEVERAL DIFFERENT ORDERS OF CANDIDATE RECIPES (different trees) to verify consistency.
   */
  @Test
  public void testPersonalizedMockedLikesAndDislikesManyTrees() {
    SearchResult mockedResult = deserializedMockedSearchResults("exampleQualityResultLength6.json");
    assertEquals(6, mockedResult.getResults().size());

    List<Recipe> mockedResultsList = mockedResult.getResults();
    ;
    for (int i = 0; i < 6; i++) {
      // change order of the mocked results every time past the first
      if (i > 0) {
        // move first Recipe in the list to the end of the list
        Recipe firstRecipe = mockedResultsList.remove(0);
        mockedResultsList.add(firstRecipe);
      }

      MealPlanGenerator generator = null;
      try {
        generator =
            new MealPlanGenerator(
                new MockedRecipeSource(mockedResult.getResults()),
                Mode.PERSONALIZED,
                "monday,wednesday",
                4,
                null,
                null,
                null,
                null,
                60,
                firebaseUtils,
                "test_user_1liked_1disliked",
                null);
      } catch (ExecutionException | InterruptedException | IOException e) {
        System.out.println(e);
        fail();
      }

      List<Recipe> results = null;
      try {
        results = generator.personalized();
      } catch (DatasourceException | RecipeVolumeException e) {
        // should never happen given we are providing successful mocked data
        System.out.println(e);
        fail();
      }

      assertEquals(2, results.size());

      for (Recipe recipe : results) {
        if (!((recipe.getId() == 1154142) || (recipe.getId() == 487780))) {
          fail();
        }
      }
    }
  }
}
