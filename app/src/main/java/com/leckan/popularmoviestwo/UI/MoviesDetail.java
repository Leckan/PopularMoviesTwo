package com.leckan.popularmoviestwo.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.leckan.popularmoviestwo.Model.Movie;
import com.leckan.popularmoviestwo.R;
import com.leckan.popularmoviestwo.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class MoviesDetail extends AppCompatActivity {

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
        ratingView.setText("Rated: "+ String.valueOf(theMovie.getVote_average())+"/10");
        TextView yearView = (TextView) findViewById(R.id.tvDetailYear);
        yearView.setText(theMovie.getRelease_date().substring(0,4));
        TextView overviewView = (TextView) findViewById(R.id.tvDetailOverview);
        overviewView.setText(theMovie.getOverview());
    }
}
