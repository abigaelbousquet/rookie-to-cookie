package edu.brown.cs.student.main.server.RecommenderAlgorithm.MealPlanGeneratorUtilities;

import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;

/**
 * Record describing a Recipe and its frequency.
 *
 * @param recipe the Recipe of interest
 * @param frequency the int frequency of recipe
 */
public record RecipeFrequencyPair(Recipe recipe, int frequency) {}
