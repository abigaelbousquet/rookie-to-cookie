package Parsing.Recipe;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.io.IOException;
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
   * Deserializes a raw instruction set json into RecipeInstructions object.
   * NOTE: WILL NEED TO BE INTEGRATED INTO RECIPE OBJECT EVENTUALLY, MAY BECOME OBSOLETE.
   *
   * @param rawJson the raw instructions json to deserialize
   * @return a Recipe, the deserialized version of rawJson
   * @throws IOException if moshi is unable to deserialize rawJson into a RecipeInstructions object
   * @throws IllegalArgumentException if rawJson describes a RecipeInstruction with no steps
   */
  public RecipeInstructions deserializeInstructions(String rawJson) throws IOException, IllegalArgumentException {
    RecipeInstructions instructions = instructionsAdapter.fromJson(rawJson);
    if (instructions.steps.isEmpty()) {
      throw new IllegalArgumentException("Error parsing: Given invalid instructions json with no steps. Raw json: " + rawJson);
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
  public record RecipeInstructions(List<Step> steps) {}
  public record Step(int number, Set<Equipment> equipment, Set<IngredientName> ingredients, String step) {}
  public record Equipment(String name) {}
  public record IngredientName(String name) {}
}
