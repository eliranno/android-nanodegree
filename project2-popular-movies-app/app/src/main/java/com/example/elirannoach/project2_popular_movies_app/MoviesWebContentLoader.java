package com.example.elirannoach.project2_popular_movies_app;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MoviesWebContentLoader extends AsyncTaskLoader<List<Movie>> {
    URL mUrl;
    Context mContext;
    List<Movie> mMovieslist;

    public MoviesWebContentLoader(Context context, URL mUrl) {
        super(context);
        this.mUrl = mUrl;
        this.mContext = context;
    }

    public MoviesWebContentLoader(Context context) {
        super(context);
        this.mContext = context;
        try {
            this.mUrl = new URL("");
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStartLoading() {
        if(mMovieslist !=null && mMovieslist.size()>0){
            deliverResult(mMovieslist);
        }
        else{
            forceLoad();
        }
    }

    @Override
    public List<Movie> loadInBackground() {
        String moviesJsonString = "";
        List<Movie> list;
        try {
            NetworkUtils networkUtils = new NetworkUtils(mContext);
            moviesJsonString = networkUtils.makeHttpRequest(mUrl);
        }
        catch (IOException e){
            return null;
        }
        return processJsonMsg(moviesJsonString);
    }

    @Override
    public void deliverResult(List<Movie> data) {
        mMovieslist = data;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        mMovieslist = null;
        super.onReset();
    }

    protected List<Movie> processJsonMsg(String moviesJsonString) {
        try{
            if (moviesJsonString !=null && !moviesJsonString.equals("")) {
                JsonMovieParser MovieParser = new JsonMovieParser(moviesJsonString);
                List<Movie> movieList = MovieParser.parse();
                return movieList;
            }
            else{
                return null;
            }
        }
        catch (JSONException e){
            return null;
        }
    }
}
