package edu.brown.cs.student.main.server.RecipeData.Recipe;

/**
 * A class describing an instruction step in a recipe.
 */
public class Step {
    private final int number;
    private final String step;

    /**
     * Constructor for the Step class.
     *
     * @param number the number associated with this Step
     * @param step the String description of this Step
     */
    public Step(int number, String step) {
        this.number = number;
        this.step = step;
    }

    /**
     * Alternate constructor for the Step class.
     *
     * @param toCopy the Step to make this a copy of
     */
    public Step(Step toCopy) {
        this.number = toCopy.getNumber();
        this.step = toCopy.getStep();
    }

    /**
     * Gets the number associated with this Step.
     *
     * @return this Step's number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Gets the description associated with this Step.
     *
     * @return this Step's step description
     */
    public String getStep() {
        return step;
    }

    /**
     * A toString method for the Step class.
     *
     * @return the String representation of this Step
     */
    @Override
    public String toString() {
        return (this.getNumber() + ") " + this.getStep());
    }
}