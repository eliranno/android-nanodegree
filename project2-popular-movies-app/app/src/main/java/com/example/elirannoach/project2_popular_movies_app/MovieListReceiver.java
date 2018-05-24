package com.example.elirannoach.project2_popular_movies_app;

import java.util.List;

public interface MovieListReceiver {
    void handleNetworkError();
    void handleDataError();
    void handleData(List<Movie> movieList);
}
