package edu.brown.cs.student.main.server.RecipeParsing.Recipe;

import java.util.List;
import java.util.Set;

/** A class describing a recipe. */
public class Recipe {

  /* High level metadata */
  private String title;
  private String imageLink;
  private int servings;
  private int timeEstimate;
  private double spoonacularScore;

  /* Categorizations information */
  private List<String> cuisines;
  private List<String> diets;
  private boolean dairyFree;
  private boolean glutenFree;
  private boolean ketogenic;
  private boolean vegan;
  private boolean vegetarian;

  /* Details */
  private Set<Ingredient> ingredients;
  private List<Instruction> instructions;

  /**
   * Constructor for the Recipe.Recipe class.
   *
   * <p>TODO: fill in javadoc here
   *
   * @param title
   * @param imageLink
   * @param servings
   * @param timeEstimate
   * @param spoonacularScore
   * @param cuisines
   * @param diets
   * @param dairyFree
   * @param glutenFree
   * @param ketogenic
   * @param vegan
   * @param vegetarian
   * @param ingredients
   * @param instructions
   */
  public Recipe(
      String title,
      String imageLink,
      int servings,
      int timeEstimate,
      double spoonacularScore,
      List<String> cuisines,
      List<String> diets,
      boolean dairyFree,
      boolean glutenFree,
      boolean ketogenic,
      boolean vegan,
      boolean vegetarian,
      Set<Ingredient> ingredients,
      List<Instruction> instructions) {
    this.title = title;
    this.imageLink = imageLink;
    this.servings = servings;
    this.timeEstimate = timeEstimate;
    this.spoonacularScore = spoonacularScore;
    this.cuisines = cuisines;
    this.diets = diets;
    this.dairyFree = dairyFree;
    this.glutenFree = glutenFree;
    this.ketogenic = ketogenic;
    this.vegan = vegan;
    this.vegetarian = vegetarian;
    this.ingredients = ingredients;
    this.instructions = instructions;
  }

  // TODO: add toString, getters

  public Set<Ingredient> getIngredients() {
    return this.ingredients;

    // TODO: return defensive copy
  }

}
