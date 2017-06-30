package com.leckan.popularmoviestwo.Utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.leckan.popularmoviestwo.Adapter.VideoAdapter;
import com.leckan.popularmoviestwo.Model.MovieVideo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Leckan on 6/27/2017.
 */

public class DownloadVideosTask  extends AsyncTask<Object, Object, Void> {

    private Context mContext;
    RecyclerView videoRecyclerView;
    String videosURL;
    ArrayList<MovieVideo> videoArrayList;
    ProgressDialog pDialog;
    VideoAdapter videoAdapter;

    public DownloadVideosTask(Context context, RecyclerView rv, String url) {
        mContext = context;
        videoRecyclerView = rv;
        videosURL = url;
    }

    @Override
    protected Void doInBackground(Object... voids) {

        String jsonStr = NetworkUtils.makeServiceCall(videosURL);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray theVideos = jsonObj.getJSONArray("results");
                videoArrayList = new ArrayList<MovieVideo>();
                // looping through All Contacts
                for (int i = 0; i < theVideos.length(); i++) {
                    JSONObject c = theVideos.getJSONObject(i);
                    MovieVideo aVideo = new MovieVideo();
                    aVideo.setId(c.getString("id"));
                    aVideo.setIso_639_1(c.getString("iso_639_1"));
                    aVideo.setIso_3166_1(c.getString("iso_3166_1"));
                    aVideo.setKey(c.getString("key"));
                    aVideo.setName(c.getString("name"));
                    aVideo.setSite(c.getString("site"));
                    aVideo.setSize(c.getInt("size"));
                    aVideo.setType(c.getString("type"));
                    if(aVideo.getSite().equalsIgnoreCase("youtube"))
                    {
                        videoArrayList.add(aVideo);
                    }
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

        videoAdapter = new VideoAdapter(videoArrayList, mContext);

        videoRecyclerView.setAdapter(videoAdapter);

    }
}