package edu.brown.cs.student.main.server.RecipeData.Recipe;


public class Step {
    private final int number;
    private final String step;

    public Step(int number, String step) {
        this.number = number;
        this.step = step;
    }

    public int getNumber() {
        return number;
    }

    public String getStep() {
        return step;
    }
}