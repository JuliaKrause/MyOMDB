package com.juliakrause.myomdb;

import android.app.ListFragment;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.juliakrause.greendao.generated.*;
import com.juliakrause.greendao.generated.Movie;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

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

    public DaoSession getDaoSession() {
        return daoSession;
    }

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
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver);
        System.out.println("in OnPause() of FavoritesFragment");
    }

    public void updateMovies() {
        MovieDao movieDao = daoSession.getMovieDao();
        //movieDao.deleteAll();
        List favorites = movieDao.queryBuilder().where(MovieDao.Properties.Favorite.eq("1")).list();
        //List<com.juliakrause.greendao.generated.Movie> moviesToWatch = (ArrayList) movieDao.queryBuilder().listLazy();
        this.favoriteMovies = favorites;
        listAdapter.clear();
        listAdapter.addAll(this.favoriteMovies);
        listAdapter.setDaoSession(this.daoSession);
        listAdapter.notifyDataSetChanged();
    }

}
