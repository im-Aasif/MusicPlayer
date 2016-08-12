package com.nerdcheck.musicplayer.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nerdcheck.musicplayer.R;
import com.nerdcheck.musicplayer.adapter.ViewPagerAdapter;
import com.nerdcheck.musicplayer.fragment.AlbumsFragment;
import com.nerdcheck.musicplayer.fragment.ArtistsFragment;
import com.nerdcheck.musicplayer.fragment.SongsFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_with_nav_view);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        setupViewPager();

        tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);



    }

    private void setupViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new SongsFragment(),"Songs");
        viewPagerAdapter.addFragments(new AlbumsFragment(),"Albums");
        viewPagerAdapter.addFragments(new ArtistsFragment(),"Artists");
        viewPager.setAdapter(viewPagerAdapter);
    }
}
