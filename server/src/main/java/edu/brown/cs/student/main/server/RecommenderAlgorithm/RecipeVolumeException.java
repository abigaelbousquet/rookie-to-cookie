package edu.brown.cs.student.main.server.RecommenderAlgorithm;

/**
 * This exception communicates that the meal plan generation algorithm could not generate enough
 * recipes within the provided constraints for the number of days needed to plan.
 */
public class RecipeVolumeException extends Exception {
  // The root cause of this datasource problem
  private final Throwable cause;

  /**
   * Constructor for a RecipeVolumeException.
   *
   * @param message the error message associated with this exception
   */
  public RecipeVolumeException(String message) {
    super(message); // Exception message
    this.cause = null;
  }

  /**
   * Returns the Throwable provided (if any) as the root cause of this exception. We don't make a
   * defensive copy here because we don't anticipate mutation of the Throwable to be any issue, and
   * because this is mostly implemented for debugging support.
   *
   * @return the root cause Throwable
   */
  public Throwable getCause() {
    return this.cause;
  }
}
