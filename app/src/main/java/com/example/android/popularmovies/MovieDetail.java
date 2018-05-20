package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {
    Movie movie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        Intent intent = getIntent();
        if (intent != null) {
            movie = getIntent().getParcelableExtra("selectedMovieKey");
            if (movie == null) {
                closeOnError();
            }
            populateUI(movie);
        } else {
            closeOnError();
        }

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Movie myMovie) {
        ImageView thumbnail = findViewById(R.id.thumbnail_iv);
        TextView title = findViewById(R.id.originalTitle_tv);
        TextView overview = findViewById(R.id.overview_tv);
        TextView rating = findViewById(R.id.user_rating_tv);
        TextView releaseDate = findViewById(R.id.release_date_tv);
        StringBuilder url = new StringBuilder("http://image.tmdb.org/t/p/w500/");
        String poster_path = myMovie.getPosterPath();
        url.append(poster_path);
        Picasso.with(this).load(url.toString()).into(thumbnail);
        title.setText(myMovie.getMovieTitle());
        overview.setText(myMovie.getOverview());
        rating.setText(myMovie.getRating());
        releaseDate.setText(myMovie.getReleaseDate());

    }
}
