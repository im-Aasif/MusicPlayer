package com.nerdcheck.musicplayer.model;

public class Song {
    Long id;
    String title, artist, album, displayName, composer, albumArt;

    public Song() {
    }

    public Song(Long id, String title, String artist, String album, String displayName) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.displayName = displayName;
//        this.composer = composer;
//        this.albumArt = albumArt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }


}
