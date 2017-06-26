package com.leckan.popularmoviestwo.Utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.leckan.popularmoviestwo.Adapter.MovieAdapter;
import com.leckan.popularmoviestwo.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Leckan on 6/26/2017.
 */

public class DownloadMoviesTask extends AsyncTask<Object, Object, Void> {

    private Context mContext;
    String sPreferredType;
    ArrayList<Movie> dMovies;
    ProgressDialog pDialog;
    RecyclerView moviesList;
    MovieAdapter adapter;

    public DownloadMoviesTask(Context context, String preferredType, RecyclerView moviesRecyclerView) {
        mContext = context;
        moviesList = moviesRecyclerView;
        sPreferredType = preferredType;
    }

    //private MainActivity theActivity;
    @Override
    protected Void doInBackground(Object... voids) {

        URL movieURL = NetworkUtils.buildUrl(sPreferredType);
        String jsonStr = NetworkUtils.makeServiceCall(movieURL.toString());
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray myUsers = jsonObj.getJSONArray("results");
                dMovies = new ArrayList<Movie>();
                // looping through All Contacts
                for (int i = 0; i < myUsers.length(); i++) {
                    JSONObject c = myUsers.getJSONObject(i);
                    Movie aMovie = new Movie();
                    aMovie.setId(c.getInt("id"));
                    aMovie.setOriginal_title(c.getString("original_title"));
                    aMovie.setPoster_path(c.getString("poster_path"));
                    aMovie.setOverview(c.getString("overview"));
                    aMovie.setRelease_date(c.getString("release_date"));
                    aMovie.setVote_average(Float.valueOf(c.getString("vote_average")));
                    // adding contact to contact list
                    dMovies.add(aMovie);
                }
            } catch (final JSONException e) {
                Log.e("Main", "Json parsing error: " + e.getMessage());
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext,
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
        } else {
            Log.e("Main", "Couldn't get json from server.");
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext,
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG)
                            .show();
                }
            });

        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();
        /**
         * Updating parsed JSON data into ListView
         * */

        adapter = new MovieAdapter(dMovies, mContext);

        moviesList.setAdapter(adapter);

    }
}
