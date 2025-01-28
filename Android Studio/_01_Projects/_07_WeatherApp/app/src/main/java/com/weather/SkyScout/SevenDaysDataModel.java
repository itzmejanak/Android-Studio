package com.weather.SkyScout;

public class SevenDaysDataModel {
    private String dayName;
    private  int weatherLogo;
    private String weatherStatus;
    private String MaxTemp;
    private String MinTemp;

    public SevenDaysDataModel(String dayName, int weatherLogo, String weatherStatus, String maxTemp, String minTemp) {
        this.dayName = dayName;
        this.weatherLogo = weatherLogo;
        this.weatherStatus = weatherStatus;
        this.MaxTemp = maxTemp;
        this.MinTemp = minTemp;
    }

    public String getDayName() {
        return dayName;
    }

    public int getWeatherLogo() {
        return weatherLogo;
    }

    public String getWeatherStatus() {
        return weatherStatus;
    }

    public String getMaxTemp() {
        return MaxTemp;
    }

    public String getMinTemp() {
        return MinTemp;
    }
}
