package edu.brown.cs.student.main.server.Parsing.Recipe;

import java.util.List;

public class RecipeInstructions {
    private final String name;
    private final List<Step> steps;

    public RecipeInstructions(String name, List<Step> steps) {
        this.name = name;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public List<Step> getSteps() {
        return steps;
    }
}

