package edu.brown.cs.student.main.server.RecipeData.Recipe;

import java.util.*;
import java.lang.reflect.Type;

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

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreditsText() {
        return creditsText;
    }

    public void setCreditsText(String creditsText) {
        this.creditsText = creditsText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public double getSpoonacularScore() {
        return spoonacularScore;
    }

    public void setSpoonacularScore(double spoonacularScore) {
        this.spoonacularScore = spoonacularScore;
    }

    public List<String> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<String> cuisines) {
        this.cuisines = cuisines;
    }

    public List<String> getDiets() {
        return diets;
    }

    public void setDiets(List<String> diets) {
        this.diets = diets;
    }

    public boolean isDairyFree() {
        return dairyFree;
    }

    public void setDairyFree(boolean dairyFree) {
        this.dairyFree = dairyFree;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public List<Ingredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    public void setExtendedIngredients(List<Ingredient> extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
    }

    public List<RecipeInstructions> getAnalyzedInstructions() {
        return analyzedInstructions;
    }

    public void setAnalyzedInstructions(List<RecipeInstructions> analyzedInstructions) {
        this.analyzedInstructions = analyzedInstructions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Recipe))
            return false;
        Recipe other = (Recipe)o;
        return (other.id == this.id);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.id);
    }

    // toString, equals, and hashCode methods
}
