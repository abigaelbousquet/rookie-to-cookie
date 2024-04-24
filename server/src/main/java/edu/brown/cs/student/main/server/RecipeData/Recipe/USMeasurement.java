package edu.brown.cs.student.main.server.RecipeData.Recipe;

public class USMeasurement {
    private final double amount;
    private final String unitLong;

    public USMeasurement(double amount, String unitLong) {
        this.amount = amount;
        this.unitLong = unitLong;
    }

    public double getAmount() {
        return amount;
    }

    public String getUnitLong() {
        return unitLong;
    }
}
