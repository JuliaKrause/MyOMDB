package com.juliakrause.myomdb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.logging.Logger;


/**
 * Created by Julia on 08.10.2016.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    //private static final Logger LOGGER = LoggerFactory.getLogger(MyBroadcastReceiver.class);

    public static final String ACTION_MYBCRACTION =
                "com.juliakrause.myomdb.MyBroadcastReceiver.ACTION_MYBCRACTION";
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("in onReceive method");
        /*LOGGER.debug("Broadcast received \\o/ "
                + intent.hashCode());*/
    }
}
