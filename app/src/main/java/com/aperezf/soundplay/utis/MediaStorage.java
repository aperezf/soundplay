package com.aperezf.soundplay.utis;


public class MediaStorage {

    private static MediaStorage singleton = null;

    private MediaStorage(){

    }

    public static MediaStorage getInstance(){
        if (singleton == null){
            singleton = new MediaStorage();
        }
        return singleton;
    }




}
