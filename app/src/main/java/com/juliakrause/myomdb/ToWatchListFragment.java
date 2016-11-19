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
import java.util.List;

import com.juliakrause.greendao.generated.*;
import com.juliakrause.greendao.generated.Movie;

import de.greenrobot.dao.query.LazyList;

/**
 * Created by Julia on 16.11.2016.
 */

public class ToWatchListFragment extends ListFragment {

    private ToWatchArrayAdapter listAdapter;

    private DaoSession daoSession;

    List<Movie> moviesToWatch;

    private ToWatchListFragmentBroadcastReceiver broadcastReceiver;

    public ToWatchListFragment() {
        broadcastReceiver = new ToWatchListFragmentBroadcastReceiver(this);
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_towatch_list, container, false);
        Button deleteButton = (Button) (view.findViewById(R.id.deleteFromWatchList));
        listAdapter = new ToWatchArrayAdapter(getContext(), moviesToWatch);
        setListAdapter(listAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ToWatchListFragmentBroadcastReceiver.ACTION_SHOW_TO_WATCH_LIST);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver);
        System.out.println("in OnPause() of ToWatchListFragment");
    }

    public void updateMovies() {
        MovieDao movieDao = daoSession.getMovieDao();
        com.juliakrause.greendao.generated.Movie movie = new com.juliakrause.greendao.generated.Movie();
        movie.setImdbId("888");
        movie.setTitle("Muss man sehen");
        movie.setType("Serie");
        movie.setYear("2000");
        movie.setToWatch(1);
        movie.setFavorite(0);
        movieDao.insert(movie);
        com.juliakrause.greendao.generated.Movie movie2 = new com.juliakrause.greendao.generated.Movie();
        movie2.setImdbId("999");
        movie2.setTitle("Muss man auch sehen");
        movie2.setType("Film");
        movie2.setYear("2001");
        movie2.setToWatch(1);
        movie2.setFavorite(0);
        movieDao.insert(movie2);

        List<com.juliakrause.greendao.generated.Movie> moviesToWatch = movieDao.queryBuilder().listLazy();
        this.moviesToWatch = moviesToWatch;
        listAdapter.clear();
        listAdapter.addAll(this.moviesToWatch);
        listAdapter.notifyDataSetChanged();
    }

}
