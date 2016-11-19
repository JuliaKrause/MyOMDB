package com.juliakrause.myomdb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by Julia on 16.11.2016.
 */

public class ToWatchListFragmentBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION_SHOW_TO_WATCH_LIST = "com.juliakrause.myomdb.ACTION.SHOW_TO_WATCH_LIST";
    //public static final String EXTRA_MOVIES_TO_WATCH = "com.juliakrause.myomdb.extra.MOVIES_TO_WATCH";

    ToWatchListFragment toWatchListFragment;

    public ToWatchListFragmentBroadcastReceiver(ToWatchListFragment toWatchListFragment) {
        this.toWatchListFragment = toWatchListFragment;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("IN THE TOWATCHLISTFRAGMENTBROADCASTRECEIVER'S RECEIVE METHOD");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SHOW_TO_WATCH_LIST.equals(action)) {
                System.out.println("Here we are");
                //ArrayList<Movie> moviesToWatch = intent.getParcelableArrayListExtra(EXTRA_MOVIES_TO_WATCH);
                toWatchListFragment.updateMovies();
            }
        }
    }

}
