package com.example.android.popularmovies;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

public class FetchMyDataTask extends AsyncTask<URL, Void, String> {
    private static final String TAG = "FetchMyDataTask";

    private Context context;
    private AsyncTaskCompleteListener<String> listener;

    public FetchMyDataTask(Context ctx, AsyncTaskCompleteListener<String> listener) {
        this.context = ctx;
        this.listener = listener;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(URL... params) {
        URL searchUrl = params[0];
        String tmdbApiQueryResults = null;
        try {
            publishProgress();
            tmdbApiQueryResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmdbApiQueryResults;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        listener.onTaskComplete(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
