package com.juliakrause.myomdb;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import static com.juliakrause.myomdb.MainActivity.FRAGMENT_TAG_LIST;

/**
 * Created by Julia on 07.10.2016.
 */

public class TabListener implements TabLayout.OnTabSelectedListener {

    private FragmentManager fm;

    public TabListener(FragmentManager fm) {
        super();
        this.fm = fm;
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
        }
        //muss eventuell auch noch FragmentTransaction ft uebergeben kriegen
        //ft.replace(R.id.somefragmentid, someFragment);
        // DO NOT COMMIT THE TRANSACTION!
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
