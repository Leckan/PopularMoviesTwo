package com.leckan.popularmoviestwo.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.leckan.popularmoviestwo.Adapter.ReviewAdapter;
import com.leckan.popularmoviestwo.Model.Movie;
import com.leckan.popularmoviestwo.Model.MovieReview;
import com.leckan.popularmoviestwo.Model.MovieVideo;
import com.leckan.popularmoviestwo.R;
import com.leckan.popularmoviestwo.Utilities.DownloadReviewsTask;
import com.leckan.popularmoviestwo.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MoviesDetail extends AppCompatActivity {

    RecyclerView reviewRecyclerView;
    ReviewAdapter reviewAdapter;

    ConnectivityManager mConMgr;
    URL videosURL;
    URL reviewsURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);


        Movie theMovie = (Movie) getIntent().getSerializableExtra("theMovie");

        ImageView imageView = (ImageView) findViewById(R.id.ivDetailImage);
        URL url = NetworkUtils.buildImageUrl(theMovie.getPoster_path());
        Picasso.with(this.getBaseContext()).load(url.toString()).into(imageView);

        TextView titleView = (TextView) findViewById(R.id.tvDetailMovieTitle);
        titleView.setText(theMovie.getOriginal_title());


        TextView ratingView = (TextView) findViewById(R.id.tvDetailRating);
        ratingView.setText("Rated: " + String.valueOf(theMovie.getVote_average()) + "/10");
        TextView yearView = (TextView) findViewById(R.id.tvDetailYear);
        yearView.setText(theMovie.getRelease_date().substring(0, 4));
        TextView overviewView = (TextView) findViewById(R.id.tvDetailOverview);
        overviewView.setText(theMovie.getOverview());


        videosURL = NetworkUtils.buildVideosUrl(theMovie.getId());
        reviewsURL = NetworkUtils.buildReviewsUrl(theMovie.getId());


        mConMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        reviewRecyclerView = (RecyclerView) findViewById(R.id.reviews_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        reviewRecyclerView.setLayoutManager(layoutManager);
        List<MovieReview> movieReviews = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(movieReviews, this);

        reviewRecyclerView.setAdapter(reviewAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        NetworkInfo networkInfo = mConMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            LoadReviews();
        }
    }

    public void LoadReviews() {
        new DownloadReviewsTask(MoviesDetail.this, reviewRecyclerView, reviewsURL.toString()).execute();
    }

}
