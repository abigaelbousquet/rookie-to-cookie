package Parsing.Recipe;

import Parsing.Recipe.SpoonacularRecipeUtilities.Recipe;
import java.util.List;

/**
 * A class representing a mocked source of Recipes.
 */
public class MockedRecipeSource implements RecipeDatasource {
  List<Recipe> mockedRecipes;

  /**
   * The constructor for the MockedRecipeSource class.
   *
   * @param mockedQueryResults the data to return when querying recipes through this datasource
   */
  public MockedRecipeSource(List<Recipe> mockedQueryResults) {
    this.mockedRecipes = mockedQueryResults;
  }

  /**
   * Returns mocked query results for a query of recipes.
   *
   * @param numRecipes the number of recipes to return (unused in this mocked version)
   * @param cuisine a comma-separated String of the cuisine(s) to query for (options: https://spoonacular.com/food-api/docs#Cuisines)  (unused in this mocked version)
   * @param excludeCuisine a comma-separated String of the cuisines to exclude from queries, a String of comma-separated cuisines (see https://spoonacular.com/food-api/docs#Cuisines) (unused in this mocked version)
   * @param diet a comma-separated String of the diets to restrict results to (options: https://spoonacular.com/food-api/docs#Diets) (unused in this mocked version)
   * @param intolerances a comma-separated String of the intolerances to ban from results (options: https://spoonacular.com/food-api/docs#Intolerances) (unused in this mocked version)
   * @param excludeIngredients a comma-separated String of specific ingredients to ban from results (unused in this mocked version)
   * @param maxReadyTime the maximum prep plus cooking time to filter results with (unused in this mocked version)
   * @return the List of Recipes of mocked data that this MockedRecipeSource was instantiated with
   */
  public List<Recipe> queryRecipes(int numRecipes, String cuisine, String excludeCuisine, String diet, String intolerances, String excludeIngredients, int maxReadyTime) {
    return this.mockedRecipes;
  }
}
