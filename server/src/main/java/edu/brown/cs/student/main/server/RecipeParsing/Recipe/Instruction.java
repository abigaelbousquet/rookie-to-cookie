package edu.brown.cs.student.main.server.RecipeParsing.Recipe;

import java.util.ArrayList;
import java.util.List;

/** A class describing an instruction. */
public class Instruction {
  private List<Ingredient> relevantIngredients;
  private String description;
  private int instructionNumber;

  /**
   * Constructor for an Instruction object.
   *
   * @param ingredients the List of relevant Ingredient objects
   * @param description the String description of this instruction
   * @throws IllegalArgumentException if description is null or empty String
   */
  public Instruction(List<Ingredient> ingredients, String description, int number)
      throws IllegalArgumentException {
    if ((description.length() == 0) || description == null) {
      throw new IllegalArgumentException(
          "Attempted to create Instruction object with no description of the instruction.");
    }

    this.relevantIngredients = ingredients;
    this.description = description;
    this.instructionNumber = number;
  }

  /**
   * Gets a defensive copy of the relevant ingredients associated with this Instruction.
   *
   * @return a defensive copy of this Instruction's list of relevant ingredients
   */
  public List<Ingredient> getRelevantIngredients() {
    List<Ingredient> copy = new ArrayList<>();
    for (Ingredient ingredient : this.relevantIngredients) {
      copy.add(new Ingredient(ingredient));
    }
    return copy;
  }

  /**
   * Gets the instructionNumber associated with this Instruction.
   *
   * @return this Instruction's instructionNumber
   */
  public int getInstructionNumber() {
    return this.instructionNumber;
  }

  /**
   * Gets the description associated with this Instruction.
   *
   * @return this Instruction's description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * A toString method for an Instruction.
   *
   * @return the String representation of this Instruction
   */
  public String toString() {
    return "Instruction number: "
        + this.instructionNumber
        + ", Involves ingredients: "
        + this.relevantIngredients
        + ", Description: "
        + this.description;
  }
}
