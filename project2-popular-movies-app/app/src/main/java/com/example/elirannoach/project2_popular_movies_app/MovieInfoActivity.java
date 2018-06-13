package com.example.elirannoach.project2_popular_movies_app;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.support.v4.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elirannoach.project2_popular_movies_app.data.FavoriteMoviesContract;
import com.example.elirannoach.project2_popular_movies_app.data.Movie;
import com.example.elirannoach.project2_popular_movies_app.data.MovieTrailerLink;
import com.example.elirannoach.project2_popular_movies_app.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.elirannoach.project2_popular_movies_app.utilities.NetworkUtils.ATHORITY_MOVIE;
import static com.example.elirannoach.project2_popular_movies_app.utilities.NetworkUtils.KEY_VALUE;
import static com.example.elirannoach.project2_popular_movies_app.utilities.NetworkUtils.QUERY_KEY_TAG;
import static com.example.elirannoach.project2_popular_movies_app.utilities.NetworkUtils.SCHEME;

public class MovieInfoActivity extends AppCompatActivity implements
        android.support.v4.app.LoaderManager.LoaderCallbacks<List<MovieTrailerLink>>{

    private TextView mMovieInfoTextMovie;
    private ImageView mMovieImage;
    private ImageView mFavoriteImage;
    private ContentResolver mContentResolver;
    private Movie mMovie;
    private RecyclerView mTrailersRecycleView;
    private static final int MOVIE_VIDEO_LIST_LOADER_ID = 15;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_info_activity);
        mMovieInfoTextMovie = findViewById(R.id.tv_movie_info);
        mMovieImage = findViewById(R.id.iv_movie_info_poster);
        mFavoriteImage = findViewById(R.id.iv_favorite);
        mTrailersRecycleView = findViewById(R.id.rv_movie_trailer_list);
        mMovie = getIntent().getExtras().getParcelable("movieObj");
        Picasso.with(this).load(getImageUri(mMovie)).into(mMovieImage);
        String infoText = createMovieInfoText(mMovie);
        mMovieInfoTextMovie.setText(infoText);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setupFavoriteButton();
        getSupportLoaderManager().initLoader(MOVIE_VIDEO_LIST_LOADER_ID, null, this);

    }

    public String createMovieInfoText(Movie movie) {
        return String.format("Movie Title: %s\r\n\n Release Date: %s\r\n\n" +
                        "Overview: %s \r\n\n Average Vote: %.2f \r", movie.getTitle(),
                movie.getReleaseDate(), movie.getOverview(), movie.getAverageVote());
    }

    public Uri getImageUri(Movie movie) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(NetworkUtils.SCHEME);
        builder.authority(NetworkUtils.AUTHORITY_IMAGE);
        builder.path(NetworkUtils.PATH_IMAGE + NetworkUtils.IMAGE_SIZE_BIG
                + movie.getImageRelativePath());
        return builder.build();
    }

    private boolean isFavoriteMovie() {
        String movieIdPath = String.valueOf(mMovie.getMovieId());
        Uri contentUri = FavoriteMoviesContract.FavoriteMovieTable.BASE_CONTENT_URI.buildUpon().appendPath(movieIdPath).build();
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor.moveToNext())
            return mMovie.getMovieId() == cursor.getInt(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovieTable.MOVIE_ID));
        else
            return false;
    }

    private int RemoveFavoriteMovieFromDb() {
        String movieIdPath = String.valueOf(mMovie.getMovieId());
        Uri contentUri = FavoriteMoviesContract.FavoriteMovieTable.BASE_CONTENT_URI.buildUpon().appendPath(movieIdPath).build();
        return getContentResolver().delete(contentUri, null, null);
    }

    private void initRecycleViews() {
        List<MovieTrailerLink> demoList = new ArrayList<>();
        //demoList.add(new MovieTrailerLink());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        MovieTrailerRecycleViewAdapter adapter = new MovieTrailerRecycleViewAdapter(this, demoList);
        mTrailersRecycleView.setLayoutManager(layoutManager);
        mTrailersRecycleView.setAdapter(adapter);
    }

    private void setupFavoriteButton() {
        if (isFavoriteMovie())
            Picasso.with(this).load(R.drawable.like_star).into(mFavoriteImage);
        else
            Picasso.with(this).load(R.drawable.not_like_star).into(mFavoriteImage);
        mFavoriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavoriteMovie()) {
                    ContentValues values = new ContentValues();
                    values.put(FavoriteMoviesContract.FavoriteMovieTable.MOVIE_TITLE, mMovie.getTitle());
                    values.put(FavoriteMoviesContract.FavoriteMovieTable.MOVIE_ID, mMovie.getMovieId());
                    Uri resultUri = getContentResolver().insert(FavoriteMoviesContract.FavoriteMovieTable.BASE_CONTENT_URI, values);
                    Picasso.with(getApplicationContext()).load(R.drawable.like_star).into(mFavoriteImage);
                    if (resultUri != null) {
                        //Toast.makeText(this,R.string.favorite_insert_successfull,Toast.LENGTH_SHORT).show();
                    }
                } else {
                    final int status = RemoveFavoriteMovieFromDb();
                    Picasso.with(getApplicationContext()).load(R.drawable.not_like_star).into(mFavoriteImage);
                }
            }
        });
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        try {
            Uri.Builder uriBuilder = new Uri.Builder();
            Uri uri = uriBuilder.scheme(SCHEME).authority(ATHORITY_MOVIE).
                    path("3/movie/" + mMovie.getMovieId() + "/videos").
                    appendQueryParameter(QUERY_KEY_TAG,KEY_VALUE).build();
            return new MovieTrailerLinkLoader(this, new URL(uri.toString()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<MovieTrailerLink>> loader, List<MovieTrailerLink> data) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        MovieTrailerRecycleViewAdapter adapter = new MovieTrailerRecycleViewAdapter(this,data);
        mTrailersRecycleView.setLayoutManager(layoutManager);
        mTrailersRecycleView.setAdapter(adapter);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<MovieTrailerLink>> loader) {

    }
}
