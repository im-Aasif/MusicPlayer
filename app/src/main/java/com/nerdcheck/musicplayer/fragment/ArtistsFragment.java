package com.nerdcheck.musicplayer.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nerdcheck.musicplayer.R;
import com.nerdcheck.musicplayer.adapter.AlbumAdapter;
import com.nerdcheck.musicplayer.adapter.ArtistAdapter;
import com.nerdcheck.musicplayer.adapter.SongsAdapter;
import com.nerdcheck.musicplayer.helper.ClickListener;
import com.nerdcheck.musicplayer.helper.DividerItemDecoration;
import com.nerdcheck.musicplayer.helper.RecyclerTouchListener;
import com.nerdcheck.musicplayer.helper.SongContentResolver;
import com.nerdcheck.musicplayer.model.Artist;
import com.nerdcheck.musicplayer.model.Song;

import java.util.ArrayList;

public class ArtistsFragment extends Fragment {
    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<Artist> artistList = new ArrayList<>();
    private ArtistAdapter artistAdapter;


    public ArtistsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artists, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.artists_recycler);
        SongContentResolver.getArtistsList(artistList,getContext());
        artistAdapter = new ArtistAdapter(artistList,getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(artistAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Artist artist = artistList.get(position);
                String toastText = "Artist selected is "+artist.getName();
//                Toast.makeText(getContext(),toastText,Toast.LENGTH_LONG).show();
                Snackbar.make(view,toastText,Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        artistList.clear();
    }
}
