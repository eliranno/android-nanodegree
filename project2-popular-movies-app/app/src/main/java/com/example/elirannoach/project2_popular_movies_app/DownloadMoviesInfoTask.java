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

    public DownloadMoviesInfoTask(Context context){
        mContext = context;
    }

    @Override
    protected String doInBackground (URL... urls)  {
        String moviesJsonString = "";
        try {
            NetworkUtils networkUtils = new NetworkUtils(mContext);
            moviesJsonString = networkUtils.makeHttpRequest(urls[0]);
        }
        catch (IOException e){
            Toast toast = Toast.makeText(mContext,R.string.connection_error,Toast.LENGTH_SHORT);
            toast.show();
        }
        return moviesJsonString;
    }

    @Override
    protected void onPostExecute(String moviesJsonString) {
        try{
            if (moviesJsonString !=null && !moviesJsonString.equals("")) {
                JsonMovieParser MovieParser = new JsonMovieParser(moviesJsonString);
                List<Movie> movieList = MovieParser.parse();

            }
        }
        catch (JSONException e){
            Toast toast = Toast.makeText(mContext,R.string.process_data_error,Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
