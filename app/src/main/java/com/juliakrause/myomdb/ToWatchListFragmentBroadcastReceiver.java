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

    ToWatchListFragment toWatchListFragment;

    public ToWatchListFragmentBroadcastReceiver(ToWatchListFragment toWatchListFragment) {
        this.toWatchListFragment = toWatchListFragment;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SHOW_TO_WATCH_LIST.equals(action)) {
                toWatchListFragment.updateMovies();
            }
        }
    }

}
