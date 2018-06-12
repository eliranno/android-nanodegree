package com.example.elirannoach.project2_popular_movies_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elirannoach.project2_popular_movies_app.data.MovieTrailerLink;

import java.util.List;

public class MovieTrailerRecycleViewAdapter extends RecyclerView.Adapter<MovieTrailerRecycleViewAdapter.MovieTrailerLinkViewHolder> {
    private Context mContent;
    private List<MovieTrailerLink> movieTrailerLinkList;
    public MovieTrailerRecycleViewAdapter(Context context ,List<MovieTrailerLink> list) {
        mContent = context;
        movieTrailerLinkList = list;
    }

    @NonNull
    @Override
    public MovieTrailerLinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View trailerPlayView = layoutInflater.inflate(R.layout.movie_trailer_view,parent,false);
        return new MovieTrailerLinkViewHolder(trailerPlayView);

    }

    @Override
    public void onBindViewHolder(@NonNull MovieTrailerLinkViewHolder holder, int position) {
        holder.trailerText.setText("Trailer "+String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return movieTrailerLinkList.size();
    }

    public class MovieTrailerLinkViewHolder extends RecyclerView.ViewHolder{
        TextView trailerText;
        ImageView playIcon;
        public MovieTrailerLinkViewHolder(View itemView) {
            super(itemView);
            trailerText = (TextView) itemView.findViewById(R.id.tv_movie_trailer_name);
            playIcon = (ImageView) itemView.findViewById(R.id.iv_trailer_play_icon);
        }
    }
}
