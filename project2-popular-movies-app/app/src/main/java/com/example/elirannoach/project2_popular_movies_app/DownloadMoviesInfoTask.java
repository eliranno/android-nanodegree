package com.example.elirannoach.project2_popular_movies_app;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class DownloadMoviesInfoTask extends AsyncTask<URL,Void,String> {

    private Context mContext;
    private MovieListHandler mMovieListHandler;

    public DownloadMoviesInfoTask(Context context,MovieListHandler movieListHandler){
        super();
        mContext = context;
        mMovieListHandler = movieListHandler;
    }

    @Override
    protected String doInBackground (URL... urls)  {
        String moviesJsonString = "";
        try {
            NetworkUtils networkUtils = new NetworkUtils(mContext);
            moviesJsonString = networkUtils.makeHttpRequest(urls[0]);
        }
        catch (IOException e){
            mMovieListHandler.handleConnectionError();
        }
        return moviesJsonString;
    }

    @Override
    protected void onPostExecute(String moviesJsonString) {
        try{
            if (moviesJsonString !=null && !moviesJsonString.equals("")) {
                JsonMovieParser MovieParser = new JsonMovieParser(moviesJsonString);
                List<Movie> movieList = MovieParser.parse();
                mMovieListHandler.handleData(movieList);
            }
        }
        catch (JSONException e){
            mMovieListHandler.handleProcessingDataError();
        }
    }
}
