package com.trex.melodaze;

public class Songs {
    private String title;
    private int duration;

    public Songs(String title, int duration) {
        this.duration = duration;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }
}