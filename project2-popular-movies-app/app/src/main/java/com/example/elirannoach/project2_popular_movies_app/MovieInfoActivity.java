package com.example.elirannoach.project2_popular_movies_app;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MovieInfoActivity extends AppCompatActivity {

    private TextView mMovieInfoTextMovie;
    private ImageView mMovieImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_info_activity);
        Movie movie = getIntent().getExtras().getParcelable("movieObj");
        mMovieInfoTextMovie = findViewById(R.id.tv_movie_info);
        mMovieImage = findViewById(R.id.iv_movie_info_poster);
        Picasso.with(this).load(getImageUri(movie)).into(mMovieImage);
        String infoText = createMovieInfoText(movie);
        mMovieInfoTextMovie.setText(infoText);
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
