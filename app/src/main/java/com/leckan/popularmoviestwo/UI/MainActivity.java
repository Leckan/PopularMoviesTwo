package com.leckan.popularmoviestwo.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.leckan.popularmoviestwo.Adapter.MovieAdapter;
import com.leckan.popularmoviestwo.Model.DummyMovies;
import com.leckan.popularmoviestwo.Model.Movie;
import com.leckan.popularmoviestwo.R;
import com.leckan.popularmoviestwo.Utilities.NetworkUtils;
import com.leckan.popularmoviestwo.Utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener {

    ConnectivityManager mConMgr;
    RecyclerView moviesList;
    MovieAdapter adapter;
    private String sPreferredType;
    private ProgressDialog pDialog;

    ArrayList<Movie> dMovies ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        moviesList = (RecyclerView) findViewById(R.id.recycler_view);

        int mNoOfColumns = Utility.calculateNoOfColumns(getApplicationContext());
        mConMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        List<Movie> Movies = DummyMovies.getMovieList();
        GridLayoutManager layoutManager = new GridLayoutManager(this, mNoOfColumns);

        moviesList.setLayoutManager(layoutManager);
        adapter = new MovieAdapter(Movies, this);

        moviesList.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        sPreferredType = preferences.getString("chosenSortType1", "popular");
        NetworkInfo networkInfo = mConMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            LoadMoviesPage(sPreferredType);
        } else {
            LoadDefaultPage();
        }
    }

    private void LoadDefaultPage() {
    }

    public void LoadMoviesPage(String prefType) {
        new DownloadMoviesTask().execute();
    }

    private class DownloadMoviesTask extends AsyncTask<Object, Object, Void> {


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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e("Main", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
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
            pDialog = new ProgressDialog(MainActivity.this);
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

            adapter = new MovieAdapter(dMovies, MainActivity.this);

            moviesList.setAdapter(adapter);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_settings) {
            Intent settingsIntent = new Intent(getBaseContext(), SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }
}
