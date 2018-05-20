package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.sip.SipAudioCall;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements RecycleviewAdapter.GridItemClickListener {
    private int itemsCount = 0; //holds the total # of movie items that must be handle by the RecyclerView
    private URL tmdbApiQueryUrl;
    private RecyclerView mRecyclerView; //handles the display of movie items within the main activity
    private RecycleviewAdapter myAdapter; //needed by the RecyclerView above for handling items display
    private Context context = this; //holds a reference to the Context of the current activity
    private ArrayList<Movie> movieArrayList; //holds a reference to the list of movie items that must be displayed
    private RecycleviewAdapter.GridItemClickListener listener = this; //holds a reference to the ClickListener implemented by the current activity
    private String toastMessage = "No connectivity....";
    private String API_KEY = BuildConfig.API_KEY; // PLEASE INSERT YOUR API KEY

    @Override
    /**
     * This method is aimed at saving useful instance members (such as the list of movie items)
     * so that, the application can easily retrieve them whenever an activity must stop and resume shortly
     */
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelableArrayList("movieArrayList", movieArrayList);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    /**
     * This method checks the connectivity of the device and return true (or false)
     * when the device has (or does not have) access to Internet
     *
     * @return
     */
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if ((savedInstanceState != null) && (savedInstanceState.containsKey("movieArrayList"))) {
            movieArrayList = savedInstanceState.getParcelableArrayList("movieArrayList");
        } else {
            makeTmdbApiQueryUrl("popular");
            if (isOnline()) {
                new FetchMyDataTask(this, new FetchMyDataTaskCompleteListener()).execute(tmdbApiQueryUrl);
            } else {
                Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * This method builds the
     * URL (using {@link NetworkUtils}) required for querying the tmdbApi
     * and saves this URL into tmdbApiQueryUrl
     */
    private void makeTmdbApiQueryUrl(String sortOrder) {
        Uri.Builder tmdbApiUrlBuilder = new Uri.Builder()
                .scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(sortOrder)
                .appendQueryParameter("api_key", API_KEY);

        String tmdbApiQueryString = tmdbApiUrlBuilder.build().toString();
        tmdbApiQueryUrl = NetworkUtils.buildUrl(tmdbApiQueryString);
    }

    /**
     * This method builds an ArrayList of movie items from a JSONArray of movie properties.
     * Here, the JSONArray will be retrieved from the TmdbAPI query.
     *
     * @param resultArray
     * @throws JSONException
     */
    private void setMovieArrayList(JSONArray resultArray) throws JSONException {
        int sizeOfList = resultArray.length();
        movieArrayList = new ArrayList<Movie>();
        for (int i = 0; i < sizeOfList; i++) {
            JSONObject movieItem = resultArray.getJSONObject(i);
            int id = movieItem.getInt("id");
            String title = movieItem.getString("title");
            String overview = movieItem.getString("overview");
            String poster_path = movieItem.getString("poster_path");
            String rating = movieItem.getString("vote_average");
            String releaseDate = movieItem.getString("release_date");
            Movie movie = new Movie(id, title, overview, poster_path, rating, releaseDate);
            movieArrayList.add(movie);
        }
    }

    /**
     * This method takes the tmdbApi  Query  Results (which is a String) as
     *
     * @param tmdbApiQueryResults, builds a JSONObject from this String,
     *                             and retrieves a JSONArray from that Object
     */
    private JSONArray getResultsArray(String tmdbApiQueryResults) {
        JSONArray resultsArray = null;
        if (tmdbApiQueryResults != null && !tmdbApiQueryResults.equals("")) {
            JSONObject root = null;
            try {
                root = new JSONObject(tmdbApiQueryResults);
                resultsArray = root.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return resultsArray;
    }

    /**
     * This method sets the # of movie items that must be handle by the RecyclerView Adapter
     * to the # of items contained in the movieArrayList retrieved from the tmdbApi query Results
     */
    private void setItemsCount() {
        itemsCount = movieArrayList.size();
    }


    private void launchDetailActivity(Movie movie) {

        Intent intent = new Intent(this, MovieDetail.class);
        intent.putExtra("selectedMovieKey", movie);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        // return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.mi_Popular) {
            makeTmdbApiQueryUrl("popular");
            if (isOnline()) {

                new FetchMyDataTask(this, new FetchMyDataTaskCompleteListener()).execute(tmdbApiQueryUrl);
            } else {
                Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if (itemThatWasClickedId == R.id.mi_topRated) {
            makeTmdbApiQueryUrl("top_rated");
            if (isOnline()) {

                new FetchMyDataTask(this, new FetchMyDataTaskCompleteListener()).execute(tmdbApiQueryUrl);
            } else {
                Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method call the code to execute when a user clicks on a movie item.
     *
     * @param clickedItem
     */
    @Override
    public void onGridItemClick(Movie clickedItem) {
        launchDetailActivity(clickedItem);
    }


    public class FetchMyDataTaskCompleteListener implements AsyncTaskCompleteListener<String> {


        @Override
        public void onTaskComplete(String tmdbApiQueryResults) {

            if (tmdbApiQueryResults != null && !tmdbApiQueryResults.equals("")) {
                JSONArray resultArray = getResultsArray(tmdbApiQueryResults);

                try {
                    setMovieArrayList(resultArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setItemsCount();
                /*
                 * Using findViewById, I get a reference to the RecyclerView from xml. This allows me to
                 * do things like set the adapter of the RecyclerView.
                 */
                mRecyclerView = (RecyclerView) findViewById(R.id.rv_mainframe);


                GridLayoutManager layoutManager = new GridLayoutManager(context, 4);

                mRecyclerView.setLayoutManager(layoutManager);

                mRecyclerView.setHasFixedSize(true);


                /*
                 * myAdapter is responsible for displaying each item in the grid.
                 */
                myAdapter = new RecycleviewAdapter(itemsCount, listener, movieArrayList, context);

                mRecyclerView.setAdapter(myAdapter); //link the recyclerView and te view adapter
            }
        }
    }
}
