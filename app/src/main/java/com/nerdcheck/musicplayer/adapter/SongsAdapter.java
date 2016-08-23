package com.nerdcheck.musicplayer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nerdcheck.musicplayer.R;
import com.nerdcheck.musicplayer.model.Song;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder>
        implements FastScrollRecyclerView.SectionedAdapter {

    private ArrayList<Song> songsList = new ArrayList<>();
    private Context context;

    public SongsAdapter(ArrayList<Song> songsList, Context context) {
        this.songsList = songsList;
        this.context = context;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_row, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        Song currSong = songsList.get(position);

        Glide
                .with(context)
                .load(currSong.getAlbumArt())
                .placeholder(R.drawable.album_art_placeholder)
                .error(R.drawable.album_art_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(holder.songThumbnail);

        //holder.songThumbnail.setImageBitmap(currSong.getAlbumArt());

        String title = checkStringLength(currSong.getTitle());
        holder.songName.setText(title);

        String artistName = checkStringLength(currSong.getArtist());
        holder.songArtist.setText(artistName);

        holder.moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SongViewHolder svh = new SongViewHolder(view);
                PopupMenu popup = new PopupMenu(context, svh.moreInfo);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(context, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });


    }

    public String checkStringLength(String fullString) {
        if (fullString.length() >= 30) {
            return fullString.substring(0, 30) + "...";
        } else {
            return fullString;
        }
    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        Song currSong = songsList.get(position);
        return currSong.getTitle().substring(0, 1);
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView songThumbnail;
        private TextView songName, songArtist;
        private ImageView moreInfo;

        public SongViewHolder(View itemView) {
            super(itemView);
            songThumbnail = (CircleImageView) itemView.findViewById(R.id.song_image);
            songName = (TextView) itemView.findViewById(R.id.song_name);
            songArtist = (TextView) itemView.findViewById(R.id.song_artist);
            moreInfo = (ImageView) itemView.findViewById(R.id.more_info);
        }
    }
}
