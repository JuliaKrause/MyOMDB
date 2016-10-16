package com.juliakrause.myomdb;

import android.app.DownloadManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.juliakrause.myomdb.dummy.DummyContent;

import static com.juliakrause.myomdb.R.id.searchView;

//http://www.omdbapi.com/

public class MainActivity extends AppCompatActivity
        implements ListFragment.OnFragmentInteractionListener,
        InfoFragment.OnFragmentInteractionListener,
        ItemFragment.OnListFragmentInteractionListener {

    private MyBroadcastReceiver receiver = new MyBroadcastReceiver();
    private SearchView searchView;
    private MySearchQueryListener searchListener;

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
        searchListener = new MySearchQueryListener();
        searchView.setOnQueryTextListener(searchListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        //die folgenden Zeilen (startService) sollen laufen, wenn man eine Suche startet, sonst nicht
        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra("query", "/?s=\"Stranger Things\"");
        startService(intent);
    }

    //in onPause(), persist data and unbind services, unregister broadcast receiver
    @Override public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);

        //TextView tv = (TextView) findViewById(R.id.textView2);

        //tv.setText("fragment says: activity has been paused");
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
}
