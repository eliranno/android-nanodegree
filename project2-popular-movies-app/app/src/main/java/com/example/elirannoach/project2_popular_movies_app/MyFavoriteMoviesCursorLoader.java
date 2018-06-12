package com.example.elirannoach.project2_popular_movies_app;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;

public class MyFavoriteMoviesCursorLoader extends CursorLoader{

    Cursor mCursor;

    public MyFavoriteMoviesCursorLoader(@NonNull Context context) {
        super(context);
        mCursor = null;
    }

    public MyFavoriteMoviesCursorLoader(@NonNull Context context, @NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
        this.mCursor = null;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = getContext().getContentResolver().query(getUri(),
                null,null,null);
        return cursor;


    }

    @Override
    public void deliverResult(Cursor cursor) {
        mCursor = cursor;
        super.deliverResult(cursor);
    }

    @Override
    protected void onStartLoading() {
        if (mCursor!=null && mCursor.getCount()>0){
            deliverResult(mCursor);
        }
        else{
            forceLoad();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
    }
}
