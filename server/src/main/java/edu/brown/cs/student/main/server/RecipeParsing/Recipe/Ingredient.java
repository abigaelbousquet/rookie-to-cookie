package edu.brown.cs.student.main.server.RecipeParsing.Recipe;

import java.util.ArrayList;
import java.util.List;

/** A class describing an ingredient in a recipe. */
public class Ingredient {
  private String name;
  private List<String> description;
  private Measurement measurement;

  /**
   * Constructor for the Ingredient class.
   *
   * @param name the common name of this ingredient
   * @param description a list of strings containing any further description of this ingredient
   *     (e.g., "softened" for butter)
   * @param measurement a Measurement object detailing how much of this Ingredient is needed
   */
  public Ingredient(String name, List<String> description, Measurement measurement) {
    this.name = name;
    this.description = description;
    this.measurement = measurement;
  }

  /**
   * Alternate constructor for the Ingredient class. Makes this a defensive copy of another
   * Ingredient.
   *
   * @param toCopy the Ingredient to make this a defensive copy of
   */
  public Ingredient(Ingredient toCopy) {
    this.name = toCopy.getName();
    this.description = toCopy.getDescription();
    this.measurement = toCopy.getMeasurement();
  }

  /**
   * Gets the name of this Ingredient.
   *
   * @return the name of this Ingredient
   */
  public String getName() {
    return this.name;
  }

  /**
   * Gets a defensive copy of the description of this Recipe.Ingredient.
   *
   * @return a copy of this Ingredient's description
   */
  public List<String> getDescription() {
    return new ArrayList<>(this.description);
  }

  /**
   * Gets the measurement of this Ingredient.
   *
   * @return the measurement of this Recipe.Ingredient
   */
  public Measurement getMeasurement() {
    return this.measurement;
  }

  /**
   * A toString method for an Ingredient.
   *
   * @return the String representation of this Ingredient
   */
  @Override
  public String toString() {
    return "Name: "
        + this.getName()
        + ", Description: "
        + this.getDescription()
        + ", Measurement: "
        + this.getMeasurement().toString();
  }
}
