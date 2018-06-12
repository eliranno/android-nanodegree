package com.example.elirannoach.project2_popular_movies_app;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.example.elirannoach.project2_popular_movies_app.data.Movie;
import com.example.elirannoach.project2_popular_movies_app.utilities.JsonMovieListParser;
import com.example.elirannoach.project2_popular_movies_app.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MoviesWebContentLoader extends AsyncTaskLoader<List<Movie>> {
    List<URL> mUrlList;
    Context mContext;
    List<Movie> mMovieslist;

    public MoviesWebContentLoader(Context context, URL url) {
        super(context);
        this.mUrlList = new ArrayList<URL>();
        this.mUrlList.add(url);
        this.mContext = context;
    }

    public MoviesWebContentLoader(Context context, List<URL> urlList) {
        super(context);
        this.mUrlList = urlList;
        this.mContext = context;
    }

    public MoviesWebContentLoader(Context context) {
        super(context);
        this.mContext = context;
        this.mUrlList = new ArrayList<URL>();
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
        List<Movie> list = new ArrayList<>();
        for (URL url : mUrlList) {
            try {
                NetworkUtils networkUtils = new NetworkUtils(mContext);
                moviesJsonString = networkUtils.makeHttpRequest(url);
                list.addAll(processJsonMsg(moviesJsonString));
            } catch (IOException e) {
                return null;
            }
        }
        return list;
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
                JsonMovieListParser MovieParser = new JsonMovieListParser(moviesJsonString);
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
