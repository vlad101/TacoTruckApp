package com.tacotruck.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Util {
        
    /**
     * Generate ingredient random variation (+/-10).
     * 
     * @return random ingredient percent variation.
     */
    public static int generateRandomPrcValue() {
        int min = -10, max = 10;
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    /**
     * Round off a value to 2 decimal places using a BigDecimal.
     * 
     * @param value - value to be rounded
     * @param places - number of places.
     * @return - rounded off result.
     */
    public static double round(double value, int places) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
