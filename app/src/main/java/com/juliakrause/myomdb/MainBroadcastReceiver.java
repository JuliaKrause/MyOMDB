package com.juliakrause.myomdb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by Julia on 08.10.2016.
 */
public class MainBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION_GET_DETAILS = "com.juliakrause.myomdb.MainBroadcastReceiver.ACTION.GET_DETAILS";
    public static final String EXTRA_IMDBID = "com.juliakrause.myomdb.MainBroadcastReceiver.extra.IMDBID";
	public static final String ACTION_LOAD_DETAILS = "com.juliakrause.myomdb.MainBroadcastReceiver.ACTION.LOAD_DETAILS";
	public static final String EXTRA_MOVIE = "com.juliakrause.myomdb.MainBroadcastReceiver.extra.MOVIE";
	public static final String ACTION_WATCHLIST = "com.juliakrause.myomdb.MainBroadcastReceiver.ACTION.WATCHLIST";
	public static final String EXTRA_MOVIES_WATCHLIST = "com.juliakrause.myomdb.MainBroadcastReceiver.extra.MOVIES_WATCHLIST";
	public static final String ACTION_FAVORITES = "com.juliakrause.myomdb.MainBroadcastReceiver.ACTION.FAVORITES";
	public static final String EXTRA_MOVIES_FAVORITES = "com.juliakrause.myomdb.MainBroadcastReceiver.extra.MOVIES_FAVORITES";
	private MainActivity mainActivity;
    
	
	public MainBroadcastReceiver(MainActivity mainActivity) {
		this.mainActivity = mainActivity;     
	}     

	@Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("in onReceive method");
		if (intent != null) {             
			final String action = intent.getAction();             
			if (ACTION_GET_DETAILS.equals(action)) {
				final String imdbID = intent.getStringExtra(EXTRA_IMDBID);                 
				mainActivity.getDetails(imdbID);
			} else if (ACTION_LOAD_DETAILS.equals(action)) {
				ArrayList<Movie> movies = intent.getParcelableArrayListExtra(EXTRA_MOVIE);
				mainActivity.prepareDetails(movies.get(0));
			} else if (ACTION_WATCHLIST.equals(action)) {
				ArrayList<Movie> movies = intent.getParcelableArrayListExtra(EXTRA_MOVIES_WATCHLIST);
				mainActivity.prepareWatchList(movies);
			} else if (ACTION_FAVORITES.equals(action)) {
				ArrayList<Movie> movies = intent.getParcelableArrayListExtra(EXTRA_MOVIES_FAVORITES);
				mainActivity.prepareFavoritesList(movies);
			}
		}
    }

}