package com.example.elirannoach.bakingapp.sync;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.elirannoach.bakingapp.data.Recipe;
import com.example.elirannoach.bakingapp.ui.RecipeListFragment;
import com.example.elirannoach.bakingapp.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AsyncRecipeLoader extends android.content.AsyncTaskLoader<List<Recipe>> {
    private Context mContext;
    private List<Recipe> mRecipeList;

    public AsyncRecipeLoader(Context context) {
        super(context);
        mContext = context;
        mRecipeList = null;
    }

    @Override
    public void deliverResult(@Nullable List<Recipe> data) {
        super.deliverResult(data);
        mRecipeList = data;
    }

    @Override
    protected void onStartLoading() {
        if(mRecipeList !=null){
            deliverResult(mRecipeList);
        }else{
            forceLoad();
        }
    }

    @Nullable
    @Override
    public List<Recipe> loadInBackground() {
        try {
            URL url = new URL(NetworkUtils.BAKING_RECIPE_URL_STRING);
            JSONArray jsonData = new JSONArray(NetworkUtils.getUrlData(url));
            Gson gson = new GsonBuilder().create();
            List<Recipe> recipeList = new ArrayList<>();
            for (int i=0;i<jsonData.length();i++){
                recipeList.add(gson.fromJson(jsonData.get(i).toString(),Recipe.class));
            }
            return recipeList;
        }
        catch (MalformedURLException|JSONException e){
            e.printStackTrace();
            return null;
        }

    }
}
