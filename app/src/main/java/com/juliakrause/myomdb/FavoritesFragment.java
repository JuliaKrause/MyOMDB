package com.juliakrause.myomdb;

import android.app.ListFragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.juliakrause.greendao.generated.*;
import com.juliakrause.greendao.generated.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julia on 16.11.2016.
 */

public class FavoritesFragment extends ListFragment {

    private FavoritesArrayAdapter listAdapter;

    private DaoSession daoSession;

    List<Movie> favoriteMovies = new ArrayList<>();

    private FavoritesFragmentBroadcastReceiver broadcastReceiver;

    public FavoritesFragment() {
        broadcastReceiver = new FavoritesFragmentBroadcastReceiver(this);
    }

    /*public DaoSession getDaoSession() {
        return daoSession;
    }*/

    public void setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        listAdapter = new FavoritesArrayAdapter(getContext(), favoriteMovies, this);
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
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Movie movie = (Movie) getListAdapter().getItem(position);
        Intent intent = new Intent(MainBroadcastReceiver.ACTION_GET_DETAILS);
        intent.putExtra(MainBroadcastReceiver.EXTRA_IMDBID, movie.getImdbId());
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver);
    }

    public void updateMovies() {
        MovieDao movieDao = daoSession.getMovieDao();
        List favorites = movieDao.queryBuilder().where(MovieDao.Properties.Favorite.eq("1")).list();
        this.favoriteMovies = favorites;
        listAdapter.clear();
        listAdapter.addAll(this.favoriteMovies);
        listAdapter.setDaoSession(this.daoSession);
        listAdapter.notifyDataSetChanged();
    }

}
