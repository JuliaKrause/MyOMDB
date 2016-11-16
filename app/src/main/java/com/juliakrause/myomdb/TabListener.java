package com.juliakrause.myomdb;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;

import static android.R.attr.id;
import static com.juliakrause.myomdb.MainActivity.FRAGMENT_TAG_LIST;

/**
 * Created by Julia on 07.10.2016.
 */

public class TabListener implements TabLayout.OnTabSelectedListener {

    protected static final String FRAGMENT_TAG_WATCHLIST = "com.juliakrause.myomdb.fragment.tag.WATCHLIST";
    private FragmentManager fm;
    private Activity mainActivity;

    private void sendBroadcastForWatchList() {
        Intent intent = new Intent(ToWatchListFragmentBroadcastReceiver.ACTION_SHOW_TO_WATCH_LIST);
        LocalBroadcastManager.getInstance(mainActivity.getApplicationContext()).sendBroadcast(intent);
        /*okay, I can send a broadcast from here to the MainBroadcastReceiver,
        but not to the ToWatchListFragmentBroadcastReceiver
        but the view has been created and inflated and the ToWatchListFragmentBroadcastReceiver was created
         */
        //Intent intent = new Intent(MainBroadcastReceiver.ACTION_GET_DETAILS);
        //LocalBroadcastManager.getInstance(mainActivity.getApplicationContext()).sendBroadcast(intent);
    }

    public TabListener(Activity mainActivity, FragmentManager fm) {
        super();
        this.fm = fm;
        this.mainActivity = mainActivity;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        String tabText = String.valueOf(tab.getText());
        System.out.println("tab " + tabText + " was selected: " + tab);
        if (tabText.equals("MOVIES")) {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            android.app.Fragment movieList = fm.findFragmentByTag(FRAGMENT_TAG_LIST);
            if (movieList == null) {
                movieList = new MovieListFragment();
            }
            if (!movieList.isAdded()) {
                fragmentTransaction.replace(R.id.fragment_container, movieList, FRAGMENT_TAG_LIST);
                //fragmentTransaction.commit();
            }
        } else if (tabText.equals("WATCH LIST")) {
            System.out.println("so far so good");
            //fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            android.app.Fragment toWatchList = fm.findFragmentById(R.id.watchlist);
            if (toWatchList == null) {
                toWatchList = new ToWatchListFragment();
            }
            if (!toWatchList.isAdded()) {
                //fragmentTransaction.add(R.id.fragment_container, toWatchList);
                fragmentTransaction.replace(R.id.fragment_container, toWatchList);
                fragmentTransaction.commit();
                sendBroadcastForWatchList();
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        String tabText = String.valueOf(tab.getText());
        System.out.println("tab " + tabText + " was unselected: " + tab);

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        String tabText = String.valueOf(tab.getText());
        System.out.println("tab " + tabText + " was reselected: " + tab);
        if (tabText.equals("MOVIES")) {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            android.app.Fragment movieList = fm.findFragmentByTag(FRAGMENT_TAG_LIST);
            if (movieList == null) {
                movieList = new MovieListFragment();
            }
            if (!movieList.isAdded()) {
                fragmentTransaction.replace(R.id.fragment_container, movieList, FRAGMENT_TAG_LIST);
                //fragmentTransaction.commit();
            }

        }
    }
}
