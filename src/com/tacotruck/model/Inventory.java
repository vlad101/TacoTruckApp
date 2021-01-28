package com.tacotruck.model;

import com.tacotruck.util.Util;
import java.util.Arrays;

public class Inventory {

    // storess the amount of ingredients
    private double[] ingredients;
    // stores initial amount of ingredients
    private final double ingredientInitialAmount;
    // stores an initial amount of shells
    private final int shellsInitialAmount;
    // stores ingredient names
    private final String[] ingredientNames;
    // stores the ingredient costs
    private final double[] ingredientCosts;
    // stores the amount of taco shells
    private int shells;
    // stores the shell cost
    private final double shellCost;
    
    public Inventory() {
        this.ingredientInitialAmount = 400;
        this.shellsInitialAmount = 400;
        this.ingredientNames = new String[] { "Beef", "Fish", "Chili", "Tomato", "Onion" };
        this.ingredients = new double[] { 
                                this.ingredientInitialAmount,
                                this.ingredientInitialAmount,
                                this.ingredientInitialAmount,
                                this.ingredientInitialAmount,
                                this.ingredientInitialAmount
        };
        this.ingredientCosts = new double[] { 0.56, 0.98, 0.45, 0.81, 1.00 };
        this.shells = this.shellsInitialAmount;
        this.shellCost = 0.25;
    }
    
    /**
     * Generate a summary string.
     * 
     * @return summary string. 
     */
    public String getSummaryStr() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < this.ingredients.length; i++) {
            double amountUsed = this.ingredientInitialAmount - this.ingredients[i];
            builder.append("Amount of ")
                    .append(this.ingredientNames[i])
                    .append(" used: ")
                    .append(Util.round(amountUsed, 2))
                    .append(" (out of ")
                    .append(this.ingredientInitialAmount)
                    .append(")\n");
        }
        // consume last new line character
        
        return builder.toString();
    }
    
    /**
     * Update inventory (ingredients, shells).
     * 
     * @param ingredients - placed order ingredients.
     */
    public void update(double[] ingredients) {
        // reduce the number of taco shells
        this.shells--;
        // shells cannot be negative
        this.shells = (this.shells < 0) ? 0 : this.shells;
        // reduce) the ingredient amounts
        for(int i = 0; i < ingredients.length; i++) {
            this.ingredients[i] -= ingredients[i];
            // ingredient count cannot be negative
            this.ingredients[i] = (this.ingredients[i] < 0) ? 0 : this.ingredients[i];
        }
    }

    public void setIngredients(double[] ingredients) {
        this.ingredients = ingredients;
    }
    
    public double[] getIngredients() {
        return ingredients;
    }

    public String[] getIngredientNames() {
        return ingredientNames;
    }

    public double[] getIngredientCosts() {
        return ingredientCosts;
    }

    public int getShells() {
        return shells;
    }

    public void setShells(int shells) {
        this.shells = shells;
    }

    public double getShellCost() {
        return shellCost;
    }

    public double getIngredientInitialAmount() {
        return ingredientInitialAmount;
    }

    public double getShellsInitialAmount() {
        return shellsInitialAmount;
    }

    @Override
    public String toString() {
        return "Inventory{" + "ingredients=" + Arrays.toString(ingredients) + 
                        ", ingredientNames=" + Arrays.toString(ingredientNames) + 
                        ", ingredientCosts=" + Arrays.toString(ingredientCosts) + 
                        ", shells=" + shells + 
                        ", ingredientInitialAmount=" + ingredientInitialAmount +
                        ", shellsInitialAmount=" + shellsInitialAmount + 
                        ", shellCost=" + shellCost + '}';
    }
}