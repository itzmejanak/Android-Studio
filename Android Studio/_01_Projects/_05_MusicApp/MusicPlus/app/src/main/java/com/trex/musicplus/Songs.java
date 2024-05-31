package com.trex.musicplus;

public class Songs {
    private String title;
    private int duration;
    private int icon;
    private String folder_name;
    private int folder_length;

    public Songs(String title, int duration, int icon) {
        this.title = title;
        this.duration = duration;
        this.icon = icon;
    }

    public Songs(String folder_name, int folder_length) {
        this.folder_name = folder_name;
        this.folder_length = folder_length;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public int getIcon() {
        return icon;
    }

    public String getFolderName() {
        return folder_name;
    }

    public int getFolderLength() {
        return folder_length;
    }
}
