package com.leckan.popularmoviestwo.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Leckan on 6/28/2017.
 */

public class MoviesDBHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = MoviesDBHelper.class.getSimpleName();

    //name & version
    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 5;

    public MoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
                MoviesContract.MovieEntry.TABLE_MOVIES + "(" + MoviesContract.MovieEntry._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MoviesContract.MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MoviesContract.MovieEntry.COLUMN_TITLE + " TEXT , " +
                MoviesContract.MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MoviesContract.MovieEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +
                MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                MoviesContract.MovieEntry.COLUMN_VOTE_COUNT + " INTEGER NOT NULL," +
                MoviesContract.MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MoviesContract.MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH + " TEXT , " +
                MoviesContract.MovieEntry.COLUMN_LOCAL_POSTER_PATH + " TEXT );" ;

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to " +
                newVersion + ". OLD DATA WILL BE DESTROYED");
        // Drop the table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MovieEntry.TABLE_MOVIES);

        // re-create database
        onCreate(sqLiteDatabase);
    }
}
