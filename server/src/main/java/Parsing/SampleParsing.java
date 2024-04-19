package Parsing;

import Parsing.Recipe.SpoonacularRecipeDatasource;
import Parsing.Recipe.SpoonacularRecipeDatasource.MealInstructions;
import Parsing.Recipe.SpoonacularRecipeDatasource.Recipe;
import Parsing.Recipe.SpoonacularRecipeDatasource.RecipeInstructions;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SampleParsing {

  public static void main(String[] args) throws FileNotFoundException {
    SpoonacularRecipeDatasource dataSrc = new SpoonacularRecipeDatasource();

    /** PARSE RECIPE */
    System.out.println("PARSING EXAMPLE RECIPE JSON ---------------------------------");
    try {
      // ***************** READING THE FILE *****************
      FileReader jsonReader = new FileReader("data/exampleRecipe.json");
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
      Recipe deserializedRecipe = dataSrc.deserializeRecipe(fileString);
      System.out.println("Done deserializing read String.\n");

    } catch (IOException | IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    System.out.println("END ---------------------------------------------------------");
    System.out.println();

    /** PARSE INSTRUCTIONS */
//    System.out.println("PARSING EXAMPLE INSTRUCTIONS JSON ---------------------------------");
//    try {
//      // ***************** READING THE FILE *****************
//      FileReader jsonReader = new FileReader("data/exampleInstructions.json");
////      FileReader jsonReader = new FileReader("data/exampleBasicInstructions.json");
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
//      MealInstructions deserializedInstructions = dataSrc.deserializeMealInstructions(fileString);
//      System.out.println("Done deserializing read String.\n");
//
//    } catch (IOException | IllegalArgumentException e) {
//      System.out.println(e.getMessage());
//    }
//    System.out.println("END ---------------------------------------------------------");
  }
}
