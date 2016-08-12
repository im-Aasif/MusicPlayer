package com.nerdcheck.musicplayer.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nerdcheck.musicplayer.R;
import com.nerdcheck.musicplayer.adapter.SongsAdapter;
import com.nerdcheck.musicplayer.helper.ClickListener;
import com.nerdcheck.musicplayer.helper.DividerItemDecoration;
import com.nerdcheck.musicplayer.helper.RecyclerTouchListener;
import com.nerdcheck.musicplayer.helper.SongContentResolver;
import com.nerdcheck.musicplayer.model.Song;

import java.util.ArrayList;
import java.util.List;

public class SongsFragment extends Fragment {

    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<Song> songsList = new ArrayList<>();
    private SongsAdapter songsAdapter;

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
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view;
    }

}
