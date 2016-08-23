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
import com.nerdcheck.musicplayer.model.Artist;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.ArrayList;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>
        implements FastScrollRecyclerView.SectionedAdapter {

    private ArrayList<Artist> artistList = new ArrayList<>();
    private Context context;

    public ArtistAdapter(ArrayList<Artist> artistList, Context context) {
        this.artistList = artistList;
        this.context = context;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_row, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        Artist currArtist = artistList.get(position);

        Glide.with(context).load(currArtist.getKey())
                .placeholder(R.drawable.album_art_placeholder)
                .error(R.drawable.album_art_placeholder)
                .crossFade()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.artistImage);

        String artistName = currArtist.getName();
        holder.artistName.setText(artistName);

        String totalAlbums = currArtist.getNumberOfAlbums()+" Albums";
        holder.artistAlbums.setText(totalAlbums);

        String totalTracks = currArtist.getNumberOfTracks()+" Tracks";
        holder.artistTracks.setText(totalTracks);
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return artistList.get(position).getName().substring(0, 1);
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder{
        private TextView artistName, artistAlbums, artistTracks;
        private ImageView artistImage;
        public ArtistViewHolder(View itemView) {
            super(itemView);
            artistImage = (ImageView)itemView.findViewById(R.id.artist_img);
            artistName = (TextView)itemView.findViewById(R.id.artist_name);
            artistAlbums = (TextView)itemView.findViewById(R.id.artist_album);
            artistTracks = (TextView)itemView.findViewById(R.id.artist_tracks);
        }
    }
}
