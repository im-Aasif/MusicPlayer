package com.nerdcheck.musicplayer.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nerdcheck.musicplayer.R;
import com.nerdcheck.musicplayer.adapter.ViewPagerAdapter;
import com.nerdcheck.musicplayer.fragment.AlbumsFragment;
import com.nerdcheck.musicplayer.fragment.ArtistsFragment;
import com.nerdcheck.musicplayer.fragment.SongsFragment;
import com.nerdcheck.musicplayer.service.MusicService;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MusicService musicService;
    private Intent playIntent;
    private SongsFragment songsFragment;
    private SlidingUpPanelLayout panelLayout;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_with_slidinguppanel);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager();

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        changeTabTextStyle(tabLayout, viewPager);

        panelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
//        panelLayout.setEnabled(false); //Disable sliding up panel layout
        linearLayout = (LinearLayout) findViewById(R.id.dragPanel);
        panelListener();
    }

    private void panelListener() {
        panelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.e("Panel Slide Offset", " " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.e("Previous State", "" + previousState);
                Log.e("New State", "" + newState);
                if (panelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    linearLayout.setVisibility(View.GONE);
                    panelLayout.setDragView(R.id.iv_close);
                }


                if (panelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    linearLayout.setVisibility(View.VISIBLE);
                    panelLayout.setDragView(R.id.dragView);
                }

            }
        });

    }

    private void changeTabTextStyle(TabLayout tabLayout, final ViewPager viewPager) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                TextView tabTextView = new TextView(this);
                tab.setCustomView(tabTextView);
                tabTextView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.setText(tab.getText());
                tabTextView.setTextColor(getResources().getColor(android.R.color.white));
                // First tab is the selected tab, so if i==0 then set BOLD typeface
                if (i == 0) {
                    tabTextView.setTypeface(null, Typeface.BOLD);
                }
            }
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                TextView text = (TextView) tab.getCustomView();
                text.setTypeface(null, Typeface.BOLD);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView text = (TextView) tab.getCustomView();
                text.setTypeface(null, Typeface.NORMAL);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setupViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new SongsFragment(), "SONGS");
        viewPagerAdapter.addFragments(new AlbumsFragment(), "ALBUMS");
        viewPagerAdapter.addFragments(new ArtistsFragment(), "ARTISTS");
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    public void onBackPressed() {
        if (panelLayout != null && panelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED
                || panelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED) {
            panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }
}
