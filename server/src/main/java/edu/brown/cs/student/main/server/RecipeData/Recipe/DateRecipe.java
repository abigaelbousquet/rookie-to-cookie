package edu.brown.cs.student.main.server.RecipeData.Recipe;

import java.util.Date;

/** A class describing a recipe, its metadata, its instructions, and its ingredients. */
public class DateRecipe {
  private final Recipe recipe;
  private final Date date;

  /**
   * Constructor for the DateRecipe class.
   *
   * @param recipe the Recipe associated with this DateRecipe
   * @param date the Date associated with this DateRecipe
   */
  public DateRecipe(Recipe recipe, Date date) {
    this.date = date;
    this.recipe = recipe;
  }

  /**
   * Gets the Recipe associated with this DateRecipe
   *
   * @return a defensive copy of this.recipe
   */
  public Recipe getRecipe() {
    return new Recipe(this.recipe);
  }

  /**
   * Gets the Date associated with this DateRecipe.
   *
   * @return this.date
   */
  public Date getDate() {
    return this.date;
  }
}
