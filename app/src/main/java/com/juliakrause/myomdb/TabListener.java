package com.juliakrause.myomdb;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

/**
 * Created by Julia on 07.10.2016.
 */

public class TabListener implements TabLayout.OnTabSelectedListener {
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        System.out.println("a tab was selected: " + tab);
        //muss eventuell auch noch FragmentTransaction ft uebergeben kriegen
        //ft.replace(R.id.somefragmentid, someFragment);
        // DO NOT COMMIT THE TRANSACTION!
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        //muss eventuell auch noch FragmentTransaction ft uebergeben kriegen
        System.out.println("a tab was unselected: " + tab);

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        //muss eventuell auch noch FragmentTransaction ft uebergeben kriegen
        System.out.println("a tab was reselected: " + tab);

    }
}

