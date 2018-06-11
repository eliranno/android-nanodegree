package com.example.elirannoach.project2_popular_movies_app;

import android.icu.text.ListFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class JsonMovieListParser {
    private JSONObject mJsonMovieData;


    public JsonMovieListParser(String movieDataString) throws JSONException{
        try {
            mJsonMovieData = new JSONObject(movieDataString);
        }
        catch (JSONException e){
            throw e;

        }
    }

    public JsonMovieListParser(JSONObject jsonMovieData){
        mJsonMovieData = jsonMovieData;
    }

    private Movie parseSingleMovie(JSONObject jsonMovieObject){
        Movie.MovieBuilder movieBuilder = new Movie.MovieBuilder();
        movieBuilder.setVoteCount(jsonMovieObject.optInt(JsonMessageKeys.VOTE_COUNT, 0));
        movieBuilder.setMovieId(jsonMovieObject.optInt(JsonMessageKeys.ID,-1));
        movieBuilder.setIsVideoAvailable(jsonMovieObject.optBoolean(JsonMessageKeys.VIDEO,false));
        movieBuilder.setAverageVote(jsonMovieObject.optDouble(JsonMessageKeys.VOTE_AVERAGE,0.0));
        movieBuilder.setTitle(jsonMovieObject.optString(JsonMessageKeys.TITLE,""));
        movieBuilder.setPopularity(jsonMovieObject.optDouble(JsonMessageKeys.POPULARITY,0.0));
        movieBuilder.setPosterPath(jsonMovieObject.optString(JsonMessageKeys.POSTER_PATH,""));
        movieBuilder.setOriginalLanguage(jsonMovieObject.optString(JsonMessageKeys.ORIGINAL_LANG,""));
        movieBuilder.setOriginalTitle(jsonMovieObject.optString(JsonMessageKeys.ORIGINAL_TITLE,""));
        JSONArray genreList = jsonMovieObject.optJSONArray(JsonMessageKeys.GENRE_IDS);
        if(genreList!=null) {
            Map<Integer, Integer> genereMap = new Hashtable<>();
            for (int genereListIndex = 0; genereListIndex < genreList.length(); genereListIndex++) {
                genereMap.put(genereListIndex, genreList.optInt(genereListIndex, 0));
            }
            movieBuilder.setGenreMap(genereMap);
        }
        movieBuilder.setBackDropPath(jsonMovieObject.optString(JsonMessageKeys.BACKDROP_PATH,""));
        movieBuilder.setIsAdultMovie(jsonMovieObject.optBoolean(JsonMessageKeys.ADULT,false));
        movieBuilder.setOverview(jsonMovieObject.optString(JsonMessageKeys.OVERVIEW,""));
        movieBuilder.setReleaseDate(jsonMovieObject.optString(JsonMessageKeys.RELEASE_DATE,""));
    return movieBuilder.build();
    }

    public boolean isMovieList(){
        return mJsonMovieData.has(JsonMessageKeys.RESULTS);
    }

    public List<Movie> parse(){
        List<Movie> movieList = new ArrayList<>();
        if(isMovieList()){
            JSONArray jsonMovieList = mJsonMovieData.optJSONArray(JsonMessageKeys.RESULTS);
            for(int index = 0; index < jsonMovieList.length();index++)
                movieList.add(parseSingleMovie((JSONObject) jsonMovieList.opt(index)));
        }
        else
            movieList.add(parseSingleMovie(mJsonMovieData));
        return movieList;
    }




}
