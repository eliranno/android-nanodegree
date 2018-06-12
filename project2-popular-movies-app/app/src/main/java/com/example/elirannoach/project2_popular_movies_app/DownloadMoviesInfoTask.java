package com.example.elirannoach.project2_popular_movies_app;

import android.content.Context;
import android.os.AsyncTask;

import com.example.elirannoach.project2_popular_movies_app.data.Movie;
import com.example.elirannoach.project2_popular_movies_app.utilities.JsonMovieListParser;
import com.example.elirannoach.project2_popular_movies_app.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class DownloadMoviesInfoTask extends AsyncTask<URL,Void,String> {

    private Context mContext;
    private MovieListReceiver mMovieListReceiver;
    private boolean mRequestFlag;

    public DownloadMoviesInfoTask(Context context,MovieListReceiver movieListReceiver){
        super();
        mContext = context;
        mMovieListReceiver = movieListReceiver;
        mRequestFlag = false;

    }

    @Override
    protected String doInBackground (URL... urls)  {
        String moviesJsonString = "";
        try {
            NetworkUtils networkUtils = new NetworkUtils(mContext);
            moviesJsonString = networkUtils.makeHttpRequest(urls[0]);
            mRequestFlag = true;
        }
        catch (IOException e){
            mRequestFlag = false;
        }
        return moviesJsonString;
    }

    @Override
    protected void onPostExecute(String moviesJsonString) {
        try{
            if (moviesJsonString !=null && !moviesJsonString.equals("") && mRequestFlag) {
                JsonMovieListParser MovieParser = new JsonMovieListParser(moviesJsonString);
                List<Movie> movieList = MovieParser.parse();
                mMovieListReceiver.handleData(movieList);
            }
            else{
                mMovieListReceiver.handleNetworkError();
            }
        }
        catch (JSONException e){
            mMovieListReceiver.handleDataError();
        }
    }
}
