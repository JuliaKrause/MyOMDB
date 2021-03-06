package com.juliakrause.myomdb;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class MyIntentService extends IntentService {

    private static final String ACTION_SEARCHOMDB = "com.juliakrause.myomdb.action.SEARCHOMDB";
    private static final String ACTION_GET_DETAILS = "com.juliakrause.myomdb.action.GET_DETAILS";
    private static final String EXTRA_TITLE = "com.juliakrause.myomdb.extra.TITLE";
    private static final String EXTRA_IMDBID = "com.juliakrause.myomdb.extra.IMDBID";

    private static final String OMDB_RESPONSE = "Response";
    private static final String OMDB_ID = "imdbID";
    private static final String OMDB_TITLE = "Title";
    private static final String OMDB_YEAR = "Year";
    private static final String OMDB_RATED = "Rated";
    private static final String OMDB_RELEASED = "Released";
    private static final String OMDB_RUNTIME = "Runtime";
    private static final String OMDB_GENRE = "Genre";
    private static final String OMDB_DIRECTOR = "Director";
    private static final String OMDB_WRITER = "Writer";
    private static final String OMDB_ACTORS = "Actors";
    private static final String OMDB_PLOT = "Plot";
    private static final String OMDB_TYPE = "Type";
    private static final String OMDB_SEARCH_RESULT = "Search";
    public static final String MESSAGE_SEARCH_RESULT = "com.juliakrause.myomdb.message.SEARCH_RESULT";
    public static final String MESSAGE_DETAILS = "com.juliakrause.myomdb.message.DETAILS";

    private static final String URL_BASE = "http://omdbapi.com";

    private Handler searchHandler;
    private Handler detailsHandler;

    public MyIntentService() {
        super("MyIntentService");
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent();
            if(msg.getData().containsKey(MESSAGE_SEARCH_RESULT)) {
                ArrayList<Movie> movies;
                movies = msg.getData().getParcelableArrayList(MESSAGE_SEARCH_RESULT);
                intent.setAction(MovieListFragmentBroadcastReceiver.ACTION_SHOW_SEARCH_RESULT);
                intent.putParcelableArrayListExtra(MovieListFragmentBroadcastReceiver.EXTRA_SEARCH_RESULT, movies);
            } else if(msg.getData().containsKey(MESSAGE_DETAILS)) {
                ArrayList<Movie> movieWithDetails = new ArrayList<>();
                Movie movie = msg.getData().getParcelable(MESSAGE_DETAILS);
                movieWithDetails.add(movie);
                intent.setAction(MainBroadcastReceiver.ACTION_LOAD_DETAILS);
                intent.putParcelableArrayListExtra(MainBroadcastReceiver.EXTRA_MOVIE, movieWithDetails);
            }
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        this.searchHandler = new MyHandler();
        this.detailsHandler = new MyHandler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            final String action = intent.getAction();

            if (ACTION_SEARCHOMDB.equals(action)) {
                final String title = intent.getStringExtra(EXTRA_TITLE);
                handleActionSearch(title);
            } else if (ACTION_GET_DETAILS.equals(action)) {
                final String imdbid = intent.getStringExtra(EXTRA_IMDBID);
                handleActionGetDetails(imdbid);
            }
        }
    }

    /**
     * Handle action Search in the provided background thread
     */
    private void handleActionSearch(String title) {
        String url = URL_BASE + "/?s=" + title;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest myRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString(OMDB_RESPONSE).equals("True")) {
                                Message msg = Message.obtain();
                                Bundle data = new Bundle();
                                data.putParcelableArrayList(MESSAGE_SEARCH_RESULT, parseSearchResponse(response));
                                msg.setData(data);

                                Messenger myMessenger = new Messenger(searchHandler);
                                myMessenger.send(msg);
                            }

                        } catch (JSONException jse) {
                            jse.printStackTrace();
                        } catch (RemoteException re) {
                            re.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.networkResponse != null) {
                            if (error.networkResponse.statusCode == 404) {
                                System.out.println("not found");
                            }
                        } else {
                            System.out.println("no connection");
                        }
                    }
                });
        queue.add(myRequest);
    }


    /**
     * Handle action getDetails in the provided background thread
     */

    private void handleActionGetDetails(String imdbid) {
        String url = URL_BASE + "/?i=" + imdbid;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest detailsRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString(OMDB_RESPONSE).equals("True")) {
                                Message msg = Message.obtain();
                                Bundle data = new Bundle();
                                data.putParcelable(MESSAGE_DETAILS, parseDetailsResponse(response));
                                msg.setData(data);

                                Messenger myMessenger = new Messenger(detailsHandler);
                                myMessenger.send(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.networkResponse != null) {
                            if (error.networkResponse.statusCode == 404) {
                                System.out.println("not found");
                            }
                        } else {
                            System.out.println("no connection");
                        }
                    }
                });
        queue.add(detailsRequest);

    }

    private ArrayList<Movie> parseSearchResponse(JSONObject response) throws JSONException {
        JSONArray moviesJSON = response.getJSONArray(OMDB_SEARCH_RESULT);
        ArrayList<Movie> movies = new ArrayList<>();
        for (int i = 0; i < moviesJSON.length(); i++) {
            JSONObject movieJSON = moviesJSON.getJSONObject(i);
            String imdbID = movieJSON.getString(OMDB_ID);
            String title = movieJSON.getString(OMDB_TITLE);
            String year = movieJSON.getString(OMDB_YEAR);
            String type = movieJSON.getString(OMDB_TYPE);
            movies.add(new Movie(imdbID, title, year, type));
        }
        return movies;
    }

    private Movie parseDetailsResponse(JSONObject response) throws JSONException {
        String imdbID = response.getString(OMDB_ID);
        String title = response.getString(OMDB_TITLE);
        String year = response.getString(OMDB_YEAR);
        String rated = response.getString(OMDB_RATED);
        String released = response.getString(OMDB_RELEASED);
        String runtime = response.getString(OMDB_RUNTIME);
        String genre = response.getString(OMDB_GENRE);
        String director = response.getString(OMDB_DIRECTOR);
        String writer = response.getString(OMDB_WRITER);
        String actors = response.getString(OMDB_ACTORS);
        String plot = response.getString(OMDB_PLOT);
        String type = response.getString(OMDB_TYPE);
        return new Movie(imdbID, title, year, rated, released, runtime, genre, director, writer, actors, plot, type);
    }

}
