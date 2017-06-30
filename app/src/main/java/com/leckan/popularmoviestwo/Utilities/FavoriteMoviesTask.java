package com.leckan.popularmoviestwo.Utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.leckan.popularmoviestwo.Adapter.MovieAdapter;
import com.leckan.popularmoviestwo.Data.MovieSystem;
import com.leckan.popularmoviestwo.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Leckan on 6/26/2017.
 */

public class FavoriteMoviesTask extends AsyncTask<Object, Object, Void> {

    private Context mContext;
    String sPreferredType;
    ArrayList<Movie> dMovies;
    ProgressDialog pDialog;
    RecyclerView moviesList;
    MovieAdapter adapter;

    public FavoriteMoviesTask(Context context,  RecyclerView moviesRecyclerView) {
        mContext = context;
        moviesList = moviesRecyclerView;
    }

    //private MainActivity theActivity;
    @Override
    protected Void doInBackground(Object... voids) {

        dMovies = (ArrayList<Movie>) new MovieSystem(mContext).GetAllMovies();

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
