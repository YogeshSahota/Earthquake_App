package com.example.android.earthquake;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import android.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Report>> {

    String mEarthquakeurl;
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mEarthquakeurl = url;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Report> loadInBackground() {

        // Extract relevant fields from the JSON response and create an {@link Event} object
        ArrayList<Report> earthquake = QueryUtils.extractEarthquakes(mEarthquakeurl);

        // Return the {@link Event}
        return earthquake;

    }
}