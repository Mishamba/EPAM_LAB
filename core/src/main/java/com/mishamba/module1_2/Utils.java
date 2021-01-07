package com.mishamba.module1_2;

public class Utils {
    public static boolean isAllPositiveNumbers(String ... str) {
        for (String number : str) {
            if (!new com.mishamba.module1.StringUtils().isPositiveNumber(number)) {
                return false;
            }
        }

        return true;
    } 
}
