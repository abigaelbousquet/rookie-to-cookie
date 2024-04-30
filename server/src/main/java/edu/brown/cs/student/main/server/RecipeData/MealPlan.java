package edu.brown.cs.student.main.server.RecipeData;

import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** A class describing a weekly meal plan. */
public class MealPlan {
  private Recipe sunday;
  private Recipe monday;
  private Recipe tuesday;
  private Recipe wednesday;
  private Recipe thursday;
  private Recipe friday;
  private Recipe saturday;
//  private List<Date> dates;
  private List<String> dates;


  /**
   * Constructor for the MealPlan class.
   *
   * @param sunday the Recipe to plan for Sunday
   * @param monday the Recipe to plan for Monday
   * @param tuesday the Recipe to plan for Tuesday
   * @param wednesday the Recipe to plan for Wednesday
   * @param thursday the Recipe to plan for Thursday
   * @param friday the Recipe to plan for Friday
   * @param saturday the Recipe to plan for Saturday
   */
  public MealPlan(
      Recipe sunday,
      Recipe monday,
      Recipe tuesday,
      Recipe wednesday,
      Recipe thursday,
      Recipe friday,
      Recipe saturday, List<String> dates) {
    this.sunday = sunday;
    this.monday = monday;
    this.tuesday = tuesday;
    this.wednesday = wednesday;
    this.thursday = thursday;
    this.friday = friday;
    this.saturday = saturday;
    this.dates = dates;
  }

  /**
   * Gets a list of the Recipes to make in this week's MealPlan.
   *
   * @return a List of the Recipes for the week, indexed where 0 is Sunday, 1 is Monday, etc.
   */
  public List<Recipe> getRecipes() {
    ArrayList<Recipe> recipeList = new ArrayList<>();
    recipeList.add(this.sunday);
    recipeList.add(this.monday);
    recipeList.add(this.tuesday);
    recipeList.add(this.wednesday);
    recipeList.add(this.thursday);
    recipeList.add(this.friday);
    recipeList.add(this.saturday);
    return recipeList;
  }

  /**
   * Gets the Recipe planned for Sunday.
   *
   * @return the Recipe planned for Sunday
   */
  public Recipe getSunday() {
    return sunday;
  }

  /**
   * Gets the Recipe planned for Monday.
   *
   * @return the Recipe planned for Monday
   */
  public Recipe getMonday() {
    return monday;
  }

  /**
   * Gets the Recipe planned for Tuesday.
   *
   * @return the Recipe planned for Tuesday
   */
  public Recipe getTuesday() {
    return tuesday;
  }

  /**
   * Gets the Recipe planned for Wednesday.
   *
   * @return the Recipe planned for Wednesday
   */
  public Recipe getWednesday() {
    return wednesday;
  }

  /**
   * Gets the Recipe planned for Thursday.
   *
   * @return the Recipe planned for Thursday
   */
  public Recipe getThursday() {
    return thursday;
  }

  /**
   * Gets the Recipe planned for Friday.
   *
   * @return the Recipe planned for Friday
   */
  public Recipe getFriday() {
    return friday;
  }

  /**
   * Gets the Recipe planned for Saturday.
   *
   * @return the Recipe planned for Saturday
   */
  public Recipe getSaturday() {
    return saturday;
  }

  /**
   * A toString method for the MealPlan class.
   *
   * @return the String representation of this MealPlan
   */
  @Override
  public String toString() {
    return "MealPlan{"
        + "Sunday: "
        + sunday
        + ", Monday: "
        + monday
        + ", Tuesday: "
        + tuesday
        + ", Wednesday: "
        + wednesday
        + ", Thursday: "
        + thursday
        + ", Friday: "
        + friday
        + ", Saturday: "
        + saturday
        + '}';
  }
  public List<String> getDates() {
    return this.dates;
  }

  public void setDates(List<String> dates) {
    this.dates = dates;
  }
}
