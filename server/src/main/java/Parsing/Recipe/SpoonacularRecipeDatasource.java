package Parsing.Recipe;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class describing a Spoonacular recipe source.
 */
public class SpoonacularRecipeDatasource {
  private static final Moshi moshi = new Moshi.Builder().build();
  private static final JsonAdapter<Recipe> recipeAdapter = moshi.adapter(Recipe.class);
  private static final JsonAdapter<RecipeInstructions> instructionsAdapter = moshi.adapter(
      RecipeInstructions.class);

  /**
   * Deserializes a raw recipe json into Recipe object.
   *
   * @param rawJson the raw recipe json to deserialize
   * @return a Recipe, the deserialized version of rawJson
   * @throws IOException if moshi is unable to deserialize rawJson into a Recipe
   * @throws IllegalArgumentException if rawJson describes a recipe without a title
   */
  public Recipe deserializeRecipe(String rawJson) throws IOException, IllegalArgumentException {
    Recipe recipe = recipeAdapter.fromJson(rawJson);
    if ((recipe.title == null) || (recipe.title.isEmpty())) {
      throw new IllegalArgumentException("Error parsing: Given invalid recipe json with no title. Raw json: " + rawJson);
    }
    return recipe;
  }

  /**
   * Deserializes a raw instruction set json (for a meal - could be multiple sub-recipes) into MealInstructions object.
   * NOTE: WILL NEED TO BE INTEGRATED INTO RECIPE OBJECT EVENTUALLY, MAY BECOME OBSOLETE.
   *
   * @param rawJson the raw meal instructions json to deserialize
   * @return a MealInstructions, the deserialized version of rawJson
   * @throws IOException if moshi is unable to deserialize rawJson into a MealInstructions object
   * @throws IllegalArgumentException if rawJson describes a MealInstructions with no recipes or a recipe with no steps
   */
  public MealInstructions deserializeMealInstructions(String rawJson) throws IOException, IllegalArgumentException {
    Type setOfString = Types.newParameterizedType(Set.class, String.class);
    JsonAdapter<Set<String>> setOfStringAdapter = moshi.adapter(setOfString);
    Set<String> rawSubRecipes = setOfStringAdapter.fromJson(rawJson);
    if (rawSubRecipes.isEmpty()) {
      throw new IllegalArgumentException("Error parsing: Given instructions json without any recipe instructions. Raw json: " + rawJson);
    }

    MealInstructions fullInstructionsDeserialized = new MealInstructions(new HashSet<>());
    for (String rawSubRecipe : rawSubRecipes) {
      RecipeInstructions instructions = instructionsAdapter.fromJson(rawSubRecipe);
      if (instructions.steps.isEmpty()) {
        throw new IllegalArgumentException("Error parsing: Given invalid instructions json where a recipe has no steps. Raw (sub)recipe json: " + rawSubRecipe);
      } else {
        fullInstructionsDeserialized.subRecipes.add(instructions);
      }
    }
    return fullInstructionsDeserialized;
  }

  /**
   * Deserializes a raw instruction set json (for a single recipe) into RecipeInstructions object.
   * NOTE: WILL NEED TO BE INTEGRATED INTO RECIPE OBJECT EVENTUALLY, MAY BECOME OBSOLETE.
   *
   * @param rawJson the raw instructions json to deserialize
   * @return a RecipeInstructions, the deserialized version of rawJson
   * @throws IOException if moshi is unable to deserialize rawJson into a RecipeInstructions object
   * @throws IllegalArgumentException if rawJson describes a RecipeInstructions with no steps
   */
  public RecipeInstructions deserializeInstructions(String rawJson) throws IOException, IllegalArgumentException {
    RecipeInstructions instructions = instructionsAdapter.fromJson(rawJson);
    if (instructions.steps.isEmpty()) {
      throw new IllegalArgumentException("Error parsing: Given invalid instructions json where >= 1 recipe has no steps. Raw json: " + rawJson);
    }
    return instructions;
  }

  /**
   * Inner record classes describing a Recipe object.
   */
  public record Recipe(int id, String title, String image, int servings, int readyInMinutes, double spoonacularScore, Set<String> cuisines, Set<String> diets, boolean dairyFree, boolean glutenFree, boolean ketogenic, boolean vegan, boolean vegetarian, Set<Ingredient> extendedIngredients) {}
  public record Ingredient(Measurement measures, List<String> meta, String name) {}
  public record Measurement(USMeasurement us) {}
  public record USMeasurement(double amount, String unitLong) {}

  /**
   * Inner record classes describing instructions (pertaining to a Recipe).
   */
  public record MealInstructions(Set<RecipeInstructions> subRecipes) {}
  public record RecipeInstructions(String name, List<Step> steps) {}
  public record Step(int number, Set<Equipment> equipment, Set<IngredientName> ingredients, String step) {}
  public record Equipment(String name) {}
  public record IngredientName(String name) {}
}
