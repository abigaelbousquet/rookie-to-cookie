package edu.brown.cs.student.main.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import edu.brown.cs.student.main.server.RecipeData.Datasource.DatasourceException;
import edu.brown.cs.student.main.server.RecipeData.Datasource.MockedRecipeSource;
import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeUtilities;
import edu.brown.cs.student.main.server.RecipeData.Datasource.SpoonacularRecipeSource;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

/** A class for unit testing the RecipeDatasources (Mocked and Real). */
public class TestRecipeDatasource {

  /** Tests querying mocked source. */
  @Test
  public void testMockedQuery() {
    List<Recipe> mockedResult = null;
    try {
      // ***************** READING THE FILE *****************
      FileReader jsonReader = new FileReader("data/exampleSearchResultLength2.json");
      BufferedReader br = new BufferedReader(jsonReader);
      String fileString = "";

      String line = br.readLine();
      while (line != null) {
        fileString = fileString + line;
        line = br.readLine();
      }
      jsonReader.close();

      // ****************** DESERIALIZING *******************
      mockedResult = RecipeUtilities.deserializeSearchResult(fileString).getResults();
      assertEquals(2, mockedResult.size());

    } catch (IOException | IllegalArgumentException e) {
      System.out.println(e.getMessage());
      fail();
    }

    MockedRecipeSource mockedSrc = new MockedRecipeSource(mockedResult);

    // same mocked result regardless of query parameters
    assertEquals(mockedResult, mockedSrc.queryRecipes(2, null, null, null, null, null, null, 60));
    assertEquals(
        mockedResult, mockedSrc.queryRecipes(5, "american", null, null, "dairy", null, null, 10));
  }

  /**
   * Tests error handling of an invalid number of recipe param in query.
   *
   * @throws DatasourceException if another unexpected exception is thrown
   */
  @Test
  public void testInvalidQuery() throws DatasourceException {
    SpoonacularRecipeSource src = new SpoonacularRecipeSource();
    assertThrows(
        IllegalArgumentException.class,
        () -> src.queryRecipes(-1, null, null, null, null, null, null, 60));
    assertThrows(
        IllegalArgumentException.class,
        () -> src.queryRecipes(150, null, null, null, null, null, null, 60));
  }

  /** TODO: basic test of real datasource query, returns >0 results */
  /**
   * Tests basic real datasource query, just to double-check that it can be queried.
   *
   * <p>NOTE: this uses 3/5000 of our daily Spoonacular requests, so this should be commented out
   * unless truly necessary.
   */
  //  @Test
  //  public void testRealQuery() {
  //    SpoonacularRecipeSource src = new SpoonacularRecipeSource();
  //    try {
  //      List<Recipe> results = src.queryRecipes(2, "american", null, null, null, null, null, 60);
  //      assertEquals(2, results.size());
  //      for (Recipe r : results) {
  //        assert(r.getReadyInMinutes() <= 60);
  //        assert(r.getCuisines().contains("American"));
  //      }
  //    } catch (DatasourceException e) {
  //      System.out.println(e);
  //      fail();
  //    }
  //  }
}
