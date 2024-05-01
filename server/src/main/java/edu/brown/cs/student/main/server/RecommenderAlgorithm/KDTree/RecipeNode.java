package edu.brown.cs.student.main.server.RecommenderAlgorithm.KDTree;

import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;

/** Class describing a Recipe node (for a K-D tree of Recipes). */
public class RecipeNode {
  protected Recipe recipe;
  protected int[] location; // [numCuisines, numIngredients, numSteps]
  protected RecipeNode left, right;

  /**
   * Constructor for the RecipeNode class.
   *
   * @param recipe the Recipe this RecipeNode wraps
   */
  public RecipeNode(Recipe recipe) {
    this.recipe = recipe;
    this.location =
        new int[] {recipe.getNumCuisines(), recipe.getNumIngredients(), recipe.getNumSteps()};
    left = null;
    right = null;
  }

  /**
   * Alternate constructor for the RecipeNode class.
   *
   * @param toCopy the RecipeNode to make this a defensive copy of
   */
  public RecipeNode(RecipeNode toCopy) {
    this.recipe = toCopy.getRecipe(); // a defensive copy
    this.location = toCopy.getLocation(); // a defensive copy
    this.left = toCopy.getLeft(); // a defensive copy
    this.right = toCopy.getRight(); // a defensive copy
  }

  /**
   * Gets this RecipeNode's location.
   *
   * @return a defensive copy of this.location
   */
  public int[] getLocation() {
    int[] copy = new int[3];
    copy[0] = this.location[0];
    copy[1] = this.location[1];
    copy[2] = this.location[2];
    return copy;
  }

  /**
   * Gets this node's Recipe (a defensive copy).
   *
   * @return a defensive copy of this.recipe
   */
  public Recipe getRecipe() {
    return new Recipe(this.recipe);
  }

  /**
   * Gets this node's left node.
   *
   * @return this RecipeNode's left node
   */
  public RecipeNode getLeft() {
    if (this.left == null) {
      return null;
    } else {
      return new RecipeNode(this.left);
    }
  }

  /**
   * Gets this node's right node.
   *
   * @return this RecipeNode's right node
   */
  public RecipeNode getRight() {
    if (this.right == null) {
      return null;
    } else {
      return new RecipeNode(this.right);
    }
  }

  /**
   * An equals method for the RecipeNode class.
   *
   * @param o the Object to check if this equals
   * @return true if o equals this, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof RecipeNode)) return false;
    RecipeNode other = (RecipeNode) o;

    boolean leftEqual, rightEqual;
    if (this.left == null) {
      if (other.left == null) {
        leftEqual = true;
      } else {
        leftEqual = false;
      }
    } else {
      leftEqual = this.left.equals(other.left);
    }

    if (this.right == null) {
      if (other.right == null) {
        rightEqual = true;
      } else {
        rightEqual = false;
      }
    } else {
      rightEqual = this.right.equals(other.right);
    }
    return (other.recipe.equals(this.recipe) && leftEqual && rightEqual);
  }

  /**
   * A hashCode method for the RecipeNode class.
   *
   * @return the hash code for this RecipeNode
   */
  @Override
  public int hashCode() {
    return ((this.recipe.hashCode() + (31 * this.left.hashCode())) + (31 * this.right.hashCode()));
  }
}
