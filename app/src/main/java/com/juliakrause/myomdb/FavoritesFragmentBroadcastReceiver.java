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

    FavoritesFragment favoritesFragment;

    public FavoritesFragmentBroadcastReceiver(FavoritesFragment favoritesFragment) {
        this.favoritesFragment = favoritesFragment;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SHOW_FAVORITES.equals(action)) {
                favoritesFragment.updateMovies();
            }
        }
    }
}
