package edu.brown.cs.student.main.server.RecommenderAlgorithm.KDTree;

import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;

/**
 * Class describing a Recipe node (for a K-D tree of Recipes).
 */
public class RecipeNode {
  protected Recipe recipe;
  protected int[] location; // [numCuisines, numIngredients, numSteps]
  protected RecipeNode left, right; // TODO: make NOT public

  /**
   * Constructor for the RecipeNode class.
   *
   * @param recipe the Recipe this RecipeNode wraps
   */
  public RecipeNode(Recipe recipe) {
    this.recipe = recipe;
    this.location = new int[]{recipe.getNumCuisines(), recipe.getNumIngredients(), recipe.getNumSteps()};
    left = null;
    right = null;
  }

  /**
   * Gets this node's left node.
   *
   * TODO: make return defensive copy!
   *
   * @return this RecipeNode's left node
   */
  public RecipeNode getLeft() {
    return this.left;
  }

  /**
   * Gets this node's right node.
   *
   * TODO: make return defensive copy!
   *
   * @return this RecipeNode's right node
   */
  public RecipeNode getRight() {
    return this.right;
  }

  /**
   * An equals method for the RecipeNode class.
   *
   * @param o the Object to check if this equals
   * @return true if o equals this, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof RecipeNode))
      return false;
    RecipeNode other = (RecipeNode)o;

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
    return ((this.recipe.hashCode() + (31*this.left.hashCode())) + (31*this.right.hashCode()));

  }
}