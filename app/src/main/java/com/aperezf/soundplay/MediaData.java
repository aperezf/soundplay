package com.aperezf.soundplay;

import java.io.Serializable;

public class MediaData implements Serializable {

    private String pathFile;
    private String title;
    private String artist;
    private int duration;

    public MediaData(String pathFile, String title, String artist, int duration) {
        this.pathFile = pathFile;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }

    MediaData(String pathFile, String title, String artist) {
        this.pathFile = pathFile;
        this.title = title;
        this.artist = artist;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString(){
        return "{pathfile: "+pathFile+"}";
    }
}
