package edu.brown.cs.student.main.server.Parsing.Recipe;

import java.util.List;

public class MealInstructions {
    private final List<RecipeInstructions> subRecipes;

    public MealInstructions(List<RecipeInstructions> subRecipes) {
        this.subRecipes = subRecipes;
    }

    public List<RecipeInstructions> getSubRecipes() {
        return subRecipes;
    }
}