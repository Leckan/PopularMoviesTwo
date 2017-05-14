package com.leckan.popularmoviestwo.Model;

import com.leckan.popularmoviestwo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leckan on 5/14/2017.
 */

public class DummyMovies {
    private static int[] images = {R.mipmap.ff8, R.mipmap.ff8};
    private static String[] titles = {"Movie Title", "Film Title"};

    public static List<Movie> getMovieList() {
        List<Movie> movies = new ArrayList<>();

        for (int j = 0; j < 20; j++) {
            for (int i = 0; i < images.length && i < titles.length; i++) {
                Movie aMovie = new Movie();
                aMovie.setOriginal_title(titles[i]);
                aMovie.setImageRes(images[i]);
                movies.add(aMovie);
            }
        }
        return movies;
    }
}