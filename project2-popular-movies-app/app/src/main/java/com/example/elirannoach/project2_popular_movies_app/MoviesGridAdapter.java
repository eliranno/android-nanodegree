package com.example.elirannoach.project2_popular_movies_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;


public class MoviesGridAdapter extends ArrayAdapter<Movie> {


    public MoviesGridAdapter(Context context, int resource, List<Movie> movieList){
        super(context,resource,movieList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        //return createViewFromResource(inflater,position,convertView,parent,R.layout.movie_grid_item);
        //mInflater, position, convertView, parent, mResource
        return super.getView(position, convertView, parent);
    }
}
