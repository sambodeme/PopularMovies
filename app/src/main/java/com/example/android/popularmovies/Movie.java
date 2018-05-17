package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class Movie implements Parcelable {
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    int id;
    String movieTitle;
    String overview;
    String posterPath;

    protected Movie(Parcel in) {
        id = in.readInt();
        movieTitle = in.readString();
        overview = in.readString();
        posterPath = in.readString();
    }

    public Movie(int id, String movieTitle, String overview, String posterPath) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.overview = overview;
        this.posterPath = posterPath;


    }

    public int getId() {
        return id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(movieTitle);
        parcel.writeString(overview);
        parcel.writeString(posterPath);
    }
}
