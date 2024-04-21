package edu.brown.cs.student.main.server.Parsing.Recipe;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

/**
 * A class describing a Spoonacular recipe source with utility methods for using associated types.
 */
public class SpoonacularRecipeUtilities {
  private static final Moshi MOSHI = new Moshi.Builder().build();
  private static final JsonAdapter<Recipe> RECIPE_JSON_ADAPTER = MOSHI.adapter(Recipe.class);
  private static final JsonAdapter<RecipeInstructions> INSTRUCTIONS_JSON_ADAPTER =
      MOSHI.adapter(RecipeInstructions.class);
  private static final Type SET_RECIPE_INSTRUCTIONS =
      Types.newParameterizedType(Set.class, RecipeInstructions.class);
  private static final JsonAdapter<List<RecipeInstructions>> MEAL_INSTRUCTIONS_JSON_ADAPTER =
      MOSHI.adapter(SET_RECIPE_INSTRUCTIONS);
  public static final JsonAdapter<SearchResult> SEARCH_RESULT_JSON_ADAPTER =
      MOSHI.adapter(SearchResult.class);
  public static final Type LIST_RECIPES = Types.newParameterizedType(List.class, Recipe.class);
  public static final JsonAdapter<List<Recipe>> LIST_RECIPE_JSON_ADAPTER =
      MOSHI.adapter(LIST_RECIPES);

  /**
   * Deserializes a raw search result json into a SearchResult object. This method should be used to
   * deserialize any successful responses from the Spoonacular /complexSearch endpoint.
   *
   * @param rawJson the raw search result json to deserialize
   * @return a SearchResult, the deserialized version of rawJson
   * @throws IOException if moshi is unable to deserialize rawJson into a SearchResult object
   */
  public static SearchResult deserializeSearchResult(String rawJson) throws IOException {
    return SEARCH_RESULT_JSON_ADAPTER.fromJson(rawJson);
  }

  /**
   * Deserializes a raw recipe json into Recipe object.
   *
   * @param rawJson the raw recipe json to deserialize
   * @return a Recipe, the deserialized version of rawJson
   * @throws IOException if moshi is unable to deserialize rawJson into a Recipe
   * @throws IllegalArgumentException if rawJson describes a recipe without a title
   */
  public static Recipe deserializeRecipe(String rawJson)
      throws IOException, IllegalArgumentException {
    Recipe recipe = RECIPE_JSON_ADAPTER.fromJson(rawJson);
    if ((recipe.getTitle() == null) || (recipe.getTitle().isEmpty())) {
      throw new IllegalArgumentException(
          "Error parsing: Given invalid recipe json with no title. Raw json: " + rawJson);
    }
    return recipe;
  }

  /**
   * Deserializes a raw instruction set json (for a meal - could be multiple sub-recipes) into
   * MealInstructions object. NOTE: WILL NEED TO BE INTEGRATED INTO RECIPE OBJECT EVENTUALLY, MAY
   * BECOME OBSOLETE.
   *
   * @param rawJson the raw meal instructions json to deserialize
   * @return a MealInstructions, the deserialized version of rawJson
   * @throws IOException if moshi is unable to deserialize rawJson into a MealInstructions object
   * @throws IllegalArgumentException if rawJson describes a MealInstructions with no recipes or a
   *     recipe with no steps
   */
  public static MealInstructions deserializeMealInstructions(String rawJson)
      throws IOException, IllegalArgumentException {
    List<RecipeInstructions> mealInstructions = MEAL_INSTRUCTIONS_JSON_ADAPTER.fromJson(rawJson);
    if (mealInstructions.isEmpty()) {
      throw new IllegalArgumentException(
          "Error parsing: Given instructions json without any recipe instructions. Raw json: "
              + rawJson);
    }

    for (RecipeInstructions rawSubRecipe : mealInstructions) {
      if (rawSubRecipe.getSteps().isEmpty()) {
        throw new IllegalArgumentException(
            "Error parsing: Given invalid instructions json where a recipe has no steps. Raw (sub)recipe json: "
                + rawSubRecipe);
      }
    }
    return new MealInstructions(mealInstructions);
  }

  /**
   * Deserializes a raw instruction set json (for a single recipe) into RecipeInstructions object.
   *
   * @param rawJson the raw instructions json to deserialize
   * @return a RecipeInstructions, the deserialized version of rawJson
   * @throws IOException if moshi is unable to deserialize rawJson into a RecipeInstructions object
   * @throws IllegalArgumentException if rawJson describes a RecipeInstructions with no steps
   */
  public static RecipeInstructions deserializeInstructions(String rawJson)
      throws IOException, IllegalArgumentException {
    RecipeInstructions instructions = INSTRUCTIONS_JSON_ADAPTER.fromJson(rawJson);
    if (instructions.getSteps().isEmpty()) {
      throw new IllegalArgumentException(
          "Error parsing: Given invalid instructions json where >= 1 recipe has no steps. Raw json: "
              + rawJson);
    }
    return instructions;
  }

  /////////////////////////////////////// DATA RECORDS ///////////////////////////////////////////

}
