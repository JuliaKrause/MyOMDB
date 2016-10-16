package com.juliakrause.myomdb;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.Intents.Insert.ACTION;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    //private static final String ACTION_FOO = "com.juliakrause.myomdb.action.FOO";
    //private static final String ACTION_BAZ = "com.juliakrause.myomdb.action.BAZ";

    // TODO: Rename parameters
    //private static final String EXTRA_PARAM1 = "com.juliakrause.myomdb.extra.PARAM1";
    //private static final String EXTRA_PARAM2 = "com.juliakrause.myomdb.extra.PARAM2";
    private static String URL_BASE = "http://omdbapi.com";
    private String url;
    private Handler handler;

    public MyIntentService() {
        super("MyIntentService");
    }

    //diese Methode läuft noch im UI Thread
    //nur die onHandleIntent Methode läuft im Service Thread
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("This is the onCreate Method in the intentService");
        handler = new Handler();
        //hier Handler konstruieren, diesen dann in onHandleIntent Methode aufrufen
    }

    public void onStartCommand() {

    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    /*public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }*/

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    /*public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }*/

    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("this is the intent service's onHandleIntent method");
        if (intent != null) {
            String val = intent.getStringExtra("query");
            url = URL_BASE + val;
            System.out.println(url);

            System.out.println("intent is not null");
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest myRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("Search");
                                System.out.println(jsonArray.length());
                                List<String> list = new ArrayList<String>();
                                for(int i = 0; i < jsonArray.length(); i++) {
                                    list.add(jsonArray.getJSONObject(i).getString("Title"));
                                }
                                for(String title : list) {
                                    System.out.println(title);
                                    //handler.handleMessage(title);

                                    /*Intent in = new Intent(ACTION);
                                    in.putExtra("resultCode", MainActivity.RESULT_OK);
                                    in.putExtra("resultValue", "My Result Value. Passed in: " + title);
                                    // Fire the broadcast with intent packaged
                                    LocalBroadcastManager.getInstance(this).sendBroadcast;*/

                                }
                                //System.out.println(jsonArray.toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //String responseString = response.toString();

                            //System.out.println(responseString);
                            //stattdessen hier nur den Titel ausgeben
                            //GSON benutzen
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if(error.networkResponse != null) {
                                if (error.networkResponse.statusCode == 404) {
                                    System.out.println("this is the error listener");
                                }
                            } else {
                                System.out.println("error.networkResponse is null");
                            }
                        }
                    });
            queue.add(myRequest);

            //hier den Handler aufrufen, mit dem dann die Daten an UI Thread posten
            //hier kommt das Request rein
            //fuer Liste mit /?s=string
            //fuer details zu film mit /?i=id von item


            final String action = intent.getAction();
            /*if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }*/
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
