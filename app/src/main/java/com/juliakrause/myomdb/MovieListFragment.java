package com.juliakrause.myomdb;

/**
 * Created by Julia on 13.11.2016.
 */
import android.app.ListFragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;

public class MovieListFragment extends ListFragment {

    private MovieArrayAdapter listAdapter;

    ArrayList<Movie> movies = new ArrayList<>();

    private MovieListFragmentBroadcastReceiver broadcastReceiver;

    public MovieListFragment() {
        broadcastReceiver = new MovieListFragmentBroadcastReceiver(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        listAdapter = new MovieArrayAdapter(getContext(), movies);
        setListAdapter(listAdapter);
        return view;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Movie movie = (Movie) getListAdapter().getItem(position);
        Intent intent = new Intent(MainBroadcastReceiver.ACTION_GET_DETAILS);
        intent.putExtra(MainBroadcastReceiver.EXTRA_IMDBID, movie.getImdbID());
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, new IntentFilter(MovieListFragmentBroadcastReceiver.ACTION_SHOW_SEARCH_RESULT));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver);
    }

    public void updateMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        listAdapter.clear();
        listAdapter.addAll(this.movies);
        listAdapter.notifyDataSetChanged();
    }

}
