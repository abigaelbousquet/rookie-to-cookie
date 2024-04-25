package edu.brown.cs.student.main.server.RecipeData.Recipe;

/**
 * A class describing a Measurement, wrapping a USMeasurement.
 */
public class Measurement {
    private final USMeasurement us;

    /**
     * Constructor for a Measurement object.
     *
     * @param us the USMeasurement describing this Measurement
     */
    public Measurement(USMeasurement us) {
        this.us = us;
    }

    /**
     * An alternate constructor for a Measurement object.
     *
     * @param toCopy the Measurement to make this a defensive copy of
     */
    public Measurement(Measurement toCopy) {
        this.us = toCopy.getUs();
    }

    /**
     * Scales this Measurement by a provided factor.
     *
     * @param scalingFactor the integer factor to scale this Measurement's amount by
     */
    public void scale(int scalingFactor) {
        this.us.scale(scalingFactor);
    }

    /**
     * Gets the USMeasurement associated with this Measurement.
     *
     * @return a defensive copy of this Measurement's us field
     */
    public USMeasurement getUs() {
        return new USMeasurement(us);
    }

    /**
     * A toString method for a Measurement.
     *
     * @returns the String interpretation of this
     */
    @Override
    public String toString() {
        return this.us.toString();
    }

    /**
     * An equals method for a Measurement.
     *
     * @param o the Object to check if this is equal to
     * @return true if this equals o, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Measurement))
            return false;
        Measurement other = (Measurement)o;
        return (other.getUs().equals(this.getUs()));
    }

    /**
     * A hashCode method for a Measurement.
     *
     * @return the hash code for this Measurement
     */
    @Override
    public int hashCode() {
        return this.getUs().hashCode();
    }
}
