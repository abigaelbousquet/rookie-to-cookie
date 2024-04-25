package edu.brown.cs.student.main.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeUtilities;
import edu.brown.cs.student.main.server.RecipeData.Datasource.SearchResult;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Ingredient;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A class for unit testing of methods in the Recipe and RecipeUtilities classes.
 */
public class TestRecipe {
  private SearchResult searchResult;
  private Recipe exampleRecipe;

  /**
   * Before each to initialize a sample SearchResult and Recipe to test with, deserialized from
   * a saved json file to avoid unnecessary calls to the Spoonacular API.
   */
  @BeforeEach
  public void getSampleSearchResults() {
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
      this.searchResult = RecipeUtilities.deserializeSearchResult(fileString);
      this.exampleRecipe = this.searchResult.getResults().get(0);
      assertEquals("Creamy Chicken Orzo Soup", this.exampleRecipe.getTitle());

    } catch (IOException | IllegalArgumentException e) {
      System.out.println(e.getMessage());
      fail();
    }
  }

  /**
   * Tests that scaling a Recipe works as expected.
   */
  @Test
  public void testScale() {
    assertEquals(6, this.exampleRecipe.getServings());
    List<Ingredient> ogIngredients = this.exampleRecipe.getExtendedIngredients();
    List<Double> ogAmounts = new ArrayList<>();
    for (Ingredient i : ogIngredients) {
      ogAmounts.add(i.getMeasures().getUs().getAmount());
    }

    this.exampleRecipe.scaleRecipe(12);
    List<Ingredient> scaledIngredients = this.exampleRecipe.getExtendedIngredients();
    List<Double> scaledAmounts = new ArrayList<>();
    for (Ingredient i : scaledIngredients) {
      scaledAmounts.add(i.getMeasures().getUs().getAmount());
    }

    assertEquals(ogAmounts.size(), scaledAmounts.size());
    for (int i = 0; i < ogAmounts.size(); i++) {
      assertEquals(2*(ogAmounts.get(i)), scaledAmounts.get(i));
    }
  }
}
