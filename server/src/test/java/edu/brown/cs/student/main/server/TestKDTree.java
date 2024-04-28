package edu.brown.cs.student.main.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeUtilities;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.KDTree.RecipeNode;
import edu.brown.cs.student.main.server.RecommenderAlgorithm.KDTree.RecipeRecommendationKDTree;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

/** A class for testing our K-D Tree implementation. */
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

  /** Tests expected creation of K-D tree from 2 Recipes. */
  @Test
  public void testTreeCreation2Nodes() {
    // set up data
    List<Recipe> recipes = parseMockedRecipes("data/exampleSearchResultLength2.json");
    assertEquals(2, recipes.size());
    RecipeNode node1 = new RecipeNode(recipes.get(0));
    RecipeNode node2 = new RecipeNode(recipes.get(1));

    // initialize tree
    RecipeRecommendationKDTree tree = new RecipeRecommendationKDTree();
    tree.insert(recipes.get(0));
    assertEquals(1, tree.getSize());
    assertEquals(node1, tree.getRoot());

    tree.insert(recipes.get(1)); // compares on 1st coordinate of location: numCuisine
    assertEquals(2, tree.getSize());

    // node1 has location [0, 13, 5], node2 has location [3, 19, 18]
    assertEquals(node2, tree.getRoot().getRight());
  }

  /** Tests expected creation of K-D tree from 4 Recipes. */
  @Test
  public void testTreeCreation4Nodes() {
    // set up data
    List<Recipe> recipes = parseMockedRecipes("data/exampleSearchResultLength4.json");
    assertEquals(4, recipes.size());
    RecipeNode node1 = new RecipeNode(recipes.get(0));
    RecipeNode node2 = new RecipeNode(recipes.get(1));
    RecipeNode node3 = new RecipeNode(recipes.get(2));
    RecipeNode node4 = new RecipeNode(recipes.get(3));

    // initialize tree
    RecipeRecommendationKDTree tree = new RecipeRecommendationKDTree();
    tree.insert(recipes.get(3));
    assertEquals(1, tree.getSize());
    assertEquals(node4, tree.getRoot());

    // node1 has location [0,11,6], node2 has location [0,7,5]
    // node3 has location [0,9,7], node4 has location [1,13,8]

    tree.insert(recipes.get(1)); // compares on 1st coordinate of location: numCuisine
    assertEquals(2, tree.getSize());
    assertEquals(node2, tree.getRoot().getLeft());

    tree.insert(recipes.get(2)); // will go left on numCuisine, then right on numIngredients
    assertEquals(3, tree.getSize());
    assertEquals(node3, tree.getRoot().getLeft().getRight());

    /*
                node4
            node2
               node3
             node1
    */

    tree.insert(recipes.get(0)); // will go L on numCuisine, R on numIngredients, L on numSteps
    assertEquals(4, tree.getSize());
    assertEquals(node1, tree.getRoot().getLeft().getRight().getLeft());
  }

  /** Tests nearest n neighbors search where tree contains less than n nodes. */
  @Test
  public void testAllNeighborsClosest() {
    List<Recipe> allRecipes = parseMockedRecipes("data/exampleSearchResultLength2.json");
    assertEquals(2, allRecipes.size());

    Recipe likedRecipe = parseMockedRecipes("data/exampleSearchResultLength4.json").get(0);

    RecipeRecommendationKDTree tree = new RecipeRecommendationKDTree();
    tree.insert(allRecipes.get(0));
    tree.insert(allRecipes.get(1));

    Set<Recipe> allRecipeSet = new HashSet<>(allRecipes);
    List<Recipe> nearest5NeighborsList = tree.nearestNeighbors(likedRecipe, 5);
    Set<Recipe> nearest5NeighborsSet = new HashSet<>(nearest5NeighborsList);
    assertEquals(2, nearest5NeighborsSet.size());
    assertEquals(allRecipeSet, nearest5NeighborsSet);
  }

  /**
   * Tests that when targetNode is in the tree, it is returned as the closest neighbor to itself.
   */
  @Test
  public void testSelfClosestNeighbor() {
    List<Recipe> allRecipes = parseMockedRecipes("data/exampleSearchResultLength2.json");
    assertEquals(2, allRecipes.size());

    Recipe likedRecipe = allRecipes.get(0);

    RecipeRecommendationKDTree tree = new RecipeRecommendationKDTree();
    tree.insert(allRecipes.get(0));
    tree.insert(allRecipes.get(1));

    List<Recipe> nearest1NeighborList = tree.nearestNeighbors(likedRecipe, 1);
    assertEquals(1, nearest1NeighborList.size());
    assertEquals(likedRecipe, nearest1NeighborList.get(0));
  }

  /** Tests n nearest neighbor search where n > 1 and size of tree > n. */
  @Test
  public void testNearestNeighborsBigTree() {
    List<Recipe> allRecipes = parseMockedRecipes("data/exampleSearchResultLength4.json");
    assertEquals(4, allRecipes.size());

    // initialize tree
    RecipeRecommendationKDTree tree = new RecipeRecommendationKDTree();
    tree.insert(allRecipes.get(3));
    tree.insert(allRecipes.get(1)); // compares on 1st coordinate of location: numCuisine
    tree.insert(allRecipes.get(2)); // will go left on numCuisine, then right on numIngredients
    tree.insert(allRecipes.get(0)); // will go L on numCuisine, R on numIngredients, L on numSteps
    /*
                node4
            node2
               node3
             node1

      node1 has location [0,11,6], node2 has location [0,7,5]
      node3 has location [0,9,7], node4 has location [1,13,8]
    */

    Recipe likedRecipe = parseMockedRecipes("data/exampleSearchResultLength2.json").get(0);
    List<Recipe> nearest2Neighbors = tree.nearestNeighbors(likedRecipe, 2);
    assertEquals(2, nearest2Neighbors.size());
    assertEquals(allRecipes.get(0), nearest2Neighbors.get(0));
    assertEquals(allRecipes.get(3), nearest2Neighbors.get(1));
  }
}
