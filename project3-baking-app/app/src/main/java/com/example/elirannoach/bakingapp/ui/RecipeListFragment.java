package com.example.elirannoach.bakingapp.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.elirannoach.bakingapp.R;
import com.example.elirannoach.bakingapp.adapters.RecipeListRecycleViewAdapter;
import com.example.elirannoach.bakingapp.data.Recipe;
import com.example.elirannoach.bakingapp.sync.AsyncRecipeLoader;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment implements android.app.LoaderManager.LoaderCallbacks<List<Recipe>> {
    @BindView(R.id.rv_recipe_list_id)
    RecyclerView mRecipeListRecycleView;
    private static final int RECIPE_LIST_ASYNC_LOADER_ID = 3;
    private OnRecipeClickListener mOnRecipeClickListerner;

    // this idling resource will be used by Espresso to wait for and synchronize with RetroFit Network call
    CountingIdlingResource espressoTestIdlingResource = new CountingIdlingResource("Network_Call");

    public interface OnRecipeClickListener{
        void onRecipeClicked(Recipe recipe);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnRecipeClickListerner = (OnRecipeClickListener) context;
        }
        catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_list_fragment,container,false);
        ButterKnife.bind(this,rootView);
        // increment idling resource for telling Espresso wait for the RetroFit network's call
        espressoTestIdlingResource.increment();
        getLoaderManager().initLoader(RECIPE_LIST_ASYNC_LOADER_ID,null,this);
        return  rootView;




    }

    @Override
    public android.content.Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new AsyncRecipeLoader(getActivity());
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<Recipe>> loader, List<Recipe> data) {
        RecyclerView.LayoutManager llm = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecipeListRecycleView.setLayoutManager(llm);
        mRecipeListRecycleView.setAdapter(new RecipeListRecycleViewAdapter(data,getActivity(),mOnRecipeClickListerner));
        espressoTestIdlingResource.decrement();
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<Recipe>> loader) {

    }

    public CountingIdlingResource getEspressoIdlingResourceForMainActivity() {
        return espressoTestIdlingResource;
    }
}
