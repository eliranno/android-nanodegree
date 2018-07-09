package com.example.elirannoach.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.elirannoach.bakingapp.R;
import com.example.elirannoach.bakingapp.data.Recipe;

public class RecipeInstructionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_instruction_activity);
        Recipe recipe = (Recipe) getIntent().getParcelableExtra("recipe");
        RecipeInstructionFragment recipeInstructionFragment = new RecipeInstructionFragment();
        recipeInstructionFragment.setRecipe(recipe,0);
        FrameLayout frame = (FrameLayout) findViewById(R.id.recipe_instruction_fragment_container_id);
        getFragmentManager().beginTransaction().add(frame.getId(),recipeInstructionFragment).commit();



    }
}
