package com.tacotruck.model;

import java.util.Arrays;
import com.tacotruck.util.Util;

public class Order {
    // stores a unique order ID counter
    private static int orderID = 0;
    // stores unique order ID for each order
    private int uniqueOrderId;
    // stores a menu item ID matching one of the IDs on the menu (0, 1, 2, 3)
    private int menuItemID;
    // stores a total cost to make
    private double totalCost;
    // stores a taco price
    private double price;
    // stores total profit
    private double totalProfit;
    // stores a service fee
    private final double serviceCharge;
    // stores an amount of each ingredient was used to make this particular taco
    private double[] ingredients;
    // stores an inventory object
    private final Inventory inventory;

    public Order() {
        // increment orderID in the constructor when a new Order object is generated
        orderID++;
        this.uniqueOrderId = 0;
        this.ingredients = new double[5];
        this.inventory = new Inventory();
        this.menuItemID = 0;
        this.totalCost = 0;
        this.price = 0;
        this.totalProfit = 0;
        this.serviceCharge = 20;
    }
    
    /**
     * Generate a summary string.
     * 
     * @return summary string. 
     */
    private String getSummaryStr() {
        StringBuilder builder = new StringBuilder();
        //builder.append(this.orders);
        
        return builder.toString();
    }
   
    /**
     * Calculate total cost for making a taco.
     */
    public void calculateTotalCost() {
        if(this.inventory != null) {
            double totalIngredientsCost = 0;
            for(int i = 0; i < this.inventory.getIngredientNames().length; i++) {
                totalIngredientsCost += this.inventory.getIngredientCosts()[i] * this.ingredients[i];
            }
            this.totalCost = this.inventory.getShellCost() + totalIngredientsCost;
            this.totalCost = Util.round(this.totalCost, 2);
        }
    }
    
    /**
     * Calculate a total price.
     */
    public void calculatePrice() {
        this.price = this.totalCost + this.totalCost * (this.serviceCharge / 100.0);
        this.price = Util.round(this.price, 2);
    }
    
    /**
     * Calculate profit.
     */
    public void calculateProfit() {
        this.totalProfit = this.price - this.totalCost;
        this.totalProfit = Util.round(this.totalProfit, 2);
    }
    
    /**
     * Set +/- 10% variation for each ingredient for a selected menu item.
     * 
     * @param menuIngridients - recipe ingredients.
     * @return array of ingredient variation.
     */
    private double[] setIngredientsVariation(double[] menuIngridients) {
        double[] variationIngredients = new double[5];
        if(menuIngridients != null) {
            variationIngredients = new double[menuIngridients.length];
            for(int i = 0; i < menuIngridients.length; i++) {
                // get random integer in the range from -10 to 10
                double randPrc = Util.generateRandomPrcValue() / 100.00;
                // calculate and round off to two decimal places
                variationIngredients[i] = Util.round(menuIngridients[i] + menuIngridients[i] * randPrc, 2);
            }
        }
        return variationIngredients;
    }
    
    public static int getOrderID() {
        return orderID;
    }

    public static void setOrderID(int orderID) {
        Order.orderID = orderID;
    }

    public int getMenuItemID() {
        return menuItemID;
    }

    public void setMenuItemID(int menuItemID) {
        this.menuItemID = menuItemID;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(double[] ingredients) {
        this.ingredients = this.setIngredientsVariation(ingredients);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getUniqueOrderId() {
        return uniqueOrderId;
    }

    public void setUniqueOrderId(int uniqueOrderId) {
        this.uniqueOrderId = uniqueOrderId;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    @Override
    public String toString() {
        return "Order{" + "uniqueOrderId=" + uniqueOrderId + 
                            ", menuItemID=" + menuItemID + 
                            ", totalCost=" + totalCost + 
                            ", totalProfit=" + totalProfit +
                            ", price=" + price + 
                            ", serviceCharge=" + serviceCharge + 
                            ", ingredients=" + Arrays.toString(ingredients) + 
                            ", inventory=" + inventory + '}';
    }
}