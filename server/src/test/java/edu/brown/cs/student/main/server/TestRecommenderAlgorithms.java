package edu.brown.cs.student.main.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.brown.cs.student.main.server.RecipeData.Datasource.DatasourceException;
import edu.brown.cs.student.main.server.RecipeData.Datasource.MockedRecipeSource;
import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeUtilities;
import edu.brown.cs.student.main.server.RecipeData.Datasource.SearchResult;
import edu.brown.cs.student.main.server.RecipeData.Datasource.SpoonacularRecipeSource;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Ingredient;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.MealPlanGenerator;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.MealPlanGeneratorUtilities.GeneratorUtilities;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.Mode;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.RecipeVolumeException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

public class TestRecommenderAlgorithms {

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

  @Test
  public void testMostImportantIngredient() {
    SearchResult mockedResult = deserializedMockedSearchResults("exampleSearchResultsWithKale.json");
    assertEquals(16, mockedResult.getResults().size());

    Recipe firstRecipe = mockedResult.getResults().get(0);
    assertEquals("Superfood Salad with Pan-Seared Salmon", firstRecipe.getTitle());

    List<String> mostImportantIngredients = GeneratorUtilities.findMostAbundantIngredients(firstRecipe, 3);
    assertEquals(3, mostImportantIngredients.size());
    assertEquals("kale", mostImportantIngredients.get(0));
    assertEquals("extra virgin olive oil", mostImportantIngredients.get(1));
    assertEquals("quinoa", mostImportantIngredients.get(2));
  }

  /**
   * Tests minimizeFoodWaste algorithm with mocked data. Ensures that Recipes after the first of
   * the results share the same ingredient of the most important ingredient to the first Recipe of
   * the results.
   */
  @Test
  public void testMinimizeFoodWasteMocked() {
    SearchResult mockedResult = deserializedMockedSearchResults("exampleSearchResultsWithKale.json");
    assertEquals(16, mockedResult.getResults().size());

    MealPlanGenerator generator = null;
    List<Date> dateList = new ArrayList<>();
    try {
      generator = new MealPlanGenerator(new MockedRecipeSource(mockedResult.getResults()),
          Mode.MINIMIZE_FOOD_WASTE, "Monday,wednesday,Sunday", 4, null, null, null, null, 60, null, null, dateList);
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
        assert(kaleIsIngredient);
      }

    } catch (DatasourceException | RecipeVolumeException e) {
      // should never happen given that I'm supplying long enough mocked results
      fail();
    }
  }

  /**
   * Tests minimizeFoodWaste algorithm with real data. Ensures that Recipes after the first of
   * the results share the same ingredient of the most important ingredient to the first Recipe of
   * the results.
   *
   * This is non-deterministic in specifics because the real data source queries a new first
   * recipe every time, but given that we are doing a broad search for only 3 final Recipes,
   * it should pass almost every time.
   *
   * NOTE: this uses 6/5000 of our daily Spoonacular requests, so this should be commented out
   * unless truly necessary.
   */
//  @Test
//  public void testMinimizeFoodWasteReal() {
//    MealPlanGenerator generator = null;
//    try {
//      generator = new MealPlanGenerator(new SpoonacularRecipeSource(),
//          Mode.MINIMIZE_FOOD_WASTE, "Monday,wednesday,Sunday", 4, null, null, null, null, 60, null, null);
//    } catch (ExecutionException | InterruptedException | IOException e) {
//      System.out.println(e);
//      fail();
//    }
//
//    try {
//      List<Recipe> mealPlanRecipes = generator.minimizeFoodWaste();
//      assertEquals(3, mealPlanRecipes.size());
//
//      List<String> mostImportantIngredientList = GeneratorUtilities.findMostAbundantIngredients(mealPlanRecipes.get(0), 1);
//      assertEquals(1, mostImportantIngredientList.size());
//      String mostImportantIngredient = mostImportantIngredientList.get(0);
//
//      for (int i = 1; i < 3; i++) {
//        Recipe nthRecipe = mealPlanRecipes.get(i);
//
//        boolean ingredientPresent = false;
//        for (Ingredient ingredient : nthRecipe.getExtendedIngredients()) {
//          if (ingredient.getName().toLowerCase().contains(mostImportantIngredient)) {
//            ingredientPresent = true;
//            break;
//          }
//        }
//        assert(ingredientPresent);
//      }
//
//    } catch (DatasourceException | RecipeVolumeException e) {
//      fail();
//    }
//  }
}
