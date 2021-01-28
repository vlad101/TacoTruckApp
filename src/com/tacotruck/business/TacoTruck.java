package com.tacotruck.business;

import com.tacotruck.model.Inventory;
import com.tacotruck.model.Order;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class TacoTruck {
    
    // stores the name of Taco Truck
    private final String tacoTruckName;
    // stores inventory information
    private Inventory inventory;
    // stores order list
    private OrderList orders;
    // stores menu item names
    private String[] menuItems;
    // stores amounts of ingredients for each menu item
    private final double[][] recipes;
    // stores main menu text
    private String mainMenuStr;
    // stores main menu option 1
    private String mainMenuOpt1;
    // stores user input Scanner object
    private final Scanner keyboard;
    // stores invalid user choice text
    private final String invalidUserInput;
    
    public TacoTruck() {
        this.inventory = new Inventory();
        this.orders = new OrderList();
        this.keyboard = new Scanner(System.in);
        this.invalidUserInput = "Invalid choice! Please Try again!";
        this.tacoTruckName =  "TacoTruck1";
        this.menuItems = new String[] { "MenuItem1", "MenuItem2", "MenuItem3", "MenuItem4" };
        this.recipes = new double[][] {
                                        { 2.00, 1.00, 1.00, 1.00, 1.00 },
                                        { 0.00, 2.00, 0.00, 1.00, 0.00 },
                                        { 1.00, 1.00, 2.00, 0.00, 1.00 },
                                        { 0.00, 1.00, 0.00, 2.00, 1.00 }
                                                                         };
        
    }

    /**
     * Initialize all components
     */
    public void setup() {
        this.mainMenuStr = this.buildMainMenuStr();
        this.mainMenuOpt1 = this.buildMainMenuOption1Str();
    }
    
    /**
     *  Display the menu and run the entire system.
     */
    public void run() {
        int userChoice;
        do {
            int minInput = 1, maxInput = 4;
            userChoice = this.getUserChoice(this.mainMenuStr, minInput, maxInput);
            switch(userChoice) {
                case 1:
                    minInput = 1;
                    maxInput = 5;
                    int recipeUserChoice = this.getUserChoice(this.mainMenuOpt1, minInput, maxInput);
                    this.doPlaceOrder(recipeUserChoice);
                    break;
                case 2:
                    this.simulatePlaceOrder();
                    break;
                case 3:
                    System.out.println(this.getSummaryStr());
                    break;
                case 4:
                    // exit
                    System.exit(0);
                    break;
            }
        }while(userChoice != 4);
    }
    
    /**
     * Simulate placing order until no more ingredients.
     */
    private void simulatePlaceOrder() {
        while(true) {
            if(this.recipes != null) {
                int recipeUserChoice = (new Random()).nextInt(this.recipes.length)+1;
                this.doPlaceOrder(recipeUserChoice);
            }
        }
    }
    
    /**
     * Place an order.
     * 
     * @param recipeUserChoice - a user recipe choice.
     */
    private void doPlaceOrder(int recipeUserChoice) {
        int menuItemChoice = recipeUserChoice - 1;
        // place order
        if(recipeUserChoice != 5) {
            Order order = this.setOrder(menuItemChoice);
            if(this.isValidOrder(order)) {
                this.runOrderSuccess(order, menuItemChoice);
            } else {
                this.runOrderFail();
            }
        }
    }
    
    /**
     * Run a success order actions.
     * 
     * @param order - placed order.
     * @param menuItemChoice - taco choice.
     */
    private void runOrderSuccess(Order order, int menuItemChoice) {
        if(order != null) {
            // add the order to the order list
            this.orders.add(order);
            // update inventory
            this.inventory.update(order.getIngredients());
            // display a success message
            System.out.println("Order #" + order.getUniqueOrderId() + " for " + this.menuItems[menuItemChoice] + " successfully placed");
        }
    }
    
    /**
     * Run a fail order actions.
     */
    private void runOrderFail() {
        System.out.println("Order cannot be fulfilled. Not enough ingredients.");
        // display Sales summary
        System.out.println(this.getSummaryStr());
        // terminate the program
        System.exit(0);
    }
    
    /**
     * Generate a summary string.
     * 
     * @return summary string. 
     */
    private String getSummaryStr() {
        StringBuilder builder = new StringBuilder();
        //builder.append(this.orders);
        builder.append("Sales summary:\n");
        builder.append("==============\n");
        // get inventory summary
        if(this.inventory != null) {
            builder.append(this.inventory.getSummaryStr());
        }
        // get order list summary
        if(this.orders != null) {
            builder.append(this.orders.getSummaryStr(this.menuItems));
        }
        return builder.toString();
    }
    
    /**
     * Determine if order can be made.
     * 
     * @param order = a taco order
     * @return 
     */
    private boolean isValidOrder(Order order) {
        boolean isValid = true;
        if(order != null && this.inventory != null) {
            // validate taco shell amount
            if(this.inventory.getShells() == 0) {
                isValid = false;
            }
            // validate ingredients amount
            if(isValid) {
                int ingredientCount = 0;
                for(int i = 0; i < order.getIngredients().length; i++) {
                    if(this.inventory.getIngredients()[i] - order.getIngredients()[i] < 0) {
                        ingredientCount++;
                    }
                }
                // must be out of at least two (2) ingredients
                if(ingredientCount >= 2) {
                    isValid = false;
                }
            }
        }
        return isValid;
    }
    
    /**
     * Set order.
     * 
     * @param menuItemChoice - menu item choice.
     * @return created order.
     */
    private Order setOrder(int menuItemChoice) {
        Order order = new Order();
        order.setUniqueOrderId(Order.getOrderID());
        order.setIngredients(this.recipes[menuItemChoice]);
        order.setMenuItemID(menuItemChoice);
        order.calculateTotalCost();
        order.calculatePrice();
        order.calculateProfit();
        return order;
    }
    
    /**
     * Build main menu option 1.
     * 
     * @return main menu option 1 string.
     */
    private String buildMainMenuOption1Str() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.tacoTruckName).append(" Menu!\n==========================\n");
        for(int i = 0; i < this.recipes.length; i++) {
            builder.append("[").append(i + 1).append("] ").append(menuItems[i]).append("(");
            //String subMenuStr = "";
            for(int j = 0; j < this.recipes[i].length; j++) {
                if(this.recipes[i][j] > 0) {
                    builder.append(inventory.getIngredientNames()[j])
                        .append(",");
                }
            }
            // remove last comma
            builder.deleteCharAt(builder.length()-1);
            builder.append(")\n");
        }
        builder.append("[5] Go back\nWhat would you like to do / order? ");
        return builder.toString();
    }
    
    /**
     * Build main menu.
     * 
     * @return main menu string.
     */
    private String buildMainMenuStr() {
        StringBuilder builder = new StringBuilder();
        builder.append("Welcome to ")
                .append(this.tacoTruckName)
                .append("!\n==========================\n[1] Manual order")
                .append("\n[2] Simulated ordering\n[3] Sales summary")
                .append("\n[4] Quit\nWhat would you like to do? ");
        return builder.toString();
    }   
    
    /**
     * Get user menu choice.
     * 
     * @param menuStr - menu text
     * @param minInput - min user choice
     * @param maxInput - max user choice
     * @return valid user choice
     */
    private int getUserChoice(String menuStr, int minInput, int maxInput) {
        int userInput = -1;
        do {
            try {
                System.out.print(menuStr);
                String inputStr = keyboard.nextLine();
                userInput = Integer.parseInt(inputStr);
                if(userInput < minInput || userInput > maxInput) {
                    System.out.println(this.invalidUserInput);
                }
            } catch (NumberFormatException e) {
                System.out.println(this.invalidUserInput);
            }
        } while (userInput < minInput || userInput > maxInput);
        return userInput;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public OrderList getOrders() {
        return orders;
    }

    public void setOrders(OrderList orders) {
        this.orders = orders;
    }

    public String[] getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(String[] menuItems) {
        this.menuItems = menuItems;
    }

    public double[][] getRecipes() {
        return recipes;
    }

    public String getTacoTruckName() {
        return tacoTruckName;
    }

    public String getMainMenuStr() {
        return mainMenuStr;
    }

    public void setMainMenuStr(String mainMenuStr) {
        this.mainMenuStr = mainMenuStr;
    }

    public String getInvalidUserInput() {
        return invalidUserInput;
    }

    public String getMainMenuOpt1() {
        return mainMenuOpt1;
    }

    public void setMainMenuOpt1(String mainMenuOpt1) {
        this.mainMenuOpt1 = mainMenuOpt1;
    }
    
    public Scanner getKeyboard() {
        return keyboard;
    }

    @Override
    public String toString() {
        return "TacoTruck{" + "tacoTruckName=" + tacoTruckName + 
                                ", inventory=" + inventory + 
                                ", orders=" + orders + ", "
                                + "menuItems=" + Arrays.toString(menuItems) + 
                                ", recipes=" + Arrays.toString(recipes) + 
                                ", mainMenuStr=" + mainMenuStr + 
                                ", mainMenuOpt1=" + mainMenuOpt1 + 
                                ", keyboard=" + keyboard + 
                                ", invalidUserInput=" + invalidUserInput + '}';
    }
}