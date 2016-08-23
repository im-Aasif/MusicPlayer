package com.nerdcheck.musicplayer.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nerdcheck.musicplayer.R;
import com.nerdcheck.musicplayer.adapter.AlbumAdapter;
import com.nerdcheck.musicplayer.helper.ClickListener;
import com.nerdcheck.musicplayer.helper.RecyclerTouchListener;
import com.nerdcheck.musicplayer.helper.SongContentResolver;
import com.nerdcheck.musicplayer.model.Album;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumsFragment extends Fragment {

    //private RecyclerView recyclerView;
    private FastScrollRecyclerView recyclerView;
    private Context context;
    private ArrayList<Album> albumsList = new ArrayList<>();
    private AlbumAdapter albumAdapter;

    public AlbumsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        recyclerView = (FastScrollRecyclerView) view.findViewById(R.id.albums_recycler);

        SongContentResolver.getAlbumList(albumsList,getContext());
        albumAdapter = new AlbumAdapter(albumsList,getContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(albumAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Album album = albumsList.get(position);
                String toastText = "Album selected is "+album.getTitle();
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
        albumsList.clear();
    }
}
