package Parsing;

import Parsing.Recipe.SpoonacularRecipeUtilities;
import Parsing.Recipe.SpoonacularRecipeUtilities.SearchResult;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SampleParsing {

  /**
   * Main method for sample deserialization of example recipe and instruction set jsons (in .json
   * file form).
   *
   * @param args n/a
   */
  public static void main(String[] args) {

    /** PARSE SEARCH RESULTS */
    System.out.println("PARSING EXAMPLE SEARCH RESULTS JSON --------------------------");
    try {
      // ***************** READING THE FILE *****************
      FileReader jsonReader = new FileReader("data/exampleCompleteSearchResult.json");
      BufferedReader br = new BufferedReader(jsonReader);
      String fileString = "";

      System.out.println("Beginning to read file.");
      String line = br.readLine();
      while (line != null) {
        fileString = fileString + line;
        line = br.readLine();
      }
      jsonReader.close();
      System.out.println("Done reading file.\n");

      // ****************** DESERIALIZING *******************
      System.out.println("Beginning to deserialize read String.");
      SearchResult deserializedSearchResult =
          SpoonacularRecipeUtilities.deserializeSearchResult(fileString);
      System.out.println("Done deserializing read String.\n");

      // ********************* SAVING ***********************
      System.out.println("Beginning to save read String.");
      //      FileWriter jsonWriter = new FileWriter("data/examplePARSEDSearchResult.json");
      FileWriter jsonWriter = new FileWriter("data/examplePARSEDSearchResult.txt");
      BufferedWriter bw = new BufferedWriter(jsonWriter);
      bw.write(
          SpoonacularRecipeUtilities.SEARCH_RESULT_JSON_ADAPTER.toJson(deserializedSearchResult));
      jsonWriter.close();
      System.out.println("Done saving read String.\n");
    } catch (IOException | IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    System.out.println("END ---------------------------------------------------------");

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
