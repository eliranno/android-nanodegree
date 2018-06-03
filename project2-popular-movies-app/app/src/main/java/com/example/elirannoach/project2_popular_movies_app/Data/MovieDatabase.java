package com.example.elirannoach.project2_popular_movies_app.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movie_app.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_FAVORTIE_TABLE = "CREATE TABLE " + FavoriteMovieTable.TABLE_NAME + "("
                                                        + FavoriteMovieTable.MOVIE_ID+ " INTEGER PRIMARY KEY,"
                                                        + FavoriteMovieTable.MOVIE_TITLE + " TEXT"
                                                        + ")";

    public MovieDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FAVORTIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
