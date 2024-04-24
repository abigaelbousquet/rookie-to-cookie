package edu.brown.cs.student.main.server.RecipeData.Recipe;


import java.util.List;

public class Ingredient {
    private final Measurement measures;
    private final List<String> meta;
    private final String name;

    public Ingredient(Measurement measures, List<String> meta, String name) {
        this.measures = measures;
        this.meta = meta;
        this.name = name;
    }

    public Measurement getMeasures() {
        return measures;
    }

    public List<String> getMeta() {
        return meta;
    }

    public String getName() {
        return name;
    }
}
