package edu.brown.cs.student.main.server.RecipeData.Datasource;

import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import java.util.List;

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
