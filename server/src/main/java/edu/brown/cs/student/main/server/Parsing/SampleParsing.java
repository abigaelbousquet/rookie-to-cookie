package edu.brown.cs.student.main.server.Parsing;

import edu.brown.cs.student.main.server.Parsing.Recipe.DatasourceException;
import edu.brown.cs.student.main.server.Parsing.Recipe.SpoonacularRecipeSource;

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
      spoonacular.queryRecipes(5, null, null, null, null, null, 60);
    } catch (DatasourceException e) {
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
