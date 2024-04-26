package edu.brown.cs.student.main.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeUtilities;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.KDTree.RecipeRecommendationKDTree;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * A class for testing our K-D Tree implementation.
 */
public class TestKDTree {

  /**
   * Helper method to get a List of Recipes from a saved json file for use testing.
   *
   * @param filepath the filepath of an example json search result's results to gather Recipes from
   * @return a list of Recipe objects from the
   */
  private static List<Recipe> parseMockedRecipes(String filepath) {
    List<Recipe> mockedResult = null;
    try {
      // ***************** READING THE FILE *****************
      FileReader jsonReader = new FileReader(filepath);
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
    } catch (IOException | IllegalArgumentException e) {
      System.out.println(e.getMessage());
      fail();
    }
    return mockedResult;
  }

  @Test
  public void testTreeCreation() {
    List<Recipe> recipes = parseMockedRecipes("data/exampleSearchResultLength2.json");
    assertEquals(2, recipes.size());

    RecipeRecommendationKDTree tree = new RecipeRecommendationKDTree();
    tree.insert(recipes.get(0));
    assertEquals(1, tree.getSize());

    tree.insert(recipes.get(1));
    assertEquals(2, tree.getSize());

    // TODO: finish this test by checking node attributes & ordering in tree
  }

  // TODO: test creation with more nodes

  // TODO: test 2 nearest neighbors maybe where size of tree is > 2, small example with mocked data

  // TODO: test where tree doesn't contain n nodes, check returns all nodes
}
