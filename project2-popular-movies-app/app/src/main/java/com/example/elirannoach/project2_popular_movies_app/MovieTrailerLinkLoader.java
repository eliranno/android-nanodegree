package com.example.elirannoach.project2_popular_movies_app;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.example.elirannoach.project2_popular_movies_app.data.MovieTrailerLink;
import com.example.elirannoach.project2_popular_movies_app.utilities.MovieTrailerParser;
import com.example.elirannoach.project2_popular_movies_app.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MovieTrailerLinkLoader extends AsyncTaskLoader<List<MovieTrailerLink>> {

    private URL mUrl;
    private  Context mContext;
    private List<MovieTrailerLink> mMovieTrailerList;
    public MovieTrailerLinkLoader(Context context, URL url) {
        super(context);
        mContext = context;
        mUrl = url;
        mMovieTrailerList = null;
    }

    @Override
    public List<MovieTrailerLink> loadInBackground() {
        NetworkUtils networkUtils = new NetworkUtils(mContext);
        try {
            String dataString = networkUtils.makeHttpRequest(mUrl);
            List<MovieTrailerLink> trailerList = MovieTrailerParser.parse(dataString);
            return trailerList;
        }
        catch (IOException e ){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deliverResult(List<MovieTrailerLink> data) {
        mMovieTrailerList = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if(mMovieTrailerList!=null && mMovieTrailerList.size()>0){
            deliverResult(mMovieTrailerList);
        }
        else {
            forceLoad();
        }
    }
}
