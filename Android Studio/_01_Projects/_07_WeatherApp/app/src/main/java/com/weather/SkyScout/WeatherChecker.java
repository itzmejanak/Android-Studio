package com.weather.SkyScout;

public class WeatherChecker {

    // Define weather condition constants
    public static final int CONDITION_SUNNY = 1;
    public static final int CONDITION_CLOUDY = 2;
    public static final int CONDITION_RAINY = 3;
    public static final int CONDITION_STORMY = 4;
    public static final int CONDITION_SNOWY = 5;
    public static final int CONDITION_WINDY = 6;
    public static final int CONDITION_FOGGY = 7;
    public static final int CONDITION_HAILSTORM = 8;
    public static final int CONDITION_THUNDERSTORM = 9;
    public static final int CONDITION_TORNADO = 10;

    // Method to get drawable resource ID based on weather code
    public int weatherImage(int code){
        switch (code) {
            case CONDITION_SUNNY:
                return R.drawable.sunny;
            case CONDITION_CLOUDY:
                return R.drawable.cloudy;
            case CONDITION_RAINY:
                return R.drawable.rainy;
            case CONDITION_STORMY:
                return R.drawable.stormy;
            case CONDITION_SNOWY:
                return R.drawable.snowy;
            case CONDITION_WINDY:
                return R.drawable.windy;
            case CONDITION_FOGGY:
                return R.drawable.foggy;
            case CONDITION_HAILSTORM:
                return R.drawable.hailstorm;
            case CONDITION_THUNDERSTORM:
                return R.drawable.thunder;
            case CONDITION_TORNADO:
                return R.drawable.tornado;
            default:
                return R.drawable.default_weather; // default image if code is unknown
        }
    }

    public String getWeatherName(int id) {
        switch (id) {
            case CONDITION_SUNNY:
                return "Sunny";
            case CONDITION_CLOUDY:
                return "Cloudy";
            case CONDITION_RAINY:
                return "Rainy";
            case CONDITION_STORMY:
                return "Stormy";
            case CONDITION_SNOWY:
                return "Snowy";
            case CONDITION_WINDY:
                return "Windy";
            case CONDITION_FOGGY:
                return "Foggy";
            case CONDITION_HAILSTORM:
                return "Hailstorm";
            case CONDITION_THUNDERSTORM:
                return "Thunderstorm";
            case CONDITION_TORNADO:
                return "Tornado";
            default:
                return "Sunny"; // default name if code is unknown
        }
    }

}
