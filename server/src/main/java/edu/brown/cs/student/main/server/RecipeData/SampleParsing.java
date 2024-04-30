package edu.brown.cs.student.main.server.RecipeData;

import static edu.brown.cs.student.main.server.RecommenderAlgorithm.MealPlanGeneratorUtilities.GeneratorUtilities.findMostAbundantIngredients;

import edu.brown.cs.student.main.server.RecipeData.Datasource.DatasourceException;
import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeUtilities;
import edu.brown.cs.student.main.server.RecipeData.Datasource.SearchResult;
import edu.brown.cs.student.main.server.RecipeData.Datasource.SpoonacularRecipeSource;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
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
      List<Recipe> results = spoonacular.queryRecipes(15, null, null, null, null, null, "kale", 60);
      SearchResult resultsObject = new SearchResult(15, results.size(), results);

      // ********************* SAVING ***********************
      System.out.println("Beginning to save read String.");
      FileWriter jsonWriter = new FileWriter("");
      BufferedWriter bw = new BufferedWriter(jsonWriter);
      String serializedResults = RecipeUtilities.SEARCH_RESULT_JSON_ADAPTER.toJson(resultsObject);
      bw.write(serializedResults);
      bw.close();
      jsonWriter.close();
      System.out.println("Done saving read String.\n");
    } catch (IOException | IllegalArgumentException | DatasourceException e) {
      System.out.println(e.getMessage());
    }

    /** MOST ABUNDANT INGREDIENT FOR TESTING */
    try {
      // ***************** READING THE FILE *****************
      FileReader jsonReader = new FileReader("data/exampleSearchResultLength4.json");
      BufferedReader br = new BufferedReader(jsonReader);
      String fileString = "";

      String line = br.readLine();
      while (line != null) {
        fileString = fileString + line;
        line = br.readLine();
      }
      jsonReader.close();

      // ****************** DESERIALIZING *******************
      SearchResult deserializedResult = RecipeUtilities.deserializeSearchResult(fileString);
      Recipe deserializedRecipe = deserializedResult.getResults().get(0);

      // ********* DETERMINE PRIMARY INGREDIENTS ************
      System.out.println("Recipe: " + deserializedRecipe.getTitle());
      List<String> orderedIngredients = findMostAbundantIngredients(deserializedRecipe, 3);
      System.out.println("Top 3 most important ingredients: " + orderedIngredients);

    } catch (IOException | IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }
}
