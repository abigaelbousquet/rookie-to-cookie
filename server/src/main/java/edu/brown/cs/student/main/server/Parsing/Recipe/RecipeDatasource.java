package edu.brown.cs.student.main.server.Parsing.Recipe;

import java.util.List;

import edu.brown.cs.student.main.server.Parsing.Recipe.SpoonacularRecipeUtilities.Recipe;

/** An interface describing a datasource for recipes. */
public interface RecipeDatasource {

  /** Queries recipe database for a certain number of Recipes. */
  List<Recipe> queryRecipes(
      int numRecipes,
      String cuisine,
      String excludeCuisine,
      String diet,
      String intolerances,
      String excludeIngredients,
      String includeIngredients,
      int maxReadyTime)
      throws DatasourceException;
}
