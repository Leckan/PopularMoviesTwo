package com.leckan.popularmoviestwo.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.leckan.popularmoviestwo.Adapter.MovieAdapter;
import com.leckan.popularmoviestwo.BuildConfig;
import com.leckan.popularmoviestwo.Model.DummyMovies;
import com.leckan.popularmoviestwo.Model.Movie;
import com.leckan.popularmoviestwo.R;
import com.leckan.popularmoviestwo.Utilities.DownloadMoviesTask;
import com.leckan.popularmoviestwo.Utilities.FavoriteMoviesTask;
import com.leckan.popularmoviestwo.Utilities.Utility;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener {

    ConnectivityManager mConMgr;
   @BindView(R.id.recycler_view) RecyclerView moviesList;
    MovieAdapter adapter;
    private String sPreferredType;
    private final String MOVIES_SAVED_STATE = "movies";
    boolean isRestoredState;
    private final String IS_RESTORED_SAVED_STATE = "detailrestored";
    private final String CURRENT_SCROLL_POSITION = "currentScrollPosition";
    private GridLayoutManager layoutManager;
    List<Movie> Movies;
    int currentScrollPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        if (BuildConfig.DEBUG_MODE) {

            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(
                                    Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this)).build());
            OkHttpClient client = new OkHttpClient();
            client.networkInterceptors().add(new StethoInterceptor());
        }

        int mNoOfColumns = Utility.calculateNoOfColumns(getApplicationContext());
        mConMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        isRestoredState = false;
        Movies = DummyMovies.getMovieList();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(MOVIES_SAVED_STATE)) {
                Movies = savedInstanceState.getParcelableArrayList(MOVIES_SAVED_STATE);
            }
            if(savedInstanceState.containsKey(IS_RESTORED_SAVED_STATE))
            {
                isRestoredState = true;
            }
            if (savedInstanceState.containsKey(CURRENT_SCROLL_POSITION)) {
                currentScrollPosition = savedInstanceState.getInt(CURRENT_SCROLL_POSITION, 0);
            }
        }
        layoutManager = new GridLayoutManager(this, mNoOfColumns);

        moviesList.setLayoutManager(layoutManager);
        adapter = new MovieAdapter(Movies, this);
        moviesList.setAdapter(adapter);

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(CURRENT_SCROLL_POSITION, layoutManager.findFirstVisibleItemPosition());
        outState.putParcelableArrayList(MOVIES_SAVED_STATE, (ArrayList<? extends Parcelable>) Movies);// adapter.movieList.toArray());
        outState.putBoolean(IS_RESTORED_SAVED_STATE,isRestoredState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(MOVIES_SAVED_STATE)) {
                Movies = savedInstanceState.getParcelableArrayList(MOVIES_SAVED_STATE);
            }
            if (savedInstanceState.containsKey(CURRENT_SCROLL_POSITION)) {
                currentScrollPosition = savedInstanceState.getInt(CURRENT_SCROLL_POSITION,0);
               layoutManager.scrollToPosition(currentScrollPosition);
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        sPreferredType = preferences.getString("chosenSortType1", "popular");


        if(Movies.get(0).getId()== 0) {
            NetworkInfo networkInfo = mConMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected() && !sPreferredType.equalsIgnoreCase("favorites")) {
                LoadMoviesPage(sPreferredType);
            } else {
                LoadDefaultPage();
            }
        }
    }

    private void LoadDefaultPage() {
        new FavoriteMoviesTask(MainActivity.this, moviesList).execute();
    }

    public void LoadMoviesPage(String prefType) {
        new DownloadMoviesTask(MainActivity.this, prefType, moviesList,currentScrollPosition).execute();

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

        onSaveInstanceState(null);
    }
}
