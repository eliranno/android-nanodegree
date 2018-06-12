package com.example.elirannoach.project2_popular_movies_app.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.elirannoach.project2_popular_movies_app.Data.FavoriteMoviesContract.FavoriteMovieTable.BASE_CONTENT_URI;
import static com.example.elirannoach.project2_popular_movies_app.Data.FavoriteMoviesContract.FavoriteMovieTable.TABLE_NAME;


public class FavoriteMoviesContentProvider extends ContentProvider {
    private MovieDatabaseHelper mMovieDataBaseHelper;
    private static final int FAVORITE = 100;
    private static final int FAVORITE_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();



    @Override
    public boolean onCreate() {
        mMovieDataBaseHelper = new MovieDatabaseHelper(getContext());
        return true;
    }

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(FavoriteMoviesContract.AUTHORITY,FavoriteMoviesContract.FAVORITE_MOVIES_PATH,FAVORITE);
        uriMatcher.addURI(FavoriteMoviesContract.AUTHORITY,FavoriteMoviesContract.FAVORITE_MOVIES_PATH+"/#",FAVORITE_WITH_ID);
        return uriMatcher;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mMovieDataBaseHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (sUriMatcher.match(uri)){
            case FAVORITE:
                cursor = db.query(FavoriteMoviesContract.FavoriteMovieTable.TABLE_NAME,
                        null,null,
                        null,null,
                        null, null);
                break;
            case FAVORITE_WITH_ID:
                String movieId = uri.getLastPathSegment();
                String queryArg = FavoriteMoviesContract.FavoriteMovieTable.MOVIE_ID + "=?";
                cursor = db.query(FavoriteMoviesContract.FavoriteMovieTable.TABLE_NAME,
                        null,queryArg,
                        new String[]{movieId},null,
                        null, null);
                break;
                default:
                    //throw new UnsupportedOperationException("bad query");
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mMovieDataBaseHelper.getWritableDatabase();
        Uri returnUri = null;
        switch (sUriMatcher.match(uri)){
            case FAVORITE:
                long id = db.insert(FavoriteMoviesContract.FavoriteMovieTable.TABLE_NAME,null,values);
                if(id>0){
                    returnUri = ContentUris.withAppendedId(BASE_CONTENT_URI,id);
                }
                else{
                    throw new SQLException("failed to insert new row");
                }
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");

        }
        getContext().getContentResolver().notifyChange(uri,null);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mMovieDataBaseHelper.getWritableDatabase();
        int retVal = 0;
        switch(sUriMatcher.match(uri)){
            case FAVORITE:
                break;
            case FAVORITE_WITH_ID:
                String movieId = uri.getLastPathSegment();
                String queryArg = FavoriteMoviesContract.FavoriteMovieTable.MOVIE_ID + "=?";
                retVal = db.delete(FavoriteMoviesContract.FavoriteMovieTable.TABLE_NAME,queryArg,new String[]{movieId});
                break;
        }
        return retVal;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
