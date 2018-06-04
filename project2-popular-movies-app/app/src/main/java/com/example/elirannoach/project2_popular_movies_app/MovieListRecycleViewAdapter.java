package com.example.elirannoach.project2_popular_movies_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListRecycleViewAdapter extends RecyclerView.Adapter<MovieListRecycleViewAdapter.MovieViewHolder> {

    private List<Movie> mMovieList;
    private Context mContext;

    public MovieListRecycleViewAdapter(Context context, List<Movie> movieList){
        super();
        mMovieList = movieList;
        mContext = context;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView mMovieTitle;
        ImageView mMovieImage;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mMovieTitle = (TextView) itemView.findViewById(R.id.tv_movie_title);
            mMovieImage = (ImageView) itemView.findViewById(R.id.iv_movie_grid_poster);
        }
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.movie_grid_item, parent, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(v);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
        ImageView movieImage = holder.mMovieImage;
        TextView movieTitle = holder.mMovieTitle;
        movieTitle.setText(mMovieList.get(position).getTitle());
        Movie movie = mMovieList.get(position);
        String movieImagePath = movie.getImageRelativePath();
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme(NetworkUtils.SCHEME);
        uriBuilder.authority(NetworkUtils.AUTHORITY_IMAGE);
        uriBuilder.path(NetworkUtils.PATH_IMAGE+NetworkUtils.IMAGE_SIZE_BIG+movieImagePath);
        Picasso.with(mContext).load(uriBuilder.toString()).into(movieImage);
        movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movie movie = mMovieList.get(position);
                Intent intent = new Intent(mContext, MovieInfoActivity.class);
                intent.putExtra("movieObj", movie);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }
}
