package edu.brown.cs.student.main.server.RecommenderAlgorithm.KDTree;

import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;

/**
 * Record for keeping track of a Recipe and its distance to a target Recipe (in terms of RecipeNodes).
 *
 * @param distance the distance from recipe to the target Recipe[Node]
 * @param recipe the Recipe of interest to compare to the target Recipe
 */
record DistanceRecipePair(double distance, Recipe recipe) {}
