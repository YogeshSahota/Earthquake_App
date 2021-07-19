package com.example.android.earthquake;

import androidx.appcompat.app.AppCompatActivity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<Report>> {

    public final String Requestedurl = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=2&limit=30";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data// Get a reference to the LoaderManager, in order to interact with loaders.
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(1, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
             TextView mEmptyStateTextView = (TextView) findViewById(R.id.empty);
            mEmptyStateTextView.setText("No Internet Connection");}

    }
    private void updateUI(ArrayList<Report> earthquakes){
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        TextView mEmptyStateTextView = (TextView) findViewById(R.id.empty);
        earthquakeListView.setEmptyView(mEmptyStateTextView);
        mEmptyStateTextView.setText("No Earthquakes Found");
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Create a new {@link ArrayAdapter} of earthquakes
        ReportAdapter adapter = new ReportAdapter(
                this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Report r = earthquakes.get(position);

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(r.getmUrl()));
                startActivity(i);
            }
        });
    }



    @Override
    public Loader<ArrayList<Report>> onCreateLoader(int i, Bundle bundle) {
        return new EarthquakeLoader(MainActivity.this,Requestedurl);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Report>> loader, ArrayList<Report> earthquakes) {
        updateUI(earthquakes);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Report>> loader) {
        updateUI(new ArrayList<Report>());
    }
}