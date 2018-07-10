package com.example.elirannoach.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.elirannoach.bakingapp.R;
import com.example.elirannoach.bakingapp.data.Recipe;
import com.example.elirannoach.bakingapp.ui.RecipeInstructionActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;
import java.util.zip.Inflater;

public class RecipeListRecycleViewAdapter extends RecyclerView.Adapter<RecipeCardViewHolder> {
    private List<Recipe> mRecipeList;
    private Context mContext;

    interface OnSelectedRecipeListener{
        void onRecipeClicked();
    }

    public RecipeListRecycleViewAdapter(List<Recipe> recipeList, Context context){
        mRecipeList = recipeList;
        mContext = context;
    }

    @NonNull
    @Override
    public RecipeCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recipeCardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card,parent,false);
        return new RecipeCardViewHolder(recipeCardView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeCardViewHolder holder, final int position) {
        Recipe recipe = mRecipeList.get(position);
        holder.mRecipeTitle.setText(recipe.getnName());
        String imgUrl = mRecipeList.get(position).getmImageSrc();
        if(!imgUrl.isEmpty())
            Picasso.with(mContext).load(imgUrl).placeholder(R.drawable.deafult_recipe_background)// Place holder image from drawable folder
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            holder.mLinearLayout.setBackground(new BitmapDrawable(bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
        holder.setOnSelectedRecipeListener(new OnSelectedRecipeListener() {
            @Override
            public void onRecipeClicked() {
                Intent intent = new Intent(mContext, RecipeInstructionActivity.class);
                intent.putExtra("recipe",mRecipeList.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }
}
