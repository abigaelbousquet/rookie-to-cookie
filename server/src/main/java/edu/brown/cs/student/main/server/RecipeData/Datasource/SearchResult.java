package edu.brown.cs.student.main.server.RecipeData.Datasource;

import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import java.util.List;

public class SearchResult {
    private final int number;
    private final int totalResults;
    private final List<Recipe> results;

    public SearchResult(int number, int totalResults, List<Recipe> results) {
        this.number = number;
        this.totalResults = totalResults;
        this.results = results;
    }

    public int getNumber() {
        return number;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<Recipe> getResults() {
        return results;
    }
}