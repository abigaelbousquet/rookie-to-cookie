package edu.brown.cs.student.main.server.RecommenderAlgorithm.KDTree;

import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;

/**
 * Class describing a Recipe node (for a K-D tree of Recipes).
 */
class RecipeNode {
  Recipe recipe;
  int[] location; // [numCuisines, numIngredients, numSteps]
  RecipeNode left, right;

  public RecipeNode(Recipe recipe) {
    this.recipe = recipe;
    this.location = new int[]{recipe.getNumCuisines(), recipe.getNumIngredients(), recipe.getNumSteps()};
    left = null;
    right = null;
  }
}