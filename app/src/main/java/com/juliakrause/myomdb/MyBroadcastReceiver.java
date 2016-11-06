package com.juliakrause.myomdb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Julia on 08.10.2016.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION_LOAD_DETAILS = "com.juliakrause.myomdb.MyBroadcastReceiver.ACTION.LOAD_DETAILS";
    public static final String EXTRA_IMDBID = "com.juliakrause.myomdb.MyBroadcastReceiver.extra.IMDBID";

    private MainActivity mainActivity;
    
	
	public MyBroadcastReceiver(MainActivity mainActivity) {         
		this.mainActivity = mainActivity;     
	}     

	@Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("in onReceive method");
		if (intent != null) {             
			final String action = intent.getAction();             
			if (ACTION_LOAD_DETAILS.equals(action)) {                 
				final String imdbID = intent.getStringExtra(EXTRA_IMDBID);                 
				//TODO: das irgendwie anders machen
                //mainActivity.loadDetails(imdbID);
			}         
		}
    }

}