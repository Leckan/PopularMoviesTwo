package com.leckan.popularmoviestwo.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leckan.popularmoviestwo.Adapter.ReviewAdapter;
import com.leckan.popularmoviestwo.Adapter.VideoAdapter;
import com.leckan.popularmoviestwo.Data.MovieSystem;
import com.leckan.popularmoviestwo.Model.Movie;
import com.leckan.popularmoviestwo.Model.MovieReview;
import com.leckan.popularmoviestwo.Model.MovieVideo;
import com.leckan.popularmoviestwo.R;
import com.leckan.popularmoviestwo.Utilities.DownloadReviewsTask;
import com.leckan.popularmoviestwo.Utilities.DownloadVideosTask;
import com.leckan.popularmoviestwo.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoviesDetail extends AppCompatActivity implements VideoAdapter.ListItemClickListener {

   ReviewAdapter reviewAdapter;
    VideoAdapter videoAdapter;
   @BindView(R.id.reviews_recycler_view) RecyclerView reviewRecyclerView;
    @BindView(R.id.videos_recycler_view) RecyclerView videoRecyclerView;
   @BindView(R.id.ivDetailImage) ImageView imageView;
   @BindView(R.id.tvDetailMovieTitle) TextView titleView;
    @BindView(R.id.tvDetailRating) TextView ratingView;
   @BindView(R.id.tvDetailYear) TextView yearView;
   @BindView(R.id.tvDetailOverview) TextView overviewView;
    @BindView(R.id.favorite_image_button) ImageButton favoriteButton;
    ConnectivityManager mConMgr;
    URL videosURL;
    URL reviewsURL;
    Movie theMovie;

    boolean isRestoredState;
    private final String IS_RESTORED_SAVED_STATE = "detailrestored";
    private final String VIDEOS_SAVED_STATE = "videos";

    private final String REVIEWS_SAVED_STATE = "reviews";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);
        ButterKnife.bind(this);

        theMovie = (Movie) getIntent().getParcelableExtra("theMovie");

       // ImageView imageView = (ImageView) findViewById(R.id.ivDetailImage);
        URL url = NetworkUtils.buildImageUrl(theMovie.getPoster_path());
        Picasso.with(this.getBaseContext()).load(url.toString())
                .placeholder(R.drawable.ic_photo)
                .error(R.drawable.ic_photo)
                .into(imageView);

        titleView.setText(theMovie.getOriginal_title());

        ratingView.setText("Rated: " + String.valueOf(theMovie.getVote_average()) + "/10");
        if(theMovie.getRelease_date() != null)
        {yearView.setText(theMovie.getRelease_date().substring(0, 4));}
        overviewView.setText(theMovie.getOverview());

        videosURL = NetworkUtils.buildVideosUrl(theMovie.getId());
        reviewsURL = NetworkUtils.buildReviewsUrl(theMovie.getId());

        mConMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        reviewRecyclerView.setLayoutManager(layoutManager);


        LinearLayoutManager videoLayoutManager = new LinearLayoutManager(this);
        videoRecyclerView.setLayoutManager(videoLayoutManager);

        List<MovieVideo> movieVideos = new ArrayList<>();
        List<MovieReview> movieReviews = new ArrayList<>();
       /* isRestoredState = false;
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(REVIEWS_SAVED_STATE)) {
                movieReviews = savedInstanceState.getParcelableArrayList(REVIEWS_SAVED_STATE);
            }
            if (savedInstanceState.containsKey(VIDEOS_SAVED_STATE)) {
                movieVideos = savedInstanceState.getParcelableArrayList(VIDEOS_SAVED_STATE);
            }
            if(savedInstanceState.containsKey(IS_RESTORED_SAVED_STATE))
            {
                isRestoredState = true;
            }
        }*/
        reviewAdapter = new ReviewAdapter(movieReviews, this);
        reviewRecyclerView.setAdapter(reviewAdapter);


        videoAdapter = new VideoAdapter(movieVideos, this);
        videoRecyclerView.setAdapter(videoAdapter);

        updateFavoriteIcon();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

       // outState.putParcelableArray(REVIEWS_SAVED_STATE, (Parcelable[]) reviewAdapter.reviewList.toArray());
      //  outState.putParcelableArray(VIDEOS_SAVED_STATE, (Parcelable[]) videoAdapter.videoList.toArray());
      //  outState.putBoolean(IS_RESTORED_SAVED_STATE,isRestoredState);
    }

    @Override
    protected void onStart() {
        super.onStart();

      //  if(isRestoredState == false) {
            NetworkInfo networkInfo = mConMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                LoadReviews();
                LoadVideos();
            }
       // }
    }
    @OnClick(R.id.favorite_image_button)
    void onFavoriteButtonClicked() {

        MovieSystem movieSystem = new MovieSystem(MoviesDetail.this);
        if (movieSystem.isAvailable(theMovie)) {
            movieSystem.Delete(theMovie);
            Toast.makeText(this,
                    "Removed from favorites",
                    Toast.LENGTH_LONG)
                    .show();
        } else {
            movieSystem.Save(theMovie);
            Toast.makeText(this,
                    "Added to favorites",
                    Toast.LENGTH_LONG)
                    .show();
        }
        updateFavoriteIcon();
    }

    private void updateFavoriteIcon() {

        MovieSystem movieSystem = new MovieSystem(MoviesDetail.this);
        if (movieSystem.isAvailable(theMovie)) {
            favoriteButton.setImageResource(R.drawable.ic_favorite);
        } else {
            favoriteButton.setImageResource(R.drawable.ic_favorite_border);
        }
    }
    public void LoadReviews() {
        new DownloadReviewsTask(MoviesDetail.this, reviewRecyclerView, reviewsURL.toString()).execute();
    }
    public void LoadVideos() {
        new DownloadVideosTask(MoviesDetail.this, videoRecyclerView, videosURL.toString()).execute();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }
}
