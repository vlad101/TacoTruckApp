package com.tacotruck.business;

import com.tacotruck.model.Inventory;
import com.tacotruck.model.Order;
import java.text.NumberFormat;
import java.util.Arrays;

public class OrderList {

    // stores inventory information
    private final Inventory inventory;
    // stores all the successful orders
    private Order[] orders;
    // stores the number of orders placed
    private int totalOrdersPlaced;
    // stores  total ingredients and taco shell costs
    private double totalCost;
    // total profit so far (cost of recipe taco - cost to make taco)
    private double totalProfit;
    
    public OrderList() {
        this.inventory = new Inventory();
        // size is set to the initial number of taco shells
        this.orders = new Order[this.inventory.getShells()];
        this.totalOrdersPlaced = 0;
        this.totalCost = 0;
        this.totalProfit = 0;
    }
    
    /**
     * Add order.
     * 
     * @param order - taco order
     */
    public void add(Order order) {
        if(order != null) {
            // add the order to the order list
            this.orders[totalOrdersPlaced] = order;
            // update total orders placed value
            this.totalOrdersPlaced++;
        } 
    }
    
     /**
     * Generate a summary string.
     * 
     * @param ingredientNames - ingredient names
     * @return summary string. 
     */
    public String getSummaryStr(String[] ingredientNames) {
        StringBuilder builder = new StringBuilder();
        if(ingredientNames != null) {
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
            double totalCosts = 0;
            double totalSales = 0;
            double totalProfits = 0;
            int[] totalMenuItems = new int[ingredientNames.length];
            // gell sales
            for(Order order : this.orders) {
                if(order != null) {
                    totalCosts += order.getTotalCost();
                    totalSales += order.getPrice();
                    totalProfits += order.getTotalProfit();
                    totalMenuItems[order.getMenuItemID()]++;
                }
            }
            builder.append("Total number of tacos sold: ");
            builder.append(this.totalOrdersPlaced);
            builder.append("\n");
            // get total number of sold items
            for(int i = 0; i < totalMenuItems.length; i++) {
                builder.append("Total number of ");
                builder.append(ingredientNames[i]);
                builder.append(" sold: ");
                builder.append(totalMenuItems[i]);
                builder.append("\n");
            }
            builder.append("Total cost: ");
            builder.append(currencyFormat.format(totalCosts));
            builder.append("\n");
            builder.append("Total sales: ");
            builder.append(currencyFormat.format(totalSales));
            builder.append("\n");
            builder.append("Total profit: ");
            builder.append(currencyFormat.format(totalProfits));
        }
        return builder.toString();
    }
    
    public Inventory getInventory() {
        return inventory;
    }

    public Order[] getOrders() {
        return orders;
    }

    public void setOrders(Order[] orders) {
        this.orders = orders;
    }

    public int getTotalOrdersPlaced() {
        return totalOrdersPlaced;
    }

    public void setTotalOrdersPlaced(int totalOrdersPlaced) {
        this.totalOrdersPlaced = totalOrdersPlaced;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    @Override
    public String toString() {
        return "OrderList{" + "inventory=" + inventory + 
                ", orders=" + Arrays.toString(orders) + 
                ", totalOrdersPlaced=" + totalOrdersPlaced + 
                ", totalCost=" + totalCost + 
                ", totalProfit=" + totalProfit + '}';
    }
}
