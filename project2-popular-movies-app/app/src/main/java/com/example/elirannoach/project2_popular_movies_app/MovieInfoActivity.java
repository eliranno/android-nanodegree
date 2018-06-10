package com.example.elirannoach.project2_popular_movies_app;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elirannoach.project2_popular_movies_app.Data.FavoriteMoviesContract;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MovieInfoActivity extends AppCompatActivity {

    private TextView mMovieInfoTextMovie;
    private ImageView mMovieImage;
    private  ImageView mFavoriteImage;
    private ContentResolver mContentResolver;
    private Movie mMovie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_info_activity);
        mMovieInfoTextMovie = findViewById(R.id.tv_movie_info);
        mMovieImage = findViewById(R.id.iv_movie_info_poster);
        mFavoriteImage = findViewById(R.id.iv_favorite);
        mMovie = getIntent().getExtras().getParcelable("movieObj");
        Picasso.with(this).load(getImageUri(mMovie)).into(mMovieImage);
        Picasso.with(this).load(R.drawable.like_star).into(mFavoriteImage);
        String infoText = createMovieInfoText(mMovie);
        mMovieInfoTextMovie.setText(infoText);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mFavoriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(FavoriteMoviesContract.FavoriteMovieTable.MOVIE_TITLE,mMovie.getTitle());
                values.put(FavoriteMoviesContract.FavoriteMovieTable.MOVIE_ID,mMovie.getMovieId());
                Uri resultUri = getContentResolver().insert(FavoriteMoviesContract.FavoriteMovieTable.BASE_CONTENT_URI,values);
                if(resultUri!=null){
                //Toast.makeText(this,R.string.favorite_insert_successfull,Toast.LENGTH_SHORT).show();
            }
        }
    });
    }

    public String createMovieInfoText(Movie movie){
        return String.format("Movie Title: %s\r\n\n Release Date: %s\r\n\n" +
                "Overview: %s \r\n\n Average Vote: %.2f \r\n\n",movie.getTitle(),
                movie.getReleaseDate(),movie.getOverview(),movie.getAverageVote());
    }

    public Uri getImageUri(Movie movie){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(NetworkUtils.SCHEME);
        builder.authority(NetworkUtils.AUTHORITY_IMAGE);
        builder.path(NetworkUtils.PATH_IMAGE+NetworkUtils.IMAGE_SIZE_BIG
                +movie.getImageRelativePath());
        return builder.build();


    }
}
