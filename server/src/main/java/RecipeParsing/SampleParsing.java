package RecipeParsing;

import RecipeParsing.Recipe.Recipe;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SampleParsing {

  public static void main(String[] args) throws FileNotFoundException {

    /** PARSE RECIPE */
    System.out.println("PARSING EXAMPLE RECIPE JSON ---------------------------------");
    try {
      // ***************** READING THE FILE *****************
      FileReader jsonReader = new FileReader("data/exampleRecipe.txt");
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
      Recipe deserializedRecipe = RecipeUtilities.deserializeRecipe(fileString);
      System.out.println("Done deserializing read String.\n");

    } catch (IOException | IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    System.out.println("END ---------------------------------------------------------");
    System.out.println();

    //    /** PARSE INSTRUCTIONS */
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
    //      List<Instruction> deserializedInstructions =
    // RecipeUtilities.deserializeInstructionList(fileString);
    //      System.out.println("Done deserializing read String.\n");
    //
    //    } catch (IOException | IllegalArgumentException e) {
    //      System.out.println(e.getMessage());
    //    }
    //    System.out.println("END ---------------------------------------------------------");
  }
}
