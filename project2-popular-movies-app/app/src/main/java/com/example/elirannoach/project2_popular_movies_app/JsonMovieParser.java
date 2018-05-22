package com.example.elirannoach.project2_popular_movies_app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class JsonMovieParser {
    private JSONObject mJsonMovieData;


    public JsonMovieParser(String movieDataString) throws JSONException{
        try {
            mJsonMovieData = new JSONObject(movieDataString);
        }
        catch (JSONException e){
            throw e;

        }
    }

    public JsonMovieParser(JSONObject jsonMovieData){
        mJsonMovieData = jsonMovieData;
    }

    public List<Movie> parse(){
        List<Movie> parsedMovieList = new ArrayList<>();
        Movie.MovieBuilder movieBuilder = new Movie.MovieBuilder();
        JSONArray jsonMovieList = mJsonMovieData.optJSONArray(JsonMessageKeys.RESULTS);
        for(int index = 0; index < jsonMovieList.length();index++) {
            JSONObject jsonMovieRecord = (JSONObject) jsonMovieList.opt(index);
            movieBuilder.setVoteCount(jsonMovieRecord.optInt(JsonMessageKeys.VOTE_COUNT, 0));
            movieBuilder.setMovieId(jsonMovieRecord.optInt(JsonMessageKeys.ID,-1));
            movieBuilder.setIsVideoAvailable(jsonMovieRecord.optBoolean(JsonMessageKeys.VIDEO,false));
            movieBuilder.setAverageVote(jsonMovieRecord.optDouble(JsonMessageKeys.VOTE_AVERAGE,0.0));
            movieBuilder.setTitle(jsonMovieRecord.optString(JsonMessageKeys.TITLE,""));
            movieBuilder.setPopularity(jsonMovieRecord.optDouble(JsonMessageKeys.POPULARITY,0.0));
            movieBuilder.setPosterPath(jsonMovieRecord.optString(JsonMessageKeys.POSTER_PATH,""));
            movieBuilder.setOriginalLanguage(jsonMovieRecord.optString(JsonMessageKeys.ORIGINAL_LANG,""));
            movieBuilder.setOriginalTitle(jsonMovieRecord.optString(JsonMessageKeys.ORIGINAL_TITLE,""));
            JSONArray genreList = jsonMovieRecord.optJSONArray(JsonMessageKeys.GENRE_IDS);
            Map<Integer,Integer> genereMap = new Hashtable<>();
            for (int genereListIndex=0;genereListIndex<genreList.length();genereListIndex++){
                genereMap.put(genereListIndex,genreList.optInt(genereListIndex,0));
            }
            movieBuilder.setGenreMap(genereMap);
            movieBuilder.setBackDropPath(jsonMovieRecord.optString(JsonMessageKeys.BACKDROP_PATH,""));
            movieBuilder.setIsAdultMovie(jsonMovieRecord.optBoolean(JsonMessageKeys.ADULT,false));
            movieBuilder.setOverview(jsonMovieRecord.optString(JsonMessageKeys.OVERVIEW,""));
            movieBuilder.setReleaseDate(jsonMovieRecord.optString(JsonMessageKeys.RELEASE_DATE,""));
            parsedMovieList.add(movieBuilder.build());
        }

        return parsedMovieList;
    }


}
