package com.juliakrause.myomdb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by Julia on 13.11.2016.
 */

public class MovieListFragmentBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION_SHOW_SEARCH_RESULT = "com.juliakrause.myomdb.action.SHOW_SEARCH_RESULT";
    public static final String EXTRA_SEARCH_RESULT = "com.juliakrause.myomdb.extra.SEARCH_RESULT";

    MovieListFragment movieListFragment;

    public MovieListFragmentBroadcastReceiver(MovieListFragment movieListFragment) {
        this.movieListFragment = movieListFragment;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SHOW_SEARCH_RESULT.equals(action)) {
                ArrayList<Movie> movies = intent.getParcelableArrayListExtra(EXTRA_SEARCH_RESULT);
                movieListFragment.updateMovies(movies);
            }
        }
    }
}
