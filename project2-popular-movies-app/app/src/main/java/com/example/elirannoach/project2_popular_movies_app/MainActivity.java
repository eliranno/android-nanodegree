package com.example.elirannoach.project2_popular_movies_app;


import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static android.support.v7.widget.RecyclerView.*;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    Toast mToast;
    RecyclerView mRecycleView;
    NetworkUtils mNetworkUtils;
    private static final int LOADER_UNIQUE_ID = 1;
    private Loader<List<Movie>> mMoviesWebContentLoader;
    private MovieListRecycleViewAdapter mViewAdapter;
    private SortCategories mSelectedCategory;
    private static final int COLUMNS_NUM = 4;

    public enum SortCategories{
        POPULAR,
        TOP_RATED
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToast = new Toast(this);
        mRecycleView = (RecyclerView) findViewById(R.id.gl_movie_id);
        mRecycleView.setLayoutManager(new GridLayoutManager(this,COLUMNS_NUM));
        mNetworkUtils = new NetworkUtils(this);
        mSelectedCategory = savedInstanceState != null ?
                SortCategories.valueOf(savedInstanceState.getString("category")) : SortCategories.POPULAR;
        populateUI(mSelectedCategory);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_view, menu);
        MenuItem item = menu.findItem(R.id.sort_by_spinner);
        Spinner spinner = (Spinner) item.getActionView();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_categories, R.layout.sort_by_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelected(false);  // must.
        spinner.setSelection(mSelectedCategory.ordinal(),true);  //must
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO : is there a way to restart the module that will now have a bundle with different data ?
                getSupportLoaderManager().destroyLoader(LOADER_UNIQUE_ID);
                populateUI(SortCategories.values()[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return true;
    }

    /**
     * This function crafts the URL message and uses the NetworkUtils to send
     * HTTP request to get the movie list. it will indirectly invoke one of the
     * MovieListReceiver interface methods
     * from http://themoviebd.org/ website.
     * @param category enum - one of the availavle categories to display
     *@return void
     */

    private void populateUI(SortCategories category) {
        Map<String, String> queryMap = new Hashtable<>();
        queryMap.put(NetworkUtils.QUERY_KEY_TAG, NetworkUtils.KEY_VALUE);
        Uri uri;
        switch (category) {
            case POPULAR:
                uri = mNetworkUtils.buildMovieUri(NetworkUtils.PATH_POPULAR_MOVIE, queryMap);
                break;
            case TOP_RATED:
                uri = mNetworkUtils.buildMovieUri(NetworkUtils.PATH_TOP_RATED, queryMap);
                break;
            default:
                uri = mNetworkUtils.buildMovieUri(NetworkUtils.PATH_POPULAR_MOVIE, queryMap);
                break;
        }
        Bundle bundle = new Bundle();
        bundle.putString("uri_string", uri.toString());
        mSelectedCategory = category;
        mMoviesWebContentLoader = getSupportLoaderManager().initLoader(LOADER_UNIQUE_ID, bundle, this);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
            try {
                URL url = new URL(args.getString("uri_string"));
                return new MoviesWebContentLoader(this, url);
            } catch (MalformedURLException e) {
                Log.e("MAIN_ACTIVITY", "no url was set");
                return new MoviesWebContentLoader(this);
            }
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        mViewAdapter = new MovieListRecycleViewAdapter(this,data);
        mRecycleView.setAdapter(mViewAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("category",mSelectedCategory.name());
        super.onSaveInstanceState(outState);

    }

    //TODO: use mToast to display error messages
}
