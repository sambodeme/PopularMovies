package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class Movie implements Parcelable {
    public Movie(){

    }
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
    String rating;
    String releaseDate;
    protected Movie(Parcel in) {
        id = in.readInt();
        movieTitle = in.readString();
        overview = in.readString();
        posterPath = in.readString();
        rating = in.readString();
        releaseDate = in.readString();
    }

    public Movie(int id, String movieTitle, String overview, String posterPath, String rating, String releaseDate) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.rating = rating;
        this.releaseDate = releaseDate;
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

    public String getRating() {
        return rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(movieTitle);
        parcel.writeString(overview);
        parcel.writeString(posterPath);
        parcel.writeString(rating);
        parcel.writeString(releaseDate);
    }
}
