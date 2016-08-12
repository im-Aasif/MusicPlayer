package com.nerdcheck.musicplayer.model;

public class Artist {
    int id;
    String key;
    String name;
    int numberOfAlbums;
    int numberOfTracks;

    public Artist(int id, String key, String name, int numberOfAlbums, int numberOfTracks) {
        this.id = id;
        this.key = key;
        this.name = (name.equals("<unknown>") ? "Unknown artist" : name);
        this.numberOfAlbums = numberOfAlbums;
        this.numberOfTracks = numberOfTracks;
    }

    public Artist(int id, String key, String name) {
        this(id, key, name, -1, -1);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfAlbums() {
        return numberOfAlbums;
    }

    public void setNumberOfAlbums(int numberOfAlbums) {
        this.numberOfAlbums = numberOfAlbums;
    }

    public int getNumberOfTracks() {
        return numberOfTracks;
    }

    public void setNumberOfTracks(int numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
    }
}
