package com.leckan.popularmoviestwo.Model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.leckan.popularmoviestwo.Data.MoviesContract;

import java.io.Serializable;

/**
 * Created by Leckan on 5/14/2017.
 */

public class Movie implements Parcelable
{
    private int _id ;
    private int id ;
    private boolean adult ;
    private String backdrop_path ;
    private int[] genre_ids ;
    private int imageRes;
    private String original_language ;
    private String original_title ;
    private String overview ;
    private float popularity ;
    private String poster_path ;
    private String release_date;
    private String title ;
    private boolean video ;
    private int vote_count ;
    private String local_poster_path;
    public Movie()
    {}

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }
    public String getLocal_poster_path() {
        return local_poster_path;
    }

    public void setLocal_poster_path(String local_poster_path) {
        this.local_poster_path = local_poster_path;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    private float vote_average ;

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(MoviesContract.MovieEntry.COLUMN_MOVIE_ID, id);
        values.put(MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE, original_title);
        values.put(MoviesContract.MovieEntry.COLUMN_OVERVIEW, overview);
        values.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE, release_date);
        values.put(MoviesContract.MovieEntry.COLUMN_POSTER_PATH, poster_path);
        values.put(MoviesContract.MovieEntry.COLUMN_POPULARITY, popularity);
        values.put(MoviesContract.MovieEntry.COLUMN_TITLE, title);
        values.put(MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE, vote_average);
        values.put(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT, vote_count);
        values.put(MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH, backdrop_path);
        return values;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(backdrop_path);
        parcel.writeString(overview);
        parcel.writeFloat(popularity);
        parcel.writeString(poster_path);
        parcel.writeString(release_date);
        parcel.writeString(title);
        parcel.writeFloat(vote_average);
        parcel.writeInt(vote_count);
        parcel.writeString(original_title);

    }
    public Movie(Parcel parcel) {
        this.id = parcel.readInt();
        this.backdrop_path = parcel.readString();
        this.overview = parcel.readString();
        this.popularity = parcel.readFloat();
        this.poster_path = parcel.readString();
        this.release_date = parcel.readString();
        this.title = parcel.readString();
        this.vote_average = parcel.readFloat();
        this.vote_count = parcel.readInt();
        this.original_title = parcel.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
