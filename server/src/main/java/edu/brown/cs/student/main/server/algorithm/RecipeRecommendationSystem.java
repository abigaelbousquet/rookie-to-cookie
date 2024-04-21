package edu.brown.cs.student.main.server.algorithm;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.google.api.client.json.Json;

import edu.brown.cs.student.main.server.Parsing.Recipe.SpoonacularRecipeSource;
import edu.brown.cs.student.main.server.Parsing.Recipe.SpoonacularRecipeUtilities;
import edu.brown.cs.student.main.server.Parsing.Recipe.SpoonacularRecipeUtilities.Ingredient;
import edu.brown.cs.student.main.server.Parsing.Recipe.SpoonacularRecipeUtilities.MealPlan;
import edu.brown.cs.student.main.server.Parsing.Recipe.SpoonacularRecipeUtilities.Recipe;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import edu.brown.cs.student.main.server.storage.Utils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class RecipeRecommendationSystem {

    // private final moshi adapter to derialize Meal plans and Recipes

    private List<SpoonacularRecipeUtilities.Recipe> allRecipes; // is a list of 30 recipes based on Spoonacular
                                                                // /complexSearch query with
    // sort=random for user preferences; duplicates have not been removed
    private List<SpoonacularRecipeUtilities.Recipe> likedRecipes;
    private List<SpoonacularRecipeUtilities.Recipe> dislikedRecipes;

    /**
     * If we get here, we are asserting our user has a uid in firebase (rather than
     * guest)
     * 
     * Workflow here:
     * 1. remove duplicates with history/near future - constructor
     * 2. remove top similarities with dislikes
     * 3. choose top similarities with likes
     * 4. return
     * @throws ExecutionException 
     * @throws InterruptedException 
     * @throws IOException 
     * @throws IllegalArgumentException 
     */

    public RecipeRecommendationSystem(List<Recipe> allRecipes, StorageInterface firebaseData, String uid, int numDays) throws InterruptedException, ExecutionException, IllegalArgumentException, IOException {
        this.allRecipes = allRecipes;
        this.removeDuplicates(allRecipes, firebaseData, uid);
        this.dislikedRecipes = this.convertFirebaseData(firebaseData.getCollection(uid, "liked recipes")); // should take the disliked recipe jsons from firebase (perhaps store as an
                                     // object?)
        this.likedRecipes = this.convertFirebaseData(firebaseData.getCollection(uid, "liked recipes"));// should take the liked recipe jsons from firebase
        if (this.likedRecipes == null && this.dislikedRecipes == null) {
            this.pickTop(allRecipes, numDays);
        }
        MealPlan mealPlan = this.createMealPlan(numDays);
        this.addToFirebase(uid, firebaseData, mealPlan);
    }

    private List<Recipe> convertFirebaseData(List<Map<String, Object>> firebaseData) throws IllegalArgumentException, IOException {
        List<Recipe> recipes = new ArrayList<>();
        for (Map<String, Object> recipeData : firebaseData) {
            // Deserialize each map entry into a Recipe object
            String recipeJson = Utils.toMoshiJson(recipeData);

            Recipe recipe = SpoonacularRecipeUtilities.deserializeRecipe(recipeJson);
            if (recipe != null) {
                recipes.add(recipe);
            }
        }
        return recipes;
    }

    private MealPlan createMealPlan(int numDays) {
        ArrayList<Recipe> toMealPlan = this.recommendRecipes(this.likedRecipes.get(0), numDays);
        // Here, you pass the parameters directly to the MealPlan constructor
        return new MealPlan(
            toMealPlan.get(0),
            toMealPlan.size() > 1 ? toMealPlan.get(1) : null,
            toMealPlan.size() > 2 ? toMealPlan.get(2) : null,
            toMealPlan.size() > 3 ? toMealPlan.get(3) : null,
            toMealPlan.size() > 4 ? toMealPlan.get(4) : null,
            toMealPlan.size() > 5 ? toMealPlan.get(5) : null,
            toMealPlan.size() > 6 ? toMealPlan.get(6) : null
        );
    }

    private void addToFirebase(String uid, StorageInterface firebaseData, MealPlan plan) {
        Map<String, Object> data = new HashMap<>();
        // also need a way to find date range, for now just gonna call mealplan-1, etc
        int mealCount =0;
        String planId="default";
        try {
            mealCount = firebaseData.getCollection(uid, "Mealplans").size();
            planId = "mealplan-" + mealCount;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        data.put(planId, plan);

        firebaseData.addDocument(uid, "Mealplans", planId, data);
    }

    private List<Recipe> pickTop(List<Recipe> sortedOptions, int numRecommendations) {
        // select top n from this.allRecipes
        return sortedOptions.subList(0, Math.min(numRecommendations, sortedOptions.size()));
    }

    /**
     * remove the duplicates of the passed in 30 list via what is
     * stored in the firebase for the past 3 weeks + 2 future weeks
     */
    private void removeDuplicates(List<Recipe> allRecipes, StorageInterface firebaseData, String uid) {
        /*
         * TODO: get the recipes for the user for the past 3 and next 2 weeks by getting
         * the
         * current date and then calculating by then what the past 3 sundays and next
         */

    }

    private double calculateSimilarity(Recipe recipe1, Recipe recipe2) {
        // Method to calculate dissimilarity based on ingredient overlap
        Set<Ingredient> ingredients1 = recipe1.extendedIngredients();
        Set<Ingredient> ingredients2 = recipe2.extendedIngredients();

        // Calculate the intersection (overlap) of ingredients
        Set<Ingredient> intersection = new HashSet<>(ingredients1);
        intersection.retainAll(ingredients2);

        // Calculate the Jaccard similarity coefficient
        double similarity = (double) intersection.size()
                / (ingredients1.size() + ingredients2.size() - intersection.size());
        return similarity;
    }

    // Method to recommend recipes for a given recipe
    public ArrayList<Recipe> recommendRecipes(Recipe targetRecipe, int numRecommendations) {
        Map<Double, Recipe> similarityMap = new HashMap<>();

        // Calculate similarity between the target recipe and all other recipes
        for (Recipe recipe : allRecipes) {
            if (!recipe.equals(targetRecipe)) {
                double similarity = calculateSimilarity(targetRecipe, recipe);
                similarityMap.put(similarity, recipe);
            }
        }

        // Sort the recipes by similarity in descending order
        List<Recipe> recommendedRecipes = new ArrayList<>(similarityMap.values());
        recommendedRecipes.sort((r1, r2) -> Double.compare(calculateSimilarity(targetRecipe, r2),
                calculateSimilarity(targetRecipe, r1)));

        // Return the top N recommended recipes
        return (ArrayList<Recipe>) pickTop(recommendedRecipes, numRecommendations);
    }
}
