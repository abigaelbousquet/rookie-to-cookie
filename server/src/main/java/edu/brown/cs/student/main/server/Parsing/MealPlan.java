package edu.brown.cs.student.main.server.Parsing;

import edu.brown.cs.student.main.server.Parsing.Recipe.Recipe;

import java.util.ArrayList;
import java.util.List;

public class MealPlan {
    private Recipe sunday;
    private Recipe monday;
    private Recipe tuesday;
    private Recipe wednesday;
    private Recipe thursday;
    private Recipe friday;
    private Recipe saturday;

    public MealPlan(Recipe sunday, Recipe monday, Recipe tuesday, Recipe wednesday, Recipe thursday, Recipe friday, Recipe saturday) {
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
    }

    public List<Recipe> getRecipes(){
        ArrayList<Recipe> recipeList = new ArrayList<>();
        recipeList.add(this.sunday);
        recipeList.add(this.monday);
        recipeList.add(this.tuesday);
        recipeList.add(this.wednesday);
        recipeList.add(this.thursday);
        recipeList.add(this.friday);
        recipeList.add(this.saturday);
        return recipeList;
    }
    public Recipe getSunday() {
        return sunday;
    }

    public Recipe getMonday() {
        return monday;
    }

    public Recipe getTuesday() {
        return tuesday;
    }

    public Recipe getWednesday() {
        return wednesday;
    }

    public Recipe getThursday() {
        return thursday;
    }

    public Recipe getFriday() {
        return friday;
    }

    public Recipe getSaturday() {
        return saturday;
    }


}
