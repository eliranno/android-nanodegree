package com.example.elirannoach.project2_popular_movies_app;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements MovieListHandler {

    Toast mToast;
    GridView mGridView;
    NetworkUtils mNetworkUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToast = new Toast(this);
        mGridView = (GridView) findViewById(R.id.gl_movie_id);
        mNetworkUtils = new NetworkUtils(this);
        populateUI(SortCategories.POPULAR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_view, menu);
        MenuItem item = menu.findItem(R.id.sort_by_spinner);
        Spinner spinner = (Spinner) item.getActionView();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return true;
    }


    private void populateUI(SortCategories category){
        Map<String,String> queryMap = new Hashtable<>();
        queryMap.put(NetworkUtils.QUERY_KEY_TAG,NetworkUtils.KEY_VALUE);
        DownloadMoviesInfoTask task = new DownloadMoviesInfoTask(this,this);
        Uri uri;
        URL url;
        try{
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
            url = new URL(uri.toString());
            task.execute(url);
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void handleConnectionError() {
        mToast.setText(R.string.connection_error);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    public void handleProcessingDataError() {
        mToast.setText(R.string.process_data_error);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    public void handleData(List<Movie> movieList) {
        MoviesGridAdapter adapter = new MoviesGridAdapter(this,0,movieList);
        mGridView.setAdapter(adapter);


    }

    public enum SortCategories{
        POPULAR,
        TOP_RATED
    }
}
