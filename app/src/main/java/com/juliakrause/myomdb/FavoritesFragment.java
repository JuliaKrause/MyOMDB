package com.juliakrause.myomdb;

import android.app.ListFragment;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Julia on 16.11.2016.
 */

public class FavoritesFragment extends ListFragment {

    private FavoritesArrayAdapter listAdapter;

    ArrayList<Movie> favoriteMovies = new ArrayList<>();

    private FavoritesFragmentBroadcastReceiver broadcastReceiver;

    public FavoritesFragment() {
        broadcastReceiver = new FavoritesFragmentBroadcastReceiver(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        Button deleteButton = (Button) (view.findViewById(R.id.deleteFromFavorites));
        listAdapter = new FavoritesArrayAdapter(getContext(), favoriteMovies);
        setListAdapter(listAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(FavoritesFragmentBroadcastReceiver.ACTION_SHOW_FAVORITES);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver);
        System.out.println("in OnPause() of FavoritesFragment");
    }

    public void updateMovies(ArrayList<Movie> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
        listAdapter.clear();
        listAdapter.addAll(this.favoriteMovies);
        listAdapter.notifyDataSetChanged();
    }

}
