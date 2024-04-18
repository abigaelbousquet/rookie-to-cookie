package edu.brown.cs.student.main.server.RecipeParsing;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs.student.main.server.RecipeParsing.Recipe.Ingredient;
import edu.brown.cs.student.main.server.RecipeParsing.Recipe.Instruction;
import edu.brown.cs.student.main.server.RecipeParsing.Recipe.Measurement;
import edu.brown.cs.student.main.server.RecipeParsing.Recipe.Recipe;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A class for useful utility static methods involved in converting between Recipe objects and
 * jsons.
 */
public class RecipeUtilities {
  private static final Moshi moshi = new Moshi.Builder().build();
  private static final Type mapStringString =
      Types.newParameterizedType(Map.class, String.class, String.class);
  private static final JsonAdapter<Map<String, String>> mapStringStringAdapter =
      moshi.adapter(mapStringString);
  private static final Type listString = Types.newParameterizedType(List.class, String.class);
  private static final JsonAdapter<List<String>> listStringAdapter = moshi.adapter(listString);

  /**
   * Deserializes a raw measurement json into a Measurement object.
   *
   * @param rawJson the raw json to deserialize
   * @return rawJson deserialized into a Measurement object
   * @throws IOException if moshi is unable to parse json
   * @throws IllegalArgumentException if amount in rawJson is not a number
   */
  public static Measurement deserializeMeasurement(String rawJson)
      throws IOException, IllegalArgumentException {
    Map<String, String> rawMeasures = mapStringStringAdapter.fromJson(rawJson);
    if (!rawMeasures.containsKey("us")) {
      throw new IllegalArgumentException(
          "Raw measurement json does not contain us measurement data. Json: " + rawJson);
    } else {
      Map<String, String> usMeasurement = mapStringStringAdapter.fromJson(rawMeasures.get("us"));
      if (!usMeasurement.containsKey("amount")) {
        throw new IllegalArgumentException(
            "Raw us measurement json does not contain amount. Json: " + usMeasurement);
      } else if (!usMeasurement.containsKey("unitLong")) {
        throw new IllegalArgumentException(
            "Raw us measurement json does not contain unit (unitLong). Json: " + usMeasurement);
      } else {
        double amount;
        try {
          amount = Double.parseDouble(usMeasurement.get("amount"));
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException(
              "Raw us measurement json does not contain a numerical amount. Json: "
                  + usMeasurement);
        }
        String unit = usMeasurement.get("unitLong");
        return new Measurement(amount, unit);
      }
    }
  }

  /**
   * Deserializes a raw ingredient json into an Ingredient object.
   *
   * @param rawJson the raw json to deserialize
   * @return rawJson deserialized into an Ingredient object
   * @throws IOException if moshi is unable to parse json
   * @throws IllegalArgumentException if rawJson does not contain all fields of an Ingredient
   */
  public static Ingredient deserializeIngredient(String rawJson)
      throws IOException, IllegalArgumentException {
    Map<String, String> rawIngredient = mapStringStringAdapter.fromJson(rawJson);

    String name;
    List<String> description;
    Measurement measurement;
    if (!rawIngredient.containsKey("name")) {
      throw new IllegalArgumentException(
          "Raw ingredient json does not contain name field. json: " + rawJson);
    } else if (!rawIngredient.containsKey("meta")) {
      throw new IllegalArgumentException(
          "Raw ingredient json does not contain description (meta) field. json: " + rawJson);
    } else if (!rawIngredient.containsKey("measures")) {
      throw new IllegalArgumentException(
          "Raw ingredient json does not contain measurement (measures) field. json: " + rawJson);
    } else {
      name = rawIngredient.get("name");
      description = listStringAdapter.fromJson(rawIngredient.get("meta"));
      measurement = deserializeMeasurement(rawIngredient.get("measures"));
    }
    return new Ingredient(name, description, measurement);
  }

  /**
   * TODO: adapt to true API response format TODO: add real relevantIngredients info
   *
   * <p>Deserializes a raw instruction json into an Instruction object.
   *
   * @param rawJson the raw json to deserialize
   * @return rawJson deserialized into an Instruction object
   * @throws IOException if moshi is unable to parse json
   * @throws IllegalArgumentException if rawJson does not contain all fields of an Instruction
   */
  public static Instruction deserializeInstruction(String rawJson)
      throws IOException, IllegalArgumentException {
    Map<String, String> rawInstruction = mapStringStringAdapter.fromJson(rawJson);

    if (!rawInstruction.containsKey("step")) {
      throw new IllegalArgumentException(
          "Raw instruction json does not contain step field. Json: " + rawInstruction);
    } else if (!rawInstruction.containsKey("number")) {
      throw new IllegalArgumentException(
          "Raw instruction json does not contain number field. Json: " + rawInstruction);
    }

    String step = rawInstruction.get("step");
    int number;
    try {
      number = Integer.parseInt(rawInstruction.get("number"));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "Raw instruction json contains non-number step field. Json: " + rawInstruction);
    }

    // TODO: implement this for real or delete from Instruction class
    List<Ingredient> ingredientList = new ArrayList<>();

    return new Instruction(ingredientList, step, number);
  }

  /**
   * TODO: adapt to true API response format TODO: connect to deserializeRecipe!!
   *
   * <p>Deserializes a raw instruction list json into an Instruction object.
   *
   * @param rawJson the raw json to deserialize
   * @return rawJson deserialized into a List of Instruction objects
   * @throws IOException if moshi is unable to parse json
   * @throws IllegalArgumentException if rawJson does not contain a list of valid Instructions
   */
  public static List<Instruction> deserializeInstructionList(String rawJson)
      throws IOException, IllegalArgumentException {
    Map<String, String> rawInstructionResponse = mapStringStringAdapter.fromJson(rawJson);
    List<Instruction> deserializedInstructionsList = new ArrayList<>();

    if (!rawInstructionResponse.containsKey("steps")) {
      throw new IllegalArgumentException(
          "Raw instructions json does not contain steps field. Json: " + rawInstructionResponse);
    }
    List<String> rawInstructionsList =
        listStringAdapter.fromJson(rawInstructionResponse.get("steps"));
    for (String instruction : rawInstructionsList) {
      deserializedInstructionsList.add(deserializeInstruction(instruction));
    }
    return deserializedInstructionsList;
  }

  /**
   * Helper method for determining if a raw recipe (as a Map) contains all expected keys.
   *
   * @param rawRecipe the Map describing a recipe
   * @return true if rawRecipe contains all expected keys
   * @throws IllegalArgumentException if rawRecipe is missing an expected key
   */
  private static boolean recipeMapContainsAllFields(Map<String, String> rawRecipe)
      throws IllegalArgumentException {
    if (!rawRecipe.containsKey("title")) {
      throw new IllegalArgumentException(
          "Raw recipe json does not contain title field. Json: " + rawRecipe);
    } else if (!rawRecipe.containsKey("image")) {
      throw new IllegalArgumentException(
          "Raw recipe json does not contain image field. Json: " + rawRecipe);
    } else if (!rawRecipe.containsKey("servings")) {
      throw new IllegalArgumentException(
          "Raw recipe json does not contain servings field. Json: " + rawRecipe);
    } else if (!rawRecipe.containsKey("readyInMinutes")) {
      throw new IllegalArgumentException(
          "Raw recipe json does not contain readyInMinutes field. Json: " + rawRecipe);
    } else if (!rawRecipe.containsKey("spoonacularScore")) {
      throw new IllegalArgumentException(
          "Raw recipe json does not contain spoonacularScore field. Json: " + rawRecipe);
    } else if (!rawRecipe.containsKey("cuisines")) {
      throw new IllegalArgumentException(
          "Raw recipe json does not contain cuisines field. Json: " + rawRecipe);
    } else if (!rawRecipe.containsKey("dairyFree")) {
      throw new IllegalArgumentException(
          "Raw recipe json does not contain dairyFree field. Json: " + rawRecipe);
    } else if (!rawRecipe.containsKey("diets")) {
      throw new IllegalArgumentException(
          "Raw recipe json does not contain diets field. Json: " + rawRecipe);
    } else if (!rawRecipe.containsKey("glutenFree")) {
      throw new IllegalArgumentException(
          "Raw recipe json does not contain glutenFree field. Json: " + rawRecipe);
    } else if (!rawRecipe.containsKey("ketogenic")) {
      throw new IllegalArgumentException(
          "Raw recipe json does not contain ketogenic field. Json: " + rawRecipe);
    } else if (!rawRecipe.containsKey("vegan")) {
      throw new IllegalArgumentException(
          "Raw recipe json does not contain vegan field. Json: " + rawRecipe);
    } else if (!rawRecipe.containsKey("vegetarian")) {
      throw new IllegalArgumentException(
          "Raw recipe json does not contain vegetarian field. Json: " + rawRecipe);
    } else if (!rawRecipe.containsKey("extendedIngredients")) {
      throw new IllegalArgumentException(
          "Raw recipe json does not contain ingredients (extendedIngredients) field. Json: "
              + rawRecipe);
    } else {
      return true;
    }
  }

  /**
   * Deserializes a raw recipe json into a Recipe object.
   *
   * @param rawJson the raw json to deserialize
   * @return rawJson deserialized into a Recipe object
   * @throws IOException if moshi is unable to parse json
   * @throws IllegalArgumentException if rawJson does not contain all fields of a Recipe
   */
  public static Recipe deserializeRecipe(String rawJson)
      throws IOException, IllegalArgumentException {
    Map<String, String> rawRecipe = mapStringStringAdapter.fromJson(rawJson);
    recipeMapContainsAllFields(rawRecipe);

    String title = rawRecipe.get("title");
    if (title.length() == 0) {
      // TODO: this might be unnecessary (if spoonacular response is guaranteed to have non-null
      // values)
      throw new IllegalArgumentException("Recipe has no title.");
    }

    String imageLink = rawRecipe.get("imageLink");

    int servings;
    int timeEstimate;
    double spoonacularScore;
    try {
      servings = Integer.parseInt(rawRecipe.get("servings"));
      timeEstimate = Integer.parseInt(rawRecipe.get("readyInMinutes"));
      spoonacularScore = Double.parseDouble(rawRecipe.get("spoonacularScore"));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "Recipe has non-number servings, readyInMinutes, and/or spoonacularScore. Json: "
              + rawRecipe);
    }

    boolean dairyFree = Boolean.parseBoolean(rawRecipe.get("dairyFree"));
    boolean glutenFree = Boolean.parseBoolean(rawRecipe.get("glutenFree"));
    boolean ketogenic = Boolean.parseBoolean(rawRecipe.get("ketogenic"));
    boolean vegan = Boolean.parseBoolean(rawRecipe.get("vegan"));
    boolean vegetarian = Boolean.parseBoolean(rawRecipe.get("vegetarian"));

    List<String> diets = listStringAdapter.fromJson(rawRecipe.get("diets"));
    List<String> cuisines = listStringAdapter.fromJson(rawRecipe.get("cuisines"));

    List<String> rawIngredientsList =
        listStringAdapter.fromJson(rawRecipe.get("extendedIngredients"));
    Set<Ingredient> deserializedIngredientsList = new HashSet();
    for (String rawIngredient : rawIngredientsList) {
      deserializedIngredientsList.add(deserializeIngredient(rawIngredient));
    }

    // TODO: REPLACE NULL WITH DESERIALIZED INSTRUCTIONS
    return new Recipe(
        title,
        imageLink,
        servings,
        timeEstimate,
        spoonacularScore,
        cuisines,
        diets,
        dairyFree,
        glutenFree,
        ketogenic,
        vegan,
        vegetarian,
        deserializedIngredientsList,
        null);
  }

  // TODO: IMPLEMENT THIS BASED ON REAL SPOONACULAR SCHEMA
  public static List<Recipe> deserializeSearchResult() {
    return new ArrayList<Recipe>();
  }
}
