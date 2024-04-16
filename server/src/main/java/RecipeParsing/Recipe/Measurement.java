package RecipeParsing.Recipe;

/** A class describing a measurement. */
public class Measurement {
  private double amount;
  private String unit;

  /**
   * Constructor for a Measurement object.
   *
   * @param amount the numerical amount of the substance
   * @param unit the unit to interpret amount in
   * @throws IllegalArgumentException if amount is <= 0
   */
  public Measurement(double amount, String unit) throws IllegalArgumentException {
    if (amount <= 0) {
      throw new IllegalArgumentException(
          "A measurement (of an ingredient) cannot be 0 or less. Attempted to create a measurement of amount "
              + amount);
    }
    this.amount = amount;
    this.unit = unit;
  }

  /**
   * Gets the amount of this measurement.
   *
   * @return this object's amount
   */
  public double getAmount() {
    return this.amount;
  }

  /**
   * Gets the unit of this measurement.
   *
   * @return this object's unit
   */
  public String getUnit() {
    return this.unit;
  }

  /**
   * A toString method for a Measurement.
   *
   * @return the String representation of this Measurement
   */
  @Override
  public String toString() {
    return this.amount + " " + this.unit;
  }
}
