package edu.brown.cs.student.main.server.Parsing;

import edu.brown.cs.student.main.server.Parsing.Recipe.Recipe;

public class MealPlan {
    private Recipe Sunday;
    private Recipe Monday;
    private Recipe Tuesday;
    private Recipe Wednesday;
    private Recipe Thursday;
    private Recipe Friday;
    private Recipe Saturday;

    public MealPlan(Recipe sunday, Recipe monday, Recipe tuesday, Recipe wednesday, Recipe thursday, Recipe friday, Recipe saturday) {
        this.Sunday = sunday;
        this.Monday = monday;
        this.Tuesday = tuesday;
        this.Wednesday = wednesday;
        this.Thursday = thursday;
        this.Friday = friday;
        this.Saturday = saturday;
    }

    public Recipe getSunday() {
        return Sunday;
    }

    public Recipe getMonday() {
        return Monday;
    }

    public Recipe getTuesday() {
        return Tuesday;
    }

    public Recipe getWednesday() {
        return Wednesday;
    }

    public Recipe getThursday() {
        return Thursday;
    }

    public Recipe getFriday() {
        return Friday;
    }

    public Recipe getSaturday() {
        return Saturday;
    }
}
