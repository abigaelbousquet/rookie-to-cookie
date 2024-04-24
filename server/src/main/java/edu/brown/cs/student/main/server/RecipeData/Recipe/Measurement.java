package edu.brown.cs.student.main.server.RecipeData.Recipe;

public class Measurement {
    private final USMeasurement us;

    public Measurement(USMeasurement us) {
        this.us = us;
    }

    public USMeasurement getUs() {
        return us;
    }
}
