package com.example.elirannoach.project2_popular_movies_app;

import java.util.List;

public interface MovieListHandler {
    void handleConnectionError();
    void handleProcessingDataError();
    void handleData(List<Movie> movieList);
}
