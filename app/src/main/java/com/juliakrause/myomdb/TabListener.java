package com.juliakrause.myomdb;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;

import com.juliakrause.greendao.generated.DaoSession;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static com.juliakrause.myomdb.MainActivity.FRAGMENT_TAG_LIST;

/**
 * Created by Julia on 07.10.2016.
 */


public class TabListener implements TabLayout.OnTabSelectedListener {

    private FragmentManager fm;
    private Activity mainActivity;
    private DaoSession daoSession;

    private void sendBroadcastForWatchList() {
        Intent intent = new Intent(MainBroadcastReceiver.ACTION_WATCHLIST);
        LocalBroadcastManager.getInstance(mainActivity.getApplicationContext()).sendBroadcast(intent);
    }

    private void sendBroadcastForFavorites() {
        Intent intent = new Intent(MainBroadcastReceiver.ACTION_FAVORITES);
        LocalBroadcastManager.getInstance(mainActivity.getApplicationContext()).sendBroadcast(intent);
    }



    public TabListener(Activity mainActivity, FragmentManager fm, DaoSession ds) {
        super();
        this.fm = fm;
        this.mainActivity = mainActivity;
        this.daoSession = ds;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        String tabText = String.valueOf(tab.getText());
        if (tabText.equals("MOVIES")) {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            android.app.Fragment movieList = fm.findFragmentByTag(FRAGMENT_TAG_LIST);
            if (movieList == null) {
                movieList = new MovieListFragment();
            }
            if (!movieList.isAdded()) {
                fragmentTransaction.replace(R.id.fragment_container, movieList, FRAGMENT_TAG_LIST);
                fragmentTransaction.commit();
            }
        } else if (tabText.equals("WATCH LIST")) {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            ToWatchListFragment toWatchList = (ToWatchListFragment) fm.findFragmentById(R.id.watchlist);
            if (toWatchList == null) {
                toWatchList = new ToWatchListFragment();
                toWatchList.setDaoSession(daoSession);
            }
            if (!toWatchList.isAdded()) {
                fragmentTransaction.replace(R.id.fragment_container, toWatchList);
                fragmentTransaction.commit();
            }
            sendBroadcastForWatchList();
        } else if (tabText.equals("FAVORITES")) {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            FavoritesFragment favorites = (FavoritesFragment) fm.findFragmentById(R.id.favorites);
            if (favorites == null) {
                favorites = new FavoritesFragment();
                favorites.setDaoSession(daoSession);
            }
            if (!favorites.isAdded()) {
                fragmentTransaction.replace(R.id.fragment_container, favorites);
                fragmentTransaction.commit();
            }
            sendBroadcastForFavorites();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        String tabText = String.valueOf(tab.getText());
        System.out.println("tab " + tabText + " was unselected: " + tab);

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        onTabSelected(tab);
    }
}
