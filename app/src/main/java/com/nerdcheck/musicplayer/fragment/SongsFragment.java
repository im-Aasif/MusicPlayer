package com.nerdcheck.musicplayer.fragment;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nerdcheck.musicplayer.R;
import com.nerdcheck.musicplayer.activity.MainActivity;
import com.nerdcheck.musicplayer.adapter.SongsAdapter;
import com.nerdcheck.musicplayer.helper.ClickListener;
import com.nerdcheck.musicplayer.helper.DividerItemDecoration;
import com.nerdcheck.musicplayer.helper.RecyclerTouchListener;
import com.nerdcheck.musicplayer.helper.SongContentResolver;
import com.nerdcheck.musicplayer.model.Song;
import com.nerdcheck.musicplayer.service.MusicService;

import java.util.ArrayList;
import java.util.List;

public class SongsFragment extends Fragment {

    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<Song> songsList = new ArrayList<>();
    private SongsAdapter songsAdapter;
    private MusicService musicService;
    private Intent playIntent;
    private Boolean musicBound = false;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        songsList.clear();
    }

    public SongsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songs, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.song_recycler);
        SongContentResolver.getSongList(songsList, getContext());
        songsAdapter = new SongsAdapter(songsList, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(songsAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Song song = songsList.get(position);
                String toastText = "Song selected is " + song.getTitle();
//                Toast.makeText(getContext(),toastText,Toast.LENGTH_LONG).show();
                Snackbar.make(view, toastText, Snackbar.LENGTH_LONG).show();

                musicService.setSong(position);
                musicService.playSong();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view;
    }


    public ServiceConnection musicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) iBinder;
            musicService = binder.getService(); //Get Service
            musicService.setList(songsList); //Get Songs List
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicBound = false;
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        if(playIntent == null){
            playIntent = new Intent(getContext(),MusicService.class);
            getActivity().bindService(playIntent,musicConnection, Context.BIND_AUTO_CREATE);
            getActivity().startService(playIntent);
        }
    }

    @Override
    public void onDestroy() {
        getActivity().stopService(playIntent);
        musicService = null;
        super.onDestroy();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater = new MenuInflater(getContext());
        inflater.inflate(R.menu.songs_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_shuffle:
                //shuffle
                break;
            case R.id.action_exit:
                getActivity().stopService(playIntent);
                musicService=null;
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
