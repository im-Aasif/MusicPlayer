package com.nerdcheck.musicplayer.model;

public class Album {
    int id;
    String key;
    String title;
    String artist;
    int numberOfTracks;
    String art_uri;

    public Album(int id, String key, String title, String artist, int numberOfTracks, String art_uri) {
        this.id = id;
        this.key = key;
        this.title = title;
        this.artist = artist;
        this.numberOfTracks = numberOfTracks;
        this.art_uri = art_uri;
    }
    public Album(int id, String key, String title, String art_uri) {
        this(id, key, title, null, -1, art_uri);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public int getNumberOfTracks() {
        return numberOfTracks;
    }

    public void setNumberOfTracks(int numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
    }

    public String getArt_uri() {
        return art_uri;
    }

    public void setArt_uri(String art_uri) {
        this.art_uri = art_uri;
    }
}
