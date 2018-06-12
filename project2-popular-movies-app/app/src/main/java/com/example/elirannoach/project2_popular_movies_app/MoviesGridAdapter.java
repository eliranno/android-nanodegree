package com.example.elirannoach.project2_popular_movies_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elirannoach.project2_popular_movies_app.data.Movie;
import com.example.elirannoach.project2_popular_movies_app.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MoviesGridAdapter extends ArrayAdapter<Movie> {
    List<Movie> mMovieList;


    public MoviesGridAdapter(Context context, int resource, List<Movie> movieList){
        super(context,resource,movieList);
        mMovieList = movieList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Gets the Movie object from the adapter at the appropriate position.
        Movie movie = mMovieList.get(position);
        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_grid_item,parent,false);
        }
        ImageView movieImage = (ImageView) convertView.findViewById(R.id.iv_movie_grid_poster);
        TextView movieTitle = (TextView) convertView.findViewById(R.id.tv_movie_title);
        movieTitle.setText(mMovieList.get(position).getTitle());
        String movieImagePath = movie.getImageRelativePath();
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme(NetworkUtils.SCHEME);
        uriBuilder.authority(NetworkUtils.AUTHORITY_IMAGE);
        uriBuilder.path(NetworkUtils.PATH_IMAGE+NetworkUtils.IMAGE_SIZE_BIG+movieImagePath);
        Picasso.with(getContext()).load(uriBuilder.toString()).into(movieImage);
        movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movie movie = mMovieList.get(position);
                Intent intent = new Intent(getContext(),MovieInfoActivity.class);
                intent.putExtra("movieObj",movie);
                getContext().startActivity(intent);


            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return mMovieList.size();
    }
}
