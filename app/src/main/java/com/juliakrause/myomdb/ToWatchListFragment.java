package com.juliakrause.myomdb;

import android.app.ListFragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.juliakrause.greendao.generated.*;
import com.juliakrause.greendao.generated.Movie;

/**
 * Created by Julia on 16.11.2016.
 */

public class ToWatchListFragment extends ListFragment {

    private ToWatchArrayAdapter listAdapter;

    private DaoSession daoSession;

    List<Movie> moviesToWatch = new ArrayList<>();

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
        listAdapter = new ToWatchArrayAdapter(getContext(), moviesToWatch, this);
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
        System.out.println("in OnPause() of ToWatchListFragment");
    }

    public void updateMovies() {
        MovieDao movieDao = daoSession.getMovieDao();
        List moviesToWatch = movieDao.queryBuilder().where(MovieDao.Properties.ToWatch.eq("1")).list();
        //List<com.juliakrause.greendao.generated.Movie> moviesToWatch = (ArrayList) movieDao.queryBuilder().listLazy();
        this.moviesToWatch = moviesToWatch;
        listAdapter.clear();
        listAdapter.addAll(this.moviesToWatch);
        listAdapter.setDaoSession(this.daoSession);
        listAdapter.notifyDataSetChanged();
    }

}
