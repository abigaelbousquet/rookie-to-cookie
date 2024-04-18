package edu.brown.cs.student.main.server.algorithm;

import java.util.List;
import java.util.Map;
import java.util.Set;
import edu.brown.cs.student.main.server.RecipeParsing.Recipe.Recipe;
import edu.brown.cs.student.main.server.RecipeParsing.Recipe.Ingredient;
import edu.brown.cs.student.main.server.storage.StorageInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class RecipeRecommendationSystem {

    // private final moshi adapter to derialize Meal plans and Recipes
    
    private List<Recipe> allRecipes; // is a list of 30 recipes based on Spoonacular /complexSearch query with
                                     // sort=random for user preferences; duplicates have not been removed
    private List<Recipe> likedRecipes;
    private List<Recipe> dislikedRecipes;


    /**
     * If we get here, we are asserting our user has a uid in firebase (rather than guest)
     * 
     * Workflow here:
     * 1. remove duplicates with history/near future - constructor
     * 2. remove top similarities with dislikes
     * 3. choose top similarities with likes
     * 4. return
     */

    public RecipeRecommendationSystem(List<Recipe> allRecipes, StorageInterface firebaseData, String uid, int numDays) {
        this.allRecipes = allRecipes;
        this.removeDuplicates(allRecipes, firebaseData, uid);
        this.dislikedRecipes = null; //should take the disliked recipe jsons from firebase (perhaps store as an object?)
        this.likedRecipes = null;//should take the liked recipe jsons from firebase
        if (this.likedRecipes == null && this.dislikedRecipes == null) {
            this.pickTop(allRecipes, numDays);
        }
        this.recommendRecipes(this.likedRecipes.get(0), numDays);
    }

    private List<Recipe> pickTop(List<Recipe> sortedOptions, int numRecommendations){
        //select top n from this.allRecipes
        return sortedOptions.subList(0, Math.min(numRecommendations, sortedOptions.size()));
    }

    /**
     * remove the duplicates of the passed in 30 list via what is
     * stored in the firebase for the past 3 weeks + 2 future weeks
     */
    private void removeDuplicates(List<Recipe> allRecipes, StorageInterface firebaseData, String uid) {
        /*
         * TODO: get the recipes for the user for the past 3 and next 2 weeks by getting the 
         * current date and then calculating by then what the past 3 sundays and next 
         */

    }

    private double calculateSimilarity(Recipe recipe1, Recipe recipe2) {
        // Method to calculate dissimilarity based on ingredient overlap
        Set<Ingredient> ingredients1 = recipe1.getIngredients();
        Set<Ingredient> ingredients2 = recipe2.getIngredients();

        // Calculate the intersection (overlap) of ingredients
        Set<Ingredient> intersection = new HashSet<>(ingredients1);
        intersection.retainAll(ingredients2);

        // Calculate the Jaccard similarity coefficient
        double similarity = (double) intersection.size()
            / (ingredients1.size() + ingredients2.size() - intersection.size());
            return similarity;
    }

    // Method to recommend recipes for a given recipe
    public List<Recipe> recommendRecipes(Recipe targetRecipe, int numRecommendations) {
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
        return pickTop(recommendedRecipes, numRecommendations);
    }
}
