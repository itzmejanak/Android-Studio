package com.weather.SkyScout;

public class CapitalizeFirstLetter {

    // Method to capitalize the first letter of a string
    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input; // Return unchanged if input is null or empty
        }

        // Convert the first character to uppercase and concatenate with the rest of the string
        return Character.toUpperCase(input.charAt(0)) + input.substring(1);
    }
}
