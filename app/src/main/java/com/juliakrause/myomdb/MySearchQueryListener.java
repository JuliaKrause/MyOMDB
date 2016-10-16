package com.juliakrause.myomdb;

import android.content.Intent;
import android.widget.SearchView;

/**
 * Created by Julia on 16.10.2016.
 */

public class MySearchQueryListener implements SearchView.OnQueryTextListener {


    public MySearchQueryListener() {
        System.out.println("This is the ctor of the Query Listener");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        /*The listener can override the standard behavior by returning true
        to indicate that it has handled the submit request.
        Otherwise return false to let the SearchView handle the submission
        by launching any associated intent.*/

        //I would like to start the intent service from here, or get the query to the main activity
        System.out.println(query);
        return true;
        //return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        /*false if the SearchView should perform the default action
        of showing any suggestions if available,
        true if the action was handled by the listener.*/
        return false;
    }
}
