package com.example.elirannoach.project2_popular_movies_app.utilities;

import com.example.elirannoach.project2_popular_movies_app.data.MovieTrailerLink;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieTrailerParser {


    public static List<MovieTrailerLink> parse(final String jsonString){
        List<MovieTrailerLink> trailerList = new ArrayList<>();
        try {
            Gson gsonBuilder = new GsonBuilder().create();
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.optJSONArray("results");
            int size = jsonArray.length();
            for(int i=0;i<size;i++){
                MovieTrailerLink movieTrailerLink = gsonBuilder.fromJson(jsonArray.get(i).toString(),MovieTrailerLink.class);
                trailerList.add(movieTrailerLink);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailerList;

    }
}
