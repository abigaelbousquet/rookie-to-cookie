package edu.brown.cs.student.main.server.RecipeData;

import edu.brown.cs.student.main.server.RecipeData.Datasource.DatasourceException;
import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeUtilities;
import edu.brown.cs.student.main.server.RecipeData.Datasource.SearchResult;
import edu.brown.cs.student.main.server.RecipeData.Datasource.SpoonacularRecipeSource;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SampleParsing {

  /**
   * Main method for sample deserialization of example recipe and instruction set jsons (in .json
   * file form).
   *
   * @param args n/a
   */
  public static void main(String[] args) {
    /** QUERYING SPOONACULAR API */
    try {
      SpoonacularRecipeSource spoonacular = new SpoonacularRecipeSource();
      List<Recipe> results = spoonacular.queryRecipes(4, null, null, null, null, null, null, 60);
      SearchResult resultsObject = new SearchResult(2, results.size(), results);

      // ********************* SAVING ***********************
      System.out.println("Beginning to save read String.");
      FileWriter jsonWriter = new FileWriter("data/exampleSearchResultLength4Take2.json");
      BufferedWriter bw = new BufferedWriter(jsonWriter);
      String serializedResults = RecipeUtilities.SEARCH_RESULT_JSON_ADAPTER.toJson(resultsObject);
      bw.write(serializedResults);
      bw.close();
      jsonWriter.close();
      System.out.println("Done saving read String.\n");
    } catch (IOException | IllegalArgumentException | DatasourceException e) {
      System.out.println(e.getMessage());
    }
  }
}
