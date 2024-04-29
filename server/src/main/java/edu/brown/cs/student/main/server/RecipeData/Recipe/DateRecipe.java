package edu.brown.cs.student.main.server.RecipeData.Recipe;

import java.util.Date;

/** A class describing a recipe, its metadata, its instructions, and its ingredients. */
public class DateRecipe {
  private final Recipe recipe;
  private final Date date;

  /**
   * COnstructor
   * @param recipe
   * @param date
   */
  public DateRecipe(Recipe recipe, Date date) {
    this.date = date;
    this.recipe = recipe;

  }
  public Recipe getRecipe() {
    return this.recipe;
  }
  public Date getDate() {
    return this.date;
  }
}
