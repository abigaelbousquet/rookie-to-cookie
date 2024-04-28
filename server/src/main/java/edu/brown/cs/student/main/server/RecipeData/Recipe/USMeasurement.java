package edu.brown.cs.student.main.server.RecipeData.Recipe;

/** A class describing a measurement with a US unit. */
public class USMeasurement {
  private double amount;
  private final String unitLong;

  /**
   * Constructor for a USMeasurement object.
   *
   * @param amount the numerical amount of the substance
   * @param unitLong the unit to interpret amount in
   * @throws IllegalArgumentException if amount is <= 0
   */
  public USMeasurement(double amount, String unitLong) throws IllegalArgumentException {
    if (amount <= 0) {
      throw new IllegalArgumentException(
          "A measurement (of an ingredient) cannot be 0 or less. Attempted to create a measurement of amount "
              + amount);
    }
    this.amount = amount;
    this.unitLong = unitLong;
  }

  /**
   * Alternate constructor for the USMeasurement class.
   *
   * @param toCopy the USMeasurement to make this a defensive copy of
   */
  public USMeasurement(USMeasurement toCopy) {
    this.amount = toCopy.getAmount();
    this.unitLong = toCopy.getUnitLong();
  }

  /**
   * Scales the amount of this USMeasurement by a provided factor.
   *
   * @param scalingFactor the integer factor to scale this USMeasurement's amount by
   */
  public void scale(int scalingFactor) {
    this.amount = this.amount * scalingFactor;
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
   * Gets the unitLong of this measurement.
   *
   * @return this object's unitLong
   */
  public String getUnitLong() {
    return unitLong;
  }

  /**
   * A toString method for a Measurement.
   *
   * @return the String representation of this Measurement
   */
  @Override
  public String toString() {
    return this.amount + " " + this.unitLong;
  }

  /**
   * An equals method for a USMeasurement.
   *
   * @param o the Object to check if this is equal to
   * @return true if this equals o, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof USMeasurement)) return false;
    USMeasurement other = (USMeasurement) o;
    return (other.getUnitLong().toLowerCase().equals(this.getUnitLong().toLowerCase())
        && (other.getAmount() == this.getAmount()));
  }

  /**
   * A hashCode method for a USMeasurement.
   *
   * @return the hash code for this USMeasurement
   */
  @Override
  public int hashCode() {
    return (Double.hashCode(this.getAmount()) + (31 * this.getUnitLong().toLowerCase().hashCode()));
  }
}
