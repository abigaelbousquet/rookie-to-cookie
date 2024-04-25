package edu.brown.cs.student.main.server.RecipeData.Recipe;

import java.util.List;

/**
 * A class describing instructions associated with a recipe.
 */
public class RecipeInstructions {
    private final String name;
    private final List<Step> steps;

    /**
     * Constructor for the RecipeInstructions class.
     *
     * @param name the String name associated with this recipe instruction set
     * @param steps the List of Steps included in this set of recipe instructions
     */
    public RecipeInstructions(String name, List<Step> steps) {
        this.name = name;
        this.steps = steps;
    }

    /**
     * Gets the name associated with this RecipeInstructions.
     *
     * @return this RecipeInstructions' name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the list of Steps associated with this RecipeInstructions.
     *
     * @return this RecipeInstructions' steps
     */
    public List<Step> getSteps() {
        return steps;
    }
}

