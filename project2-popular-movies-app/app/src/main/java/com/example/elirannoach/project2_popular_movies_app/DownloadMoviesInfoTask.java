package com.example.elirannoach.project2_popular_movies_app;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class DownloadMoviesInfoTask extends AsyncTask<URL,Void,String> {

    private Context mContext;
    private MovieListReceiver mMovieListReceiver;

    public DownloadMoviesInfoTask(Context context,MovieListReceiver movieListReceiver){
        super();
        mContext = context;
        mMovieListReceiver = movieListReceiver;
    }

    @Override
    protected String doInBackground (URL... urls)  {
        String moviesJsonString = "";
        try {
            NetworkUtils networkUtils = new NetworkUtils(mContext);
            moviesJsonString = networkUtils.makeHttpRequest(urls[0]);
        }
        catch (IOException e){
            mMovieListReceiver.handleNetworkError();
        }
        return moviesJsonString;
    }

    @Override
    protected void onPostExecute(String moviesJsonString) {
        try{
            if (moviesJsonString !=null && !moviesJsonString.equals("")) {
                JsonMovieParser MovieParser = new JsonMovieParser(moviesJsonString);
                List<Movie> movieList = MovieParser.parse();
                mMovieListReceiver.handleData(movieList);
            }
        }
        catch (JSONException e){
            mMovieListReceiver.handleDataError();
        }
    }
}
