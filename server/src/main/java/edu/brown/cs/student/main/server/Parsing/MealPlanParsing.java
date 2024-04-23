package edu.brown.cs.student.main.server.Parsing;//package edu.brown.cs.student.main.server.Parsing;
//
//import com.squareup.moshi.JsonAdapter;
//import com.squareup.moshi.Moshi;
//
//import java.io.IOException;
//
//public class MealPlanParsing {
//
//    private static final Moshi MOSHI = new Moshi.Builder().build();
//    private static final JsonAdapter<MealPlan> MEALPLAN_JSON_ADAPTER = MOSHI.adapter(MealPlan.class);
//
//    /**
//     * Deserializes a raw recipe json into Recipe object.
//     *
//     * @param rawJson the raw recipe json to deserialize
//     * @return a Recipe, the deserialized version of rawJson
//     * @throws IOException if moshi is unable to deserialize rawJson into a Recipe
//     * @throws IllegalArgumentException if rawJson describes a recipe without a title
//     */
//    public static MealPlan deserializePlan(String mealName, String rawJson)
//            throws IOException, IllegalArgumentException {
////        String mealJson = String.valueOf(rawJson.get(mealName));
//
//        MealPlan plan = MEALPLAN_JSON_ADAPTER.fromJson(rawJson);
//        if (plan == null) {
//            throw new IllegalArgumentException(
//                    "Error parsing: Given invalid recipe json with no title. Raw json: " + rawJson);
//        }
//        return plan;
//    }
//}
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;
import edu.brown.cs.student.main.server.Parsing.MealPlan;

import java.io.IOException;
import java.util.Map;

public class MealPlanParsing {
    private static final Moshi MOSHI = new Moshi.Builder().build();
    private static final JsonAdapter<Map<String, MealPlan>> MEALPLAN_JSON_ADAPTER = MOSHI.adapter(
            com.squareup.moshi.Types.newParameterizedType(Map.class, String.class, MealPlan.class));

    public static MealPlan deserializePlan(String mealName, String rawJson)
            throws IOException, IllegalArgumentException {
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
