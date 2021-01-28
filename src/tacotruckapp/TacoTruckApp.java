package tacotruckapp;

import com.tacotruck.business.TacoTruck;

public class TacoTruckApp {
    public static void main(String[] args) {
        TacoTruck myTruck = new TacoTruck();
        // initialize all the necessary components (inventory, recipes, etc.)
        myTruck.setup();
        // display the menu and run the entire system
        myTruck.run();
    }
}
