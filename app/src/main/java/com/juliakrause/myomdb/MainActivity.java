package com.juliakrause.myomdb;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.widget.TextView;

import java.util.ArrayList;

//TODO: I do not have a backstack for the fragments
//TODO: greenDAO

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String ACTION_SEARCHOMDB = "com.juliakrause.myomdb.action.SEARCHOMDB";
    private static final String ACTION_GET_DETAILS = "com.juliakrause.myomdb.action.GET_DETAILS";
    private static final String EXTRA_TITLE = "com.juliakrause.myomdb.extra.TITLE";
    private static final String EXTRA_IMDBID = "com.juliakrause.myomdb.extra.IMDBID";
    protected static final String FRAGMENT_TAG_LIST = "com.juliakrause.myomdb.fragment.tag.LIST";

    protected static final String FRAGMENT_TAG_DETAILS = "com.juliakrause.myomdb.fragment.tag.DETAILS";

    private MainBroadcastReceiver receiver;
    private SearchView searchView;
    private FragmentManager fragmentManager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("THREAD IS: ");
        System.out.println(Thread.currentThread().getId());

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getFragmentManager();

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addOnTabSelectedListener(new TabListener(this, fragmentManager));

        String movies = getResources().getString(R.string.tab1);
        String watchList = getResources().getString(R.string.tab2);
        String favorites = getResources().getString(R.string.tab3);

        tabLayout.addTab(tabLayout.newTab().setText(movies));
        tabLayout.addTab(tabLayout.newTab().setText(watchList));
        tabLayout.addTab(tabLayout.newTab().setText(favorites));

        receiver = new MainBroadcastReceiver(this);
    }

    //in onResume(), bind activity to services, manipulate fragments, register broadcast receiver
    @Override public void onResume() {
        super.onResume();

        TextView myText = (TextView) findViewById(R.id.tvTitle);
        IntentFilter filter = new IntentFilter();
        filter.addAction(MainBroadcastReceiver.ACTION_GET_DETAILS);
        filter.addAction(MainBroadcastReceiver.ACTION_LOAD_DETAILS);
        filter.addAction(MainBroadcastReceiver.ACTION_WATCHLIST);
        filter.addAction(MainBroadcastReceiver.ACTION_FAVORITES);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

    }

    //in onPause(), persist data and unbind services, unregister broadcast receiver
    @Override public void onPause() {
        super.onPause();
        //to persist data, I guess I need some variables at the beginning, which I instantiate in onCreate()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        System.out.println("THREAD IS: ");
        System.out.println(Thread.currentThread().getId());

    }

    public void prepareList() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment movieList = fragmentManager.findFragmentByTag(FRAGMENT_TAG_LIST);
        if (movieList == null) {
            movieList = new MovieListFragment();
        }
        if (!movieList.isAdded()) {
            fragmentTransaction.replace(R.id.fragment_container, movieList, FRAGMENT_TAG_LIST);
            fragmentTransaction.commit();
        }

    }

    public void getDetails(String imdbID) {
        System.out.println("Movie ID is: " + imdbID);
        Intent intent = new Intent(this, MyIntentService.class);
        intent.setAction(ACTION_GET_DETAILS);
        intent.putExtra(EXTRA_IMDBID, imdbID);
        startService(intent);
    }

    public void prepareDetails(Movie movie) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetailsFragment details = DetailsFragment.newInstance(movie);
        fragmentTransaction.replace(R.id.fragment_container, details, FRAGMENT_TAG_DETAILS);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        tabLayout.getTabAt(0).select();
        prepareList();
        System.out.println("Query is: " + query);
        Intent intent = new Intent(this, MyIntentService.class);
        intent.setAction(ACTION_SEARCHOMDB);
        intent.putExtra(EXTRA_TITLE, query);
        startService(intent);
        return true;
    }

    public void prepareWatchList(ArrayList<Movie> moviesToWatch) {
        Intent intent = new Intent(ToWatchListFragmentBroadcastReceiver.ACTION_SHOW_TO_WATCH_LIST);
        intent.putParcelableArrayListExtra(ToWatchListFragmentBroadcastReceiver.EXTRA_MOVIES_TO_WATCH, moviesToWatch);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void prepareFavoritesList(ArrayList<Movie> favoriteMovies) {
        Intent intent = new Intent(FavoritesFragmentBroadcastReceiver.ACTION_SHOW_FAVORITES);
        intent.putParcelableArrayListExtra(FavoritesFragmentBroadcastReceiver.EXTRA_FAVORITES, favoriteMovies);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}
