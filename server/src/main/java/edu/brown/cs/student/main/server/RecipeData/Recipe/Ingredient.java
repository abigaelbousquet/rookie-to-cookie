package edu.brown.cs.student.main.server.RecipeData.Recipe;

import java.util.ArrayList;
import java.util.List;

/** A class describing an ingredient. */
public class Ingredient {
  private final Measurement measures;
  private final List<String> meta;
  private final String name;

  /**
   * Constructor for the Ingredient class.
   *
   * @param name the common name of this ingredient
   * @param meta a list of strings containing any further description of this ingredient (e.g.,
   *     "softened" for butter)
   * @param measures a Measurement object detailing how much of this Ingredient is needed
   */
  public Ingredient(Measurement measures, List<String> meta, String name) {
    this.measures = measures;
    this.meta = meta;
    this.name = name;
  }

  /**
   * Alternate constructor for the Ingredient class. Makes this a defensive copy of another
   * Ingredient.
   *
   * @param toCopy the Ingredient to make this a defensive copy of
   */
  public Ingredient(Ingredient toCopy) {
    this.name = toCopy.getName();
    this.meta = toCopy.getMeta();
    this.measures = toCopy.getMeasures();
  }

  /**
   * Scales the Measurement of this Ingredient by a provided factor.
   *
   * @param scalingFactor the integer factor to scale this Ingredient's amount by
   */
  public void scale(int scalingFactor) {
    this.measures.scale(scalingFactor);
  }

  /**
   * Gets the measurement of this Ingredient.
   *
   * @return the measures of this Ingredient
   */
  public Measurement getMeasures() {
    return new Measurement(measures);
  }

  /**
   * Gets a defensive copy of the meta of this Ingredient.
   *
   * @return a defensive copy of this Ingredient's description
   */
  public List<String> getMeta() {
    return new ArrayList<>(meta);
  }

  /**
   * Gets the name of this Ingredient.
   *
   * @return the name of this Ingredient
   */
  public String getName() {
    return name;
  }

  /**
   * An equals method for an Ingredient.
   *
   * @param o the Object to check if this is equal to
   * @return true if this equals o, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Ingredient)) return false;
    Ingredient other = (Ingredient) o;
    return (other.name.toLowerCase().equals(this.name.toLowerCase()));
  }

  /**
   * A hashCode method for an Ingredient.
   *
   * @return the hash code for this Ingredient
   */
  @Override
  public int hashCode() {
    return this.getName().hashCode();
  }

  /**
   * A method to serialize an Ingredient object into JSON format.
   *
   * @return the JSON representation of this Ingredient
   */
  @Override
  public String toString() {
    StringBuilder jsonBuilder = new StringBuilder();
    jsonBuilder.append("{");
    jsonBuilder.append("\"measures\":").append(measures.toString()).append(",");
    jsonBuilder.append("\"meta\":").append(listToJson(meta)).append(",");
    jsonBuilder.append("\"name\":\"").append(name).append("\"");
    jsonBuilder.append("}");
    return jsonBuilder.toString();
  }

  /**
   * A helper method to serialize a list of strings into JSON format.
   *
   * @param list the list of strings to be serialized
   * @return the JSON representation of the list
   */
  private String listToJson(List<String> list) {
    StringBuilder listBuilder = new StringBuilder();
    listBuilder.append("[");
    for (int i = 0; i < list.size(); i++) {
      listBuilder.append("\"").append(list.get(i)).append("\"");
      if (i < list.size() - 1) {
        listBuilder.append(",");
      }
    }
    listBuilder.append("]");
    return listBuilder.toString();
  }
}
