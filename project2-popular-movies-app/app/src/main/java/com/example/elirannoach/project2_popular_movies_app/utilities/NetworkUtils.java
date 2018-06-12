package com.example.elirannoach.project2_popular_movies_app.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Scanner;

public class NetworkUtils {

    public static final String QUERY_KEY_TAG = "api_key";
    public static final String TAG = "NetworkUtils";
    public static final String KEY_VALUE  = "0d3fecc6c9418d69e5d5541e5f4d7d33";  //TODO : remove this from here
    public static final String IMAGE_SIZE_BIG = "/w185";
    public static final String SCHEME = "http";
    public static final String ATHORITY_MOVIE = "api.themoviedb.org";
    public static final String AUTHORITY_IMAGE = "image.tmdb.org";
    public static final String PATH_POPULAR_MOVIE = "/3/movie/popular";
    public static final String PATH_TOP_RATED = "/3/movie/top_rated";
    public static final String PATH_IMAGE = "/t/p";
    public static final String IMAGE_SIZE_SMALL = "/w154";
    private Context mContext;

    public NetworkUtils(Context context){
        mContext = context;
    }

    /**
     * This function construct a Uri object to be used to fetch data
     * from http://themoviebd.org/ website.
     * @param path - path to be used in the Uri
     * @param queryMap - a set of keys and values that will be used to in the query part of the Uri
     *@return new Uri object that contains an end-point
     */

    public Uri buildMovieUri(String path, Map<String,String> queryMap){
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme(SCHEME);
        uriBuilder.authority(ATHORITY_MOVIE);
        uriBuilder.path(path);
        for (Map.Entry<String,String> entry : queryMap.entrySet()){
            uriBuilder.appendQueryParameter(entry.getKey(),entry.getValue());
        }
        Uri movieUri = uriBuilder.build();
        Log.d(TAG,movieUri.toString());
        return movieUri;
    }

    /**
     * This function attempts to make HTTP connection to the end point specified
     * in the Uri object and retrieve the data in JSON format
     * @param url - URL object to connect
     *@return String that contains the response from the end-point.
     */

    public String makeHttpRequest(URL url) throws MalformedURLException,IOException{
        try {
            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();
            Scanner s = new Scanner(is).useDelimiter("//A");
            return s.hasNext() ? s.next() : "";
        }
        catch (MalformedURLException e ){
            throw e;
        }
        catch (IOException e){
            throw e;
        }
    }

    /**
     * This function construct a Uri object to be used to fetch data
     * from http://themoviebd.org/ website.
     * @param imageSize - size of the image
     * @param posterPath - relative path of the movie poster.
     *@return new Uri object to fetch the movie poster.
     */

    public Uri BuildImageUri(String imageSize,String posterPath){
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme(SCHEME);
        uriBuilder.authority(ATHORITY_MOVIE);
        uriBuilder.path(PATH_IMAGE+imageSize+posterPath);
        Uri movieUri = uriBuilder.build();
        Log.d(TAG,movieUri.toString());
        return movieUri;
    }

    public boolean isOnline(){
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();

    }
}


