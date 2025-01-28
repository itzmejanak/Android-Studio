package com.weather.SkyScout;

public class HourlyModel {
    private String temperature;
    private String time;
    private int iconResId;

    public HourlyModel(String temperature, String time, int iconResId) {
        this.temperature = temperature;
        this.time = time;
        this.iconResId = iconResId;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getName() {
        return time;
    }

    public int getIconResId() {
        return iconResId;
    }
}
