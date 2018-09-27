package com.aperezf.soundplay;


import android.os.Parcel;
import android.os.Parcelable;

public class MediaData implements Parcelable{

    private String pathFile;
    private String title;
    private String artist;

    public static final Creator<MediaData> CREATOR = new Creator<MediaData>() {
        @Override
        public MediaData createFromParcel(Parcel in) {
            return new MediaData(in);
        }

        @Override
        public MediaData[] newArray(int size) {
            return new MediaData[size];
        }
    };

    public MediaData(String pathFile, String title, String artist) {
        this.pathFile = pathFile;
        this.title = title;
        this.artist = artist;
    }

    protected MediaData(Parcel in) {
        readFromParcel(in);
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


    @Override
    public String toString(){
        return "{pathfile: "+pathFile+"}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pathFile);
        dest.writeString(title);
        dest.writeString(artist);
    }

    public void readFromParcel(Parcel in){
        pathFile = in.readString();
        title = in.readString();
        artist = in.readString();
    }
}
