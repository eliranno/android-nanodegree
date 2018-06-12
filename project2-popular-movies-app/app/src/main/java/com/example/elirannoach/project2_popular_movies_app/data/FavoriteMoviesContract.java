package com.example.elirannoach.project2_popular_movies_app.data;

import android.net.Uri;
import android.provider.BaseColumns;


public class FavoriteMoviesContract {

    public static final String AUTHORITY = "com.elirannoach.popular_movies_app";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final String FAVORITE_MOVIES_PATH  = "favorites";
    public static final String FAVORITE_SINGLE_MOVIE_PATH = "favorite";


    public static class FavoriteMovieTable implements BaseColumns {
        public static final Uri BASE_CONTENT_URI  = BASE_URI.buildUpon().appendPath(FAVORITE_MOVIES_PATH).build();
        public static final String TABLE_NAME = "favorites";
        public static final String MOVIE_ID = "movieId";
        public static final String MOVIE_TITLE = "title";

    }
}
