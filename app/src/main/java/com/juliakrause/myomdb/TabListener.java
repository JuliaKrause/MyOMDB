package com.juliakrause.myomdb;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static com.juliakrause.myomdb.MainActivity.FRAGMENT_TAG_LIST;

/**
 * Created by Julia on 07.10.2016.
 */

public class TabListener implements TabLayout.OnTabSelectedListener {

    protected static final String FRAGMENT_TAG_WATCHLIST = "com.juliakrause.myomdb.fragment.tag.WATCHLIST";
    private FragmentManager fm;
    private Activity mainActivity;

    private void sendBroadcastForWatchList() {
        Intent intent = new Intent(ToWatchListFragmentBroadcastReceiver.ACTION_SHOW_TO_WATCH_LIST);
        ArrayList<Movie> moviesToWatch = new ArrayList<>();
        intent.putParcelableArrayListExtra(ToWatchListFragmentBroadcastReceiver.EXTRA_MOVIES_TO_WATCH, moviesToWatch);
        LocalBroadcastManager.getInstance(mainActivity.getApplicationContext()).sendBroadcast(intent);
       }

    private void sendBroadcastForFavorites() {
        Intent intent = new Intent(FavoritesFragmentBroadcastReceiver.ACTION_SHOW_FAVORITES);
        ArrayList<Movie> favorites = new ArrayList<>();
        intent.putParcelableArrayListExtra(FavoritesFragmentBroadcastReceiver.EXTRA_FAVORITES, favorites);
        LocalBroadcastManager.getInstance(mainActivity.getApplicationContext()).sendBroadcast(intent);
    }

    private void addWatchListFragment() {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        android.app.Fragment toWatchList = fm.findFragmentById(R.id.watchlist);
        if (toWatchList == null) {
            toWatchList = new ToWatchListFragment();
        }
        if (!toWatchList.isAdded()) {
            fragmentTransaction.add(R.id.fragment_container, toWatchList);
        }

        fragmentTransaction.commit();
    }

    private void addFavoritesFragment() {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        android.app.Fragment favorites = fm.findFragmentById(R.id.favorites);
        if (favorites == null) {
            favorites = new FavoritesFragment();
        }
        if (!favorites.isAdded()) {
            fragmentTransaction.add(R.id.fragment_container, favorites);
        }

        fragmentTransaction.commit();
    }


    public TabListener(Activity mainActivity, FragmentManager fm) {
        super();
        this.fm = fm;
        this.mainActivity = mainActivity;
        addWatchListFragment();
        addFavoritesFragment();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        String tabText = String.valueOf(tab.getText());
        System.out.println("tab " + tabText + " was selected: " + tab);
        if (tabText.equals("MOVIES")) {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            android.app.Fragment movieList = fm.findFragmentByTag(FRAGMENT_TAG_LIST);
            if (movieList == null) {
                movieList = new MovieListFragment();
            }
            if (!movieList.isAdded()) {
                fragmentTransaction.replace(R.id.fragment_container, movieList, FRAGMENT_TAG_LIST);
                //fragmentTransaction.commit();
            }
        } else if (tabText.equals("WATCH LIST")) {
            System.out.println("we want a watch list");
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            android.app.Fragment toWatchList = fm.findFragmentById(R.id.watchlist);
            if (toWatchList == null) {
                System.out.println("is it still null? yes");
                toWatchList = new ToWatchListFragment();
            }
            if (!toWatchList.isAdded()) {
                //fragmentTransaction.add(R.id.fragment_container, toWatchList);
                fragmentTransaction.replace(R.id.fragment_container, toWatchList);
                fragmentTransaction.commit();
            }
            sendBroadcastForWatchList();
        } else if (tabText.equals("FAVORITES")) {
            System.out.println("we want a favorites list");
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            android.app.Fragment favorites = fm.findFragmentById(R.id.favorites);
            if (favorites == null) {
                System.out.println("is it still null? yes");
                favorites = new FavoritesFragment();
            }
            if (!favorites.isAdded()) {
                //fragmentTransaction.add(R.id.fragment_container, toWatchList);
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
        String tabText = String.valueOf(tab.getText());
        System.out.println("tab " + tabText + " was reselected: " + tab);
        if (tabText.equals("MOVIES")) {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            android.app.Fragment movieList = fm.findFragmentByTag(FRAGMENT_TAG_LIST);
            if (movieList == null) {
                movieList = new MovieListFragment();
            }
            if (!movieList.isAdded()) {
                fragmentTransaction.replace(R.id.fragment_container, movieList, FRAGMENT_TAG_LIST);
                //fragmentTransaction.commit();
            }

        }
    }
}
