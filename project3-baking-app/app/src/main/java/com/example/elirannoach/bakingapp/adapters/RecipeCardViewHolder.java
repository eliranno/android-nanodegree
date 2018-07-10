package com.example.elirannoach.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.elirannoach.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.recipe_name_textview_id)
    TextView mRecipeTitle;
    @BindView(R.id.card_layout_container_id)
    LinearLayout mLinearLayout;
    RecipeListRecycleViewAdapter.OnSelectedRecipeListener mListerner;

    public RecipeCardViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        itemView.setOnClickListener(this);
        mListerner = null;
    }

    public void setOnSelectedRecipeListener(RecipeListRecycleViewAdapter.OnSelectedRecipeListener listener){
        mListerner = listener;
    }

    @Override
    public void onClick(View v) {
        mListerner.onRecipeClicked();
    }
}
