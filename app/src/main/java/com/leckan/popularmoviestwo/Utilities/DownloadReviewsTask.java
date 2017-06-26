package com.leckan.popularmoviestwo.Utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.leckan.popularmoviestwo.Adapter.ReviewAdapter;
import com.leckan.popularmoviestwo.Model.MovieReview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Leckan on 6/26/2017.
 */

public class DownloadReviewsTask extends AsyncTask<Object, Object, Void> {

    private Context mContext;
    RecyclerView reviewRecyclerView;
    String reviewsURL;
    ArrayList<MovieReview> reviewArrayList;
    ProgressDialog pDialog;
    ReviewAdapter reviewAdapter;

    public DownloadReviewsTask(Context context, RecyclerView rv, String url) {
        mContext = context;
        reviewRecyclerView = rv;
        reviewsURL = url;
    }

    @Override
    protected Void doInBackground(Object... voids) {

        String jsonStr = NetworkUtils.makeServiceCall(reviewsURL);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray theReviews = jsonObj.getJSONArray("results");
                reviewArrayList = new ArrayList<MovieReview>();
                // looping through All Contacts
                for (int i = 0; i < theReviews.length(); i++) {
                    JSONObject c = theReviews.getJSONObject(i);
                    MovieReview aReview = new MovieReview();
                    aReview.setAuthor(c.getString("author"));
                    aReview.setContent(c.getString("content"));
                    aReview.setId(c.getString("id"));
                    aReview.setUrl(c.getString("url"));
                    reviewArrayList.add(aReview);
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

        reviewAdapter = new ReviewAdapter(reviewArrayList, mContext);

        reviewRecyclerView.setAdapter(reviewAdapter);

    }
}