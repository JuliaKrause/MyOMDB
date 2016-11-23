package com.juliakrause.myomdb;

import android.database.sqlite.SQLiteDatabase;
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
import com.juliakrause.greendao.generated.*;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String DATABASE_NAME = "greendao-demo";

    // database connection / session related
    private SQLiteDatabase databaseConnection;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    // data access objects for our entities
    private MovieDao movieDao;


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

        databaseConnection = new DaoMaster.DevOpenHelper(this, DATABASE_NAME, null).getWritableDatabase();
        daoMaster = new DaoMaster(databaseConnection);
        daoSession = daoMaster.newSession();
        movieDao = daoSession.getMovieDao();
        //movieDao.deleteAll();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getFragmentManager();

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addOnTabSelectedListener(new TabListener(this, fragmentManager, daoSession));

        String movies = getResources().getString(R.string.tab1);
        String watchList = getResources().getString(R.string.tab2);
        String favorites = getResources().getString(R.string.tab3);

        tabLayout.addTab(tabLayout.newTab().setText(movies));
        tabLayout.addTab(tabLayout.newTab().setText(watchList));
        tabLayout.addTab(tabLayout.newTab().setText(favorites));

        receiver = new MainBroadcastReceiver(this);
    }

    @Override public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(MainBroadcastReceiver.ACTION_GET_DETAILS);
        filter.addAction(MainBroadcastReceiver.ACTION_LOAD_DETAILS);
        filter.addAction(MainBroadcastReceiver.ACTION_WATCHLIST);
        filter.addAction(MainBroadcastReceiver.ACTION_FAVORITES);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

    }

    @Override public void onPause() {
        super.onPause();

        //clean up local db
        QueryBuilder<com.juliakrause.greendao.generated.Movie> builder = movieDao.queryBuilder();
        WhereCondition condition1 = builder.or(MovieDao.Properties.Favorite.eq(null),
                MovieDao.Properties.Favorite.eq("0"));
        WhereCondition condition2 = builder.or(MovieDao.Properties.ToWatch.eq(null),
                MovieDao.Properties.ToWatch.eq("0"));
        builder.where(condition1, condition2);
        DeleteQuery<com.juliakrause.greendao.generated.Movie> deleteQuery = builder.buildDelete();
        deleteQuery.executeDeleteWithoutDetachingEntities();

        if (databaseConnection != null && databaseConnection.isOpen()) {
            databaseConnection.close();
        }

        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    public void getDetails(String imdbID) {
        Intent intent = new Intent(this, MyIntentService.class);
        intent.setAction(ACTION_GET_DETAILS);
        intent.putExtra(EXTRA_IMDBID, imdbID);
        startService(intent);
    }

    public void prepareDetails(Movie movie) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetailsFragment details = DetailsFragment.newInstance(movie);
        details.setDaoSession(daoSession);
        fragmentTransaction.replace(R.id.fragment_container, details, FRAGMENT_TAG_DETAILS);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        tabLayout.getTabAt(0).select();
        Intent intent = new Intent(this, MyIntentService.class);
        intent.setAction(ACTION_SEARCHOMDB);
        intent.putExtra(EXTRA_TITLE, query);
        startService(intent);
        return true;
    }

    public void prepareWatchList() {
        Intent intent = new Intent(ToWatchListFragmentBroadcastReceiver.ACTION_SHOW_TO_WATCH_LIST);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void prepareFavoritesList() {
        Intent intent = new Intent(FavoritesFragmentBroadcastReceiver.ACTION_SHOW_FAVORITES);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}
