package com.nerdcheck.musicplayer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nerdcheck.musicplayer.R;
import com.nerdcheck.musicplayer.model.Album;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>
        implements FastScrollRecyclerView.SectionedAdapter {

    private ArrayList<Album> albumList = new ArrayList<>();
    private Context context;

    public AlbumAdapter(ArrayList<Album> albumList, Context context) {
        this.albumList = albumList;
        this.context = context;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_cardview_row, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        Album currAlbum = albumList.get(position);

        Glide.with(context).load(currAlbum.getArt_uri())
                .placeholder(R.drawable.album_art_placeholder)
                .error(R.drawable.album_art_placeholder)
                .crossFade()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.albumArt);

        String title = checkStringLength(currAlbum.getTitle());
        holder.albumName.setText(title);

        String artistName = checkStringLength(currAlbum.getArtist());
        holder.albumArtist.setText(artistName);

    }

    private String checkStringLength(String fullString) {
        if (fullString.length() >= 25) {
            return fullString.substring(0, 25) + "...";
        } else {
            return fullString;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        Album currAlbum = albumList.get(position);
        return currAlbum.getTitle().substring(0, 1);
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder{
        private TextView albumName, albumArtist;
        private ImageView albumArt;
        public AlbumViewHolder(View itemView) {
            super(itemView);
            albumArt = (ImageView)itemView.findViewById(R.id.album_img);
            albumArtist = (TextView) itemView.findViewById(R.id.artist_name);
            albumName = (TextView) itemView.findViewById(R.id.album_name);

        }
    }
}
