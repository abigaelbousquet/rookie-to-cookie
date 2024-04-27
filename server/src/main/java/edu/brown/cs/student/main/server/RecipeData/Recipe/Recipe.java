package edu.brown.cs.student.main.server.RecipeData.Recipe;

import java.util.*;
import java.lang.reflect.Type;

/**
 * A class describing a recipe, its metadata, its instructions, and its ingredients.
 */
public class Recipe {
    private final int id;
    private final String creditsText;
    private final String title;
    private final String image;
    private int servings;
    private final int readyInMinutes;
    private final double spoonacularScore;
    private final List<String> cuisines;
    private final List<String> diets;
    private final boolean dairyFree;
    private final boolean glutenFree;
    private final boolean vegan;
    private final boolean vegetarian;
    private final List<Ingredient> extendedIngredients;
    private final List<RecipeInstructions> analyzedInstructions;

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
     * Alternate constructor for the Recipe class.
     *
     * @param toCopy the Recipe to make this a copy of
     */
    public Recipe(Recipe toCopy) {
        this.id = toCopy.getId();
        this.creditsText = toCopy.getCreditsText();
        this.title = toCopy.getTitle();
        this.image = toCopy.getImage();
        this.servings = toCopy.getServings();
        this.readyInMinutes = toCopy.getReadyInMinutes();
        this.spoonacularScore = toCopy.getSpoonacularScore();
        this.cuisines = toCopy.getCuisines();
        this.diets = toCopy.getDiets();
        this.dairyFree = toCopy.isDairyFree();
        this.glutenFree = toCopy.isGlutenFree();
        this.vegan = toCopy.isVegan();
        this.vegetarian = toCopy.isVegetarian();
        this.extendedIngredients = toCopy.getExtendedIngredients();
        this.analyzedInstructions = toCopy.getAnalyzedInstructions();
    }

    /**
     * Scales this Recipe's ingredients up to the multiple of the original servings
     * closest to the desired number of servings. If called with an attempt to scale down, will not
     * modify this Recipe.
     *
     * @param desiredServings the desired number of servings this Recipe should make
     */
    public void scaleRecipe(int desiredServings) {
        if (desiredServings <= this.servings) {
            return;
        }

        int scalingFactor = (int) Math.ceil((double) desiredServings / this.servings);
        for (Ingredient i : this.extendedIngredients) {
            i.scale(scalingFactor);
        }
        this.servings = this.servings * scalingFactor;
    }

    /**
     * Gets the number of cuisines this Recipe is categorized as.
     *
     * @return the length of this.cuisines if non-null, 0 otherwise
     */
    public int getNumCuisines() {
        if (this.cuisines == null) {
            return 0;
        } else {
            return this.cuisines.size();
        }
    }

    /**
     * Gets the number of Ingredients this Recipe uses.
     *
     * @return the length of this Recipe's extendedIngredients field
     */
    public int getNumIngredients() {
        if (this.extendedIngredients == null) {
            // should never happen in theory, but just to be safe
            return 0;
        } else {
            return this.extendedIngredients.size();
        }
    }

    /**
     * Gets the number of steps in this Recipe's instructions.
     *
     * @return the number of Steps associated with this Recipe
     */
    public int getNumSteps() {
        if (this.analyzedInstructions == null) {
            // should never happen in theory, but just to be safe
            return 0;
        } else {
            int totalSteps = 0;
            for (RecipeInstructions instructionSet : this.analyzedInstructions) {
                totalSteps += instructionSet.getSteps().size();
            }
            return totalSteps;
        }
    }

    /**
     * Gets the numerical unique ID associated with this Recipe.
     *
     * @return the id of this Recipe
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the credits to the original publisher of this Recipe.
     *
     * @return the credits to the original publisher
     */
    public String getCreditsText() {
        return this.creditsText;
    }

    /**
     * Gets the title of this Recipe.
     *
     * @return the title of this Recipe
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the link to an image of the results of this Recipe.
     *
     * @return the link to the image of this Recipe
     */
    public String getImage() {
        return this.image;
    }

    /**
     * Gets the number of servings this Recipe serves.
     *
     * @return the number of servings of this Recipe
     */
    public int getServings() {
        return this.servings;
    }

    /**
     * Gets the number of minutes to typically make this Recipe.
     *
     * @return the number of minutes to make this Recipe
     */
    public int getReadyInMinutes() {
        return this.readyInMinutes;
    }

    /**
     * Gets the rating given to this Recipe by Spoonacular.
     *
     * @return the Spoonacular score of this Recipe
     */
    public double getSpoonacularScore() {
        return this.spoonacularScore;
    }

    /**
     * Gets a list of the cuisines this Recipe is classified as.
     *
     * @return a defensive copy of the cuisines of this Recipe
     */
    public List<String> getCuisines() {
        return new ArrayList<String>(this.cuisines);
    }

    /**
     * Gets a list of the diets this Recipe follows.
     *
     * @return a defensive copy of the diets followed by this Recipe
     */
    public List<String> getDiets() {
        return new ArrayList<String>(this.diets);
    }

    /**
     * Checks if this Recipe is dairy-free.
     *
     * @return true if the Recipe is dairy-free, false otherwise
     */
    public boolean isDairyFree() {
        return this.dairyFree;
    }

    /**
     * Checks if this Recipe is gluten-free.
     *
     * @return true if the Recipe is gluten-free, false otherwise
     */
    public boolean isGlutenFree() {
        return this.glutenFree;
    }

    /**
     * Checks if this Recipe is vegan.
     *
     * @return true if the Recipe is vegan, false otherwise
     */
    public boolean isVegan() {
        return this.vegan;
    }

    /**
     * Checks if this Recipe is vegetarian.
     *
     * @return true if the Recipe is vegetarian, false otherwise
     */
    public boolean isVegetarian() {
        return this.vegetarian;
    }

    /**
     * Gets the list of Ingredients used in this Recipe.
     *
     * @return a defensive copy of the list of Ingredients in this Recipe
     */
    public List<Ingredient> getExtendedIngredients() {
        List<Ingredient> copyOfIngredients = new ArrayList<>();
        for (Ingredient i : this.extendedIngredients) {
            copyOfIngredients.add(new Ingredient(i));
        }
        return copyOfIngredients;
    }

    /**
     * Gets the List of RecipeInstructions associated with this Recipe.
     *
     * @return a defensive copy of the List of RecipeInstructions in this Recipe
     */
    public List<RecipeInstructions> getAnalyzedInstructions() {
        List<RecipeInstructions> copyOfRecipeInstructions = new ArrayList<>();
        for (RecipeInstructions i : this.analyzedInstructions) {
            copyOfRecipeInstructions.add(new RecipeInstructions(i));
        }
        return copyOfRecipeInstructions;
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
