package com.example.elirannoach.project2_popular_movies_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewManager;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MovieListHandler {

    Toast mToast;
    GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToast = new Toast(this);
        mGridView = (GridView) findViewById(R.id.gl_movie_id);
        Map<String,String> queryMap = new Hashtable<>();
        queryMap.put(NetworkUtils.QUERY_KEY_TAG,NetworkUtils.KEY_VALUE);
        try {
            URL popularMoviesUrl = new URL(new NetworkUtils(this).buildMovieUri(NetworkUtils.PATH_POPULAR_MOVIE, queryMap).toString());
            DownloadMoviesInfoTask task = new DownloadMoviesInfoTask(this,this);
            task.execute(popularMoviesUrl);
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
}
