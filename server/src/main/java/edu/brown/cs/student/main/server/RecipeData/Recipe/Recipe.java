package edu.brown.cs.student.main.server.RecipeData.Recipe;

import java.util.*;
import java.lang.reflect.Type;

/**
 * A class describing a recipe, its metadata, its instructions, and its ingredients.
 */
public class Recipe {
    private int id;
    private String creditsText;
    private String title;
    private String image;
    private int servings;
    private int readyInMinutes;
    private double spoonacularScore;
    private List<String> cuisines;
    private List<String> diets;
    private boolean dairyFree;
    private boolean glutenFree;
    private boolean vegan;
    private boolean vegetarian;
    private List<Ingredient> extendedIngredients;
    private List<RecipeInstructions> analyzedInstructions;

    /**
     * Constructor for the Recipe class.
     *
     * @param id the numerical unique ID associated with this Recipe
     * @param creditsText credits to the original publisher of this Recipe
     * @param title the title of this Recipe
     * @param image a link to an image of the results of this Recipe
     * @param servings the number of servings this Recipe serves.
     * @param readyInMinutes the number of minutes to typically make this Recipe
     * @param spoonacularScore the rating given to this Recipe by Spoonacular, on a scale from 0 (worst) - 100 (best)
     * @param cuisines a list of the cuisines this Recipe is classified as
     * @param diets a list of the diets this Recipe follows
     * @param dairyFree a boolean indicating if this Recipe is dairyFree
     * @param glutenFree a boolean indicating if this Recipe is glutenFree
     * @param vegan a boolean indicating if this Recipe is vegan
     * @param vegetarian a boolean indicating if this Recipe is vegetarian
     * @param extendedIngredients the list of Ingredients used in this Recipe
     * @param analyzedInstructions the List of RecipeInstructions associated with this Recipe
     */
    public Recipe(int id, String creditsText, String title, String image, int servings, int readyInMinutes, double spoonacularScore,
                  List<String> cuisines, List<String> diets, boolean dairyFree, boolean glutenFree, boolean vegan, boolean vegetarian,
                  List<Ingredient> extendedIngredients, List<RecipeInstructions> analyzedInstructions) {
        this.id = id;
        this.creditsText = creditsText;
        this.title = title;
        this.image = image;
        this.servings = servings;
        this.readyInMinutes = readyInMinutes;
        this.spoonacularScore = spoonacularScore;
        this.cuisines = cuisines;
        this.diets = diets;
        this.dairyFree = dairyFree;
        this.glutenFree = glutenFree;
        this.vegan = vegan;
        this.vegetarian = vegetarian;
        this.extendedIngredients = extendedIngredients;
        this.analyzedInstructions = analyzedInstructions;
    }

    /**
     * Gets the numerical unique ID associated with this Recipe.
     *
     * @return the id of this Recipe
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the credits to the original publisher of this Recipe.
     *
     * @return the credits to the original publisher
     */
    public String getCreditsText() {
        return creditsText;
    }

    /**
     * Gets the title of this Recipe.
     *
     * @return the title of this Recipe
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the link to an image of the results of this Recipe.
     *
     * @return the link to the image of this Recipe
     */
    public String getImage() {
        return image;
    }

    /**
     * Gets the number of servings this Recipe serves.
     *
     * @return the number of servings of this Recipe
     */
    public int getServings() {
        return servings;
    }

    /**
     * Gets the number of minutes to typically make this Recipe.
     *
     * @return the number of minutes to make this Recipe
     */
    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    /**
     * Gets the rating given to this Recipe by Spoonacular.
     *
     * @return the Spoonacular score of this Recipe
     */
    public double getSpoonacularScore() {
        return spoonacularScore;
    }

    /**
     * Gets a list of the cuisines this Recipe is classified as.
     *
     * @return the cuisines of this Recipe
     */
    public List<String> getCuisines() {
        return cuisines;
    }

    /**
     * Gets a list of the diets this Recipe follows.
     *
     * @return the diets followed by this Recipe
     */
    public List<String> getDiets() {
        return diets;
    }

    /**
     * Checks if this Recipe is dairy-free.
     *
     * @return true if the Recipe is dairy-free, false otherwise
     */
    public boolean isDairyFree() {
        return dairyFree;
    }

    /**
     * Checks if this Recipe is gluten-free.
     *
     * @return true if the Recipe is gluten-free, false otherwise
     */
    public boolean isGlutenFree() {
        return glutenFree;
    }

    /**
     * Checks if this Recipe is vegan.
     *
     * @return true if the Recipe is vegan, false otherwise
     */
    public boolean isVegan() {
        return vegan;
    }

    /**
     * Checks if this Recipe is vegetarian.
     *
     * @return true if the Recipe is vegetarian, false otherwise
     */
    public boolean isVegetarian() {
        return vegetarian;
    }

    /**
     * Gets the list of Ingredients used in this Recipe.
     *
     * @return the list of Ingredients in this Recipe
     */
    public List<Ingredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    /**
     * Gets the List of RecipeInstructions associated with this Recipe.
     *
     * @return the List of RecipeInstructions in this Recipe
     */
    public List<RecipeInstructions> getAnalyzedInstructions() {
        return analyzedInstructions;
    }

    /**
     * An equals method for the Recipe class.
     *
     * @param o the Object to check if this equals
     * @return true if o equals this, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Recipe))
            return false;
        Recipe other = (Recipe)o;
        return (other.id == this.id);
    }

    /**
     * A hashCode method for the Recipe class.
     *
     * @return the hash code for this Recipe
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(this.id);
    }
}
