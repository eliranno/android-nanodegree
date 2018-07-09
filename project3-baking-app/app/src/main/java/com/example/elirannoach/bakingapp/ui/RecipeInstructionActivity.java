package com.example.elirannoach.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.elirannoach.bakingapp.R;
import com.example.elirannoach.bakingapp.data.Recipe;

public class RecipeInstructionActivity extends AppCompatActivity {
    BottomNavigationView mBottomNavigationView;
    private int mRecipeStepNumber;
    private Recipe mRecipe;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_instruction_activity);
        mBottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                RecipeInstructionFragment fragment = new RecipeInstructionFragment();
                switch (item.getItemId()) {
                    case R.id.action_previous:
                        mRecipeStepNumber--;
                        fragment.setRecipe(mRecipe,mRecipeStepNumber);
                        getFragmentManager().beginTransaction().replace(R.id.recipe_instruction_fragment_container_id,fragment).commit();
                        break;
                    case R.id.action_next:
                        mRecipeStepNumber++;
                        fragment.setRecipe(mRecipe,mRecipeStepNumber);
                        getFragmentManager().beginTransaction().replace(R.id.recipe_instruction_fragment_container_id,fragment).commit();
                        break;
                    case R.id.action_homes:
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        mRecipe = (Recipe) getIntent().getParcelableExtra("recipe");
        if (savedInstanceState==null)
            mRecipeStepNumber = 0;
        else
            mRecipeStepNumber = savedInstanceState.getInt("step");
        RecipeInstructionFragment recipeInstructionFragment = new RecipeInstructionFragment();
        recipeInstructionFragment.setRecipe(mRecipe,mRecipeStepNumber);
        FrameLayout frame = (FrameLayout) findViewById(R.id.recipe_instruction_fragment_container_id);
        getFragmentManager().beginTransaction().add(frame.getId(),recipeInstructionFragment).commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt("step",mRecipeStepNumber);
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
