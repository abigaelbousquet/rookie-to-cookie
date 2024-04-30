package edu.brown.cs.student.main.server.RecipeData.Datasource;

import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import java.util.List;

/** A class describing a search result from a RecipeDatasource. */
public class SearchResult {
  private final int number;
  private final int totalResults;
  private final List<Recipe> results;

  /**
   * Constructor for the SearchResult class.
   *
   * @param numberRequested the number of Recipes requested to search for
   * @param totalResults the number of Recipes retrieved in the search
   * @param results the list of Recipes found in the search, or length totalResults
   */
  public SearchResult(int numberRequested, int totalResults, List<Recipe> results) {
    this.number = numberRequested;
    this.totalResults = totalResults;
    this.results = results;
  }

  /**
   * Gets the number of Recipes requested to search for.
   *
   * @return the number of Recipes requested to search for
   */
  public int getNumber() {
    return number;
  }

  /**
   * Gets the number of Recipes retrieved in the search.
   *
   * @return the number of Recipes retrieved in the search
   */
  public int getTotalResults() {
    return totalResults;
  }

  /**
   * Gets the list of Recipes found in the search, or length totalResults.
   *
   * @return the list of Recipes found in the search
   */
  public List<Recipe> getResults() {
    return results;
  }
}
