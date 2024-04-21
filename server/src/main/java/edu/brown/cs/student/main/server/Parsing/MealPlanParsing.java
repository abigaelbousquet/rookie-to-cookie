package edu.brown.cs.student.main.server.Parsing;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import edu.brown.cs.student.main.server.Endpoints.Profile;

import java.io.IOException;

public class MealPlanParsing {

    private static final Moshi MOSHI = new Moshi.Builder().build();
    private static final JsonAdapter<MealPlan> MEALPLAN_JSON_ADAPTER = MOSHI.adapter(MealPlan.class);

    /**
     * Deserializes a raw recipe json into Recipe object.
     *
     * @param rawJson the raw recipe json to deserialize
     * @return a Recipe, the deserialized version of rawJson
     * @throws IOException if moshi is unable to deserialize rawJson into a Recipe
     * @throws IllegalArgumentException if rawJson describes a recipe without a title
     */
    public static MealPlan deserializePlan(String rawJson)
            throws IOException, IllegalArgumentException {
        MealPlan plan = MEALPLAN_JSON_ADAPTER.fromJson(rawJson);
        if (plan == null) {
            throw new IllegalArgumentException(
                    "Error parsing: Given invalid recipe json with no title. Raw json: " + rawJson);
        }
        return plan;
    }
}
