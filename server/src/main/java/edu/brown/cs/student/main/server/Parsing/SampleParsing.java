package edu.brown.cs.student.main.server.Parsing;

import edu.brown.cs.student.main.server.Parsing.Recipe.DatasourceException;
import edu.brown.cs.student.main.server.Parsing.Recipe.SpoonacularRecipeSource;
import edu.brown.cs.student.main.server.Parsing.Recipe.SpoonacularRecipeUtilities;
import edu.brown.cs.student.main.server.Parsing.Recipe.SpoonacularRecipeUtilities.Recipe;
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
      List<Recipe> results = spoonacular.queryRecipes(2, null, null, null, null, null, null, 60);

      // NOTE: as of 3:45, 4/20 the below is not erroring but is producing an empty file :(

      // ********************* SAVING ***********************
      System.out.println("Beginning to save read String.");
      FileWriter jsonWriter = new FileWriter("data/exampleQuery2RecipeResults.txt");
      BufferedWriter bw = new BufferedWriter(jsonWriter);
      String serializedResults =
          SpoonacularRecipeUtilities.LIST_RECIPE_JSON_ADAPTER.toJson(results);
      bw.write(serializedResults);
      jsonWriter.close();
      System.out.println("Done saving read String.\n");
    } catch (IOException | IllegalArgumentException | DatasourceException e) {
      System.out.println(e.getMessage());
    }

    /** PARSE SEARCH RESULTS */
    //    System.out.println("PARSING EXAMPLE SEARCH RESULTS JSON --------------------------");
    //    try {
    //      // ***************** READING THE FILE *****************
    //      FileReader jsonReader = new FileReader("data/exampleCompleteSearchResult.json");
    //      BufferedReader br = new BufferedReader(jsonReader);
    //      String fileString = "";
    //
    //      System.out.println("Beginning to read file.");
    //      String line = br.readLine();
    //      while (line != null) {
    //        fileString = fileString + line;
    //        line = br.readLine();
    //      }
    //      jsonReader.close();
    //      System.out.println("Done reading file.\n");
    //
    //      // ****************** DESERIALIZING *******************
    //      System.out.println("Beginning to deserialize read String.");
    //      SearchResult deserializedSearchResult =
    //          SpoonacularRecipeUtilities.deserializeSearchResult(fileString);
    //      System.out.println("Done deserializing read String.\n");
    //
    //      // ********************* SAVING ***********************
    //      System.out.println("Beginning to save read String.");
    //      //      FileWriter jsonWriter = new FileWriter("data/examplePARSEDSearchResult.json");
    //      FileWriter jsonWriter = new FileWriter("data/examplePARSEDSearchResult.txt");
    //      BufferedWriter bw = new BufferedWriter(jsonWriter);
    //      bw.write(
    //
    // SpoonacularRecipeUtilities.SEARCH_RESULT_JSON_ADAPTER.toJson(deserializedSearchResult));
    //      jsonWriter.close();
    //      System.out.println("Done saving read String.\n");
    //    } catch (IOException | IllegalArgumentException e) {
    //      System.out.println(e.getMessage());
    //    }
    //    System.out.println("END ---------------------------------------------------------");

    /** PARSE RECIPE */
    //    System.out.println("PARSING EXAMPLE RECIPE JSON ---------------------------------");
    //    try {
    //      // ***************** READING THE FILE *****************
    //      FileReader jsonReader = new FileReader("data/exampleRecipe.json");
    //      BufferedReader br = new BufferedReader(jsonReader);
    //      String fileString = "";
    //
    //      System.out.println("Beginning to read file.");
    //      String line = br.readLine();
    //      while (line != null) {
    //        fileString = fileString + line;
    //        line = br.readLine();
    //      }
    //      jsonReader.close();
    //      System.out.println("Done reading file.\n");
    //
    //      // ****************** DESERIALIZING *******************
    //      System.out.println("Beginning to deserialize read String.");
    //      Recipe deserializedRecipe = SpoonacularRecipeUtilities.deserializeRecipe(fileString);
    //      System.out.println("Done deserializing read String.\n");
    //
    //    } catch (IOException | IllegalArgumentException e) {
    //      System.out.println(e.getMessage());
    //    }
    //    System.out.println("END ---------------------------------------------------------");
    //    System.out.println();
    //
    /** PARSE INSTRUCTIONS */
    //    System.out.println("PARSING EXAMPLE INSTRUCTIONS JSON ---------------------------------");
    //    try {
    //      // ***************** READING THE FILE *****************
    //      FileReader jsonReader = new FileReader("data/exampleInstructions.json");
    //      BufferedReader br = new BufferedReader(jsonReader);
    //      String fileString = "";
    //
    //      System.out.println("Beginning to read file.");
    //      String line = br.readLine();
    //      while (line != null) {
    //        fileString = fileString + line;
    //        line = br.readLine();
    //      }
    //      jsonReader.close();
    //      System.out.println("Done reading file.\n");
    //
    //      // ****************** DESERIALIZING *******************
    //      System.out.println("Beginning to deserialize read String.");
    //      MealInstructions deserializedInstructions =
    // SpoonacularRecipeUtilities.deserializeMealInstructions(fileString);
    //      System.out.println("Done deserializing read String.\n");
    //
    //    } catch (IOException | IllegalArgumentException e) {
    //      System.out.println(e.getMessage());
    //    }
    //    System.out.println("END ---------------------------------------------------------");
  }
}
