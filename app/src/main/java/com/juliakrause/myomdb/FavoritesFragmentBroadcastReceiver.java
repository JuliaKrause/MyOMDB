package com.juliakrause.myomdb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by Julia on 16.11.2016.
 */

public class FavoritesFragmentBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION_SHOW_FAVORITES = "com.juliakrause.myomdb.ACTION.SHOW_FAVORITES";
    public static final String EXTRA_FAVORITES = "com.juliakrause.myomdb.extra.FAVORITES";

    FavoritesFragment favoritesFragment;

    public FavoritesFragmentBroadcastReceiver() {}

    public FavoritesFragmentBroadcastReceiver(FavoritesFragment favoritesFragment) {
        this.favoritesFragment = favoritesFragment;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("IN THE FAVORITESFRAGMENTBROADCASTRECEIVER'S RECEIVE METHOD");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SHOW_FAVORITES.equals(action)) {
                System.out.println("Here we are");
                ArrayList<Movie> favoriteMovies = intent.getParcelableArrayListExtra(EXTRA_FAVORITES);
                favoritesFragment.updateMovies(favoriteMovies);
            }
        }
    }

}
