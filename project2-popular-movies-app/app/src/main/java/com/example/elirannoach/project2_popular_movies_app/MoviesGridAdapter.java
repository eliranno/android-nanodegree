package com.example.elirannoach.project2_popular_movies_app;

import android.content.Context;
import android.media.Image;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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
        Picasso.with(getContext()).load(R.drawable.test_image).placeholder(R.drawable.test_image).into(movieImage);
        return convertView;



    }

    @Override
    public int getCount() {
        return mMovieList.size();
    }

    public static class MovieViewHolder{
        ImageView imageView;
    }
}
