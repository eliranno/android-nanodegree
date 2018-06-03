package com.example.elirannoach.project2_popular_movies_app.Data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.elirannoach.project2_popular_movies_app.Movie;

public class MovieDatabaseController {
    private SQLiteDatabase movieDb;

    public MovieDatabaseController(SQLiteDatabase db){
        this.movieDb = db;
    }

    public boolean insertMovieIntoFavoriteTable(Movie movie){
        final String INSERT_STATEMENT = "INSERT INTO " + FavoriteMovieTable.TABLE_NAME
                + "VALUES " +"(" + movie.getTitle() +")";
        movieDb.execSQL(INSERT_STATEMENT) ;
        return true;
    }

    public boolean deleteMovieFromFavoriteTable(Movie movie){
        final String DELETE_STATEMENT = "DELETE FROM " + FavoriteMovieTable.TABLE_NAME
                + "WHERE " + FavoriteMovieTable.MOVIE_TITLE + "=" + movie.getTitle();
        movieDb.execSQL(DELETE_STATEMENT);
        return true;
    }

    public boolean deleteMovieTable(){
        final String DELETE_STATEMENT = "DELETE * FROM " + FavoriteMovieTable.TABLE_NAME;
        movieDb.execSQL(DELETE_STATEMENT);
        return true;
    }
}
