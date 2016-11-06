package com.juliakrause.myomdb;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import android.net.Uri;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.juliakrause.myomdb.dummy.DummyContent;

//TODO: get result of search to main activity
//TODO: get result of search from main activity in list fragment
//TODO: think about what you just did and do it better (a handler and/or a broadcast receiver
//TODO: and/or a local broadcast manager should play a role in this)
//TODO: start detail search from event listener in list
//TODO: display result of detail search in detail fragment
//TODO: make github repo
//TODO: do something to test that different tab shows different list
//TODO: e.g. favorites tab could show dummy list or such

//TODO: now the time for local data has come
//TODO: do not freak out because after a month of this, you're still only done with part 1
//TODO: find out where these lines come from:
import static android.content.Intent.ACTION_SEARCH;
import static com.juliakrause.myomdb.R.id.searchView;

public class MainActivity extends AppCompatActivity
        implements ListFragment.OnFragmentInteractionListener,
        ItemFragment.OnListFragmentInteractionListener,
        SearchView.OnQueryTextListener {

    private static final String ACTION_SEARCHOMDB = "com.juliakrause.myomdb.action.SEARCHOMDB";
    private static final String ACTION_GET_DETAILS = "com.juliakrause.myomdb.action.GET_DETAILS";
    private static final String FRAGMENT_TAG_LIST = "com.juliakrause.myomdb.fragment.tag.LIST";
    private static final String FRAGMENT_TAG_DETAILS = "com.juliakrause.myomdb.fragment.tag.DETAILS";
    private MyBroadcastReceiver receiver;
    private SearchView searchView;
    private Messenger detailsMessenger;
    private Messenger searchMessenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("THREAD IS: ");
        System.out.println(Thread.currentThread().getId());

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addOnTabSelectedListener(new TabListener());

        String tab1 = getResources().getString(R.string.tab1);
        String tab2 = getResources().getString(R.string.tab2);
        String tab3 = getResources().getString(R.string.tab3);

        tabLayout.addTab(tabLayout.newTab().setText(tab1));
        tabLayout.addTab(tabLayout.newTab().setText(tab2));
        tabLayout.addTab(tabLayout.newTab().setText(tab3));

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = ItemFragment.newInstance(1);
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
        //detailsMessenger = new Messenger(new DetailsHandler());
        //searchMessenger = new Messenger(new SearchHandler());
        receiver = new MyBroadcastReceiver(this);
    }

    //in onResume(), bind activity to services, manipulate fragments, register broadcast receiver
    @Override public void onResume() {
        super.onResume();

        //not sure why I would create an IntentFilter here and not in the manifest file
        IntentFilter filter = new IntentFilter(MyBroadcastReceiver.ACTION_LOAD_DETAILS);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        System.out.println("Query is: " + query);
        Intent intent = new Intent(this, MyIntentService.class);
        intent.setAction(ACTION_SEARCHOMDB);
        //intent.setAction(ACTION_GET_DETAILS);
        intent.putExtra("query", query);
        //intent.putExtra("query", "tt2661044");
        startService(intent);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    //TODO: ich glaube, ich will mir die Handler lieber direkt im Intent Service machen
    /*private class DetailsHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Movie movie = msg.getData().getParcelable(DownloadService.MESSAGE_DETAILS);
            showDetails(movie);
        }
    }

    private class SearchHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            ArrayList<Movie> movies = msg.getData().getParcelableArrayList(DownloadService.MESSAGE_SEARCH_RESULT);
            Intent intent = new Intent(MovieListFragmentBroadcastReceiver.ACTION_SHOW_SEARCH_RESULT);
            intent.putParcelableArrayListExtra(MovieListFragmentBroadcastReceiver.EXTRA_SEARCH_RESULT, movies);
            LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
        }
    }

    public void loadDetails(String imdbID) {
        DownloadService.startActionLoadDetails(this, imdbID, detailsMessenger);
    }

    public void showDetails(Movie movie) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetailsFragment details = DetailsFragment.newInstance(movie);
        fragmentTransaction.replace(R.id.fragmentContainer, details, FRAGMENT_TAG_DETAILS);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void onSearchClick(View view) {
        showList();
        String title = ((EditText) findViewById(R.id.editTextSearch)).getText().toString();
        DownloadService.startActionSearch(this, title, searchMessenger);
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }*/
}
