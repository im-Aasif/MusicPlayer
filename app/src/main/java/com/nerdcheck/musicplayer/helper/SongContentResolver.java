package com.nerdcheck.musicplayer.helper;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.nerdcheck.musicplayer.model.Album;
import com.nerdcheck.musicplayer.model.Artist;
import com.nerdcheck.musicplayer.model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SongContentResolver {

    public static void getSongList(ArrayList<Song> songsList, Context context) {
        ContentResolver musicResolver = context.getContentResolver();
        //ContentResolver albumResolver = context.getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        //String selection = MediaStore.Audio.Media.IS_MUSIC + "?";
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
//        Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
//        Cursor albumCursor = albumResolver.query(albumUri, null, null, null, null);

        try {
            if (musicCursor != null && musicCursor.moveToFirst()) {
                //Get columns
                int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
                int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int displayNameColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
                int albumColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                int composerColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER);
//            int albumArtColumn = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
//            int albumIdColumn = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID);

                do {
                    long thisId = musicCursor.getLong(idColumn);
                    String thisTitle = musicCursor.getString(titleColumn);
                    String thisArtist = musicCursor.getString(artistColumn);
                    String thisAlbum = musicCursor.getString(albumColumn);
                    //String thisComposer = musicCursor.getString(composerColumn);
                    String thisDisplayName = musicCursor.getString(displayNameColumn);
//                String thisAlbumArt = albumCursor.getString(albumArtColumn);
//                String thisAlbumId = albumCursor.getString(albumIdColumn);
                    //Log.e("Music Cursor value", thisId + "\n" + thisTitle + "\n" + thisArtist + "\n" + thisAlbum + "\n" + thisDisplayName + "\n");
                    songsList.add(new Song(thisId, thisTitle, thisArtist, thisAlbum, thisDisplayName));
                } while (musicCursor.moveToNext());
                            }

        }
        catch (NullPointerException e){
            Log.e("Cursor Issue", e.toString());
        }
        musicCursor.close();
        songsList.trimToSize();


        Collections.sort(songsList, new Comparator<Song>() {
            @Override
            public int compare(Song a, Song b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });

    }

    public static void getAlbumList(ArrayList<Album> albumList, Context context) {
        Uri contentUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(MediaStore.Audio.Albums._ID);
            int album = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
            int album_key = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_KEY);
            int album_art = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
            int artist = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
            int numberOfTracks = cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS);
            do {
                if (cursor.getString(album).equals("sdcard")) continue;
                albumList.add(new Album(
                        cursor.getInt(id),
                        cursor.getString(album_key),
                        cursor.getString(album),
                        cursor.getString(artist),
                        cursor.getInt(numberOfTracks),
                        album_art > -1 ? cursor.getString(album_art) : null
                ));
            } while (cursor.moveToNext());
            cursor.close();
            albumList.trimToSize();

            Collections.sort(albumList, new Comparator<Album>() {
                @Override
                public int compare(Album a, Album b) {
                    return a.getTitle().compareTo(b.getTitle());
                }
            });

        }
    }

    public static void getArtistsList(ArrayList<Artist> artistList, Context context) {
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(MediaStore.Audio.Artists._ID);
            int artist = cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST);
            int artist_key = cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST_KEY);
            int tracks = cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS);
            int albums = cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS);

            do {
                if (cursor.getString(artist).equals("<unknown>")) continue;
                artistList.add(new Artist(
                        cursor.getInt(id),
                        cursor.getString(artist_key),
                        cursor.getString(artist),
                        cursor.getInt(albums),
                        cursor.getInt(tracks)
                ));
            } while (cursor.moveToNext());
            cursor.close();

            artistList.trimToSize();

            Collections.sort(artistList, new Comparator<Artist>() {
                @Override
                public int compare(Artist a, Artist b) {
                    return a.getName().compareTo(b.getName());
                }
            });
        }
    }
}

