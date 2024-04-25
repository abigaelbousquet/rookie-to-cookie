package edu.brown.cs.student.main.server.RecipeData.Datasource;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs.student.main.server.RecipeData.MealPlan;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * A class describing a Spoonacular recipe source with utility methods for using associated types.
 */
public class RecipeUtilities {
  private static final Moshi MOSHI = new Moshi.Builder().build();
  private static final JsonAdapter<Recipe> RECIPE_JSON_ADAPTER = MOSHI.adapter(Recipe.class);
  public static final JsonAdapter<SearchResult> SEARCH_RESULT_JSON_ADAPTER =
      MOSHI.adapter(SearchResult.class);
  public static final Type LIST_RECIPES = Types.newParameterizedType(List.class, Recipe.class);
  public static final JsonAdapter<List<Recipe>> LIST_RECIPE_JSON_ADAPTER =
      MOSHI.adapter(LIST_RECIPES);
  private static final JsonAdapter<Map<String, MealPlan>> MEALPLAN_JSON_ADAPTER = MOSHI.adapter(
      com.squareup.moshi.Types.newParameterizedType(Map.class, String.class, MealPlan.class));


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

  // TODO: javadoc (abby isn't sure what mealName is / how this works)
  public static MealPlan deserializePlan(String mealName, String rawJson)
      throws IllegalArgumentException {
    Map<String, MealPlan> mealPlanMap;
    try {
      mealPlanMap = MEALPLAN_JSON_ADAPTER.fromJson(rawJson);
    } catch (IOException e) {
      throw new IllegalArgumentException("Error parsing JSON: " + e.getMessage());
    }

    if (mealPlanMap == null) {
      throw new IllegalArgumentException(
          "Error parsing: Given invalid recipe json with no title. Raw json: " + rawJson);
    }

    MealPlan plan = mealPlanMap.get(mealName);
    if (plan == null) {
      throw new IllegalArgumentException("Meal plan for " + mealName + " not found.");
    }
    return plan;
  }
}
