package com.juliakrause.myomdb;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.widget.Toast;

import com.juliakrause.myomdb.dummy.DummyContent;

//TODO: find out how I can know what thread I'm in
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

    private MyBroadcastReceiver receiver = new MyBroadcastReceiver();
    private SearchView searchView;
    private static final String ACTION_SEARCHOMDB = "com.juliakrause.myomdb.action.SEARCHOMDB";
    private static final String ACTION_GET_DETAILS = "com.juliakrause.myomdb.action.GET_DETAILS";

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    }

    //in onResume(), bind activity to services, manipulate fragments, register broadcast receiver
    @Override public void onResume() {
        super.onResume();

        //not sure why I would create an IntentFilter here and not in the manifest file
        //IntentFilter filter = new IntentFilter(MyIntentService.);
        //LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
    }

    //in onPause(), persist data and unbind services, unregister broadcast receiver
    @Override public void onPause() {
        super.onPause();
        //to persist data, I guess I need some variables at the beginning, which I instantiate in onCreate()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);

    }

    // Define the callback for what to do when data is received
    private BroadcastReceiver testReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra("resultCode", RESULT_CANCELED);
            if (resultCode == RESULT_OK) {
                String resultValue = intent.getStringExtra("resultValue");
                Toast.makeText(MainActivity.this, resultValue, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        System.out.println("Query is: " + query);
        Intent intent = new Intent(this, MyIntentService.class);
        //intent.setAction(ACTION_SEARCHOMDB);
        intent.setAction(ACTION_GET_DETAILS);
        intent.putExtra("query", query);
        //intent.putExtra("query", "tt2661044");
        startService(intent);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
