package com.leckan.popularmoviestwo.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.leckan.popularmoviestwo.Model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leckan on 6/30/2017.
 */

public class MovieSystem {
    private final Context context;

    public MovieSystem(Context context) {
        this.context = context.getApplicationContext();
    }

    public void Save(Movie movie) {
        context.getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI, movie.toContentValues());

            }

    public void Delete(Movie movie) {
        context.getContentResolver().delete(
                MoviesContract.MovieEntry.CONTENT_URI,
                MoviesContract.MovieEntry.COLUMN_MOVIE_ID + " = " + movie.getId(),
                null
        );
    }
    public List<Movie> GetAllMovies()
    {
        List<Movie> movies = new ArrayList<Movie>();

        Cursor cursor = context.getContentResolver().query(
                MoviesContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Movie movie = cursorToMovie(cursor);
            movies.add(movie);
            cursor.moveToNext();
        }
        cursor.close();
        return movies;
    }


    private Movie cursorToMovie(Cursor cursor) {
        Movie movie = new Movie();
        movie.setId(cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_ID)));
        movie.setTitle(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_TITLE)));
        movie.setOriginal_title(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE)));
        movie.setBackdrop_path(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH)));
        movie.setPopularity(cursor.getFloat(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_POPULARITY)));
        movie.setPoster_path(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_POSTER_PATH)));
        movie.setRelease_date(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE)));
        movie.setVote_average(cursor.getFloat(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE)));
        movie.setVote_count(cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT)));
        movie.setLocal_poster_path(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_LOCAL_POSTER_PATH)));
        movie.setOverview(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_OVERVIEW)));
        movie.setOverview(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_OVERVIEW)));
        return movie;
    }

    public boolean isAvailable(Movie movie) {
        boolean exist = false;
        Cursor cursor = context.getContentResolver().query(
                MoviesContract.MovieEntry.CONTENT_URI,
                null,
                MoviesContract.MovieEntry.COLUMN_MOVIE_ID + " = " + movie.getId(),
                null,
                null
        );
        if (cursor != null) {
            exist = cursor.getCount() != 0;
            cursor.close();
        }
        return exist;
    }
}
