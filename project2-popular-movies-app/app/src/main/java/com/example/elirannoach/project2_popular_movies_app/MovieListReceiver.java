package com.example.elirannoach.project2_popular_movies_app;

import com.example.elirannoach.project2_popular_movies_app.data.Movie;

import java.util.List;

public interface MovieListReceiver {
    void handleNetworkError();
    void handleDataError();
    void handleData(List<Movie> movieList);
}
