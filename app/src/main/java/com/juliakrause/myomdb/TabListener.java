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

    //protected static final String FRAGMENT_TAG_WATCHLIST = "com.juliakrause.myomdb.fragment.tag.WATCHLIST";
    private FragmentManager fm;
    private Activity mainActivity;
    private DaoSession daoSession;

    private void sendBroadcastForWatchList() {
        Intent intent = new Intent(MainBroadcastReceiver.ACTION_WATCHLIST);
        /*ArrayList<Movie> moviesToWatch = new ArrayList<>();
        moviesToWatch.add(new Movie("457", "Tolle Serie", "2000", "series"));
        moviesToWatch.add(new Movie("999", "Must see Movie", "1998", "movie"));
        moviesToWatch.add(new Movie("888", "This is a game", "2007", "game"));
        intent.putParcelableArrayListExtra(MainBroadcastReceiver.EXTRA_MOVIES_WATCHLIST, moviesToWatch);*/
        LocalBroadcastManager.getInstance(mainActivity.getApplicationContext()).sendBroadcast(intent);
    }

    private void sendBroadcastForFavorites() {
        Intent intent = new Intent(MainBroadcastReceiver.ACTION_FAVORITES);
        /*ArrayList<Movie> favorites = new ArrayList<>();
        favorites.add(new Movie("1000", "Serie XY", "1988", "series"));
        favorites.add(new Movie("12", "Best Movie Ever!!!", "2005", "movie"));
        favorites.add(new Movie("66", "This is a not game oh but it is", "2015", "game"));
        intent.putParcelableArrayListExtra(MainBroadcastReceiver.EXTRA_MOVIES_FAVORITES, favorites);*/
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
        //System.out.println("tab " + tabText + " was selected or reselected: " + tab);
        if (tabText.equals("MOVIES")) {
            //fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            android.app.Fragment movieList = fm.findFragmentByTag(FRAGMENT_TAG_LIST);
            if (movieList == null) {
                movieList = new MovieListFragment();
            }
            if (!movieList.isAdded()) {
                fragmentTransaction.replace(R.id.fragment_container, movieList, FRAGMENT_TAG_LIST);
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        } else if (tabText.equals("WATCH LIST")) {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            //android.app.Fragment toWatchList = fm.findFragmentById(R.id.watchlist);
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
            //android.app.Fragment favorites = fm.findFragmentById(R.id.favorites);
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
