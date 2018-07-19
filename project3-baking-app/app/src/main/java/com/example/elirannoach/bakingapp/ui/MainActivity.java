package com.example.elirannoach.bakingapp.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.elirannoach.bakingapp.R;
import com.example.elirannoach.bakingapp.data.Recipe;
import com.example.elirannoach.bakingapp.ui.RecipeListFragment;

public class MainActivity extends AppCompatActivity implements RecipeListFragment.OnRecipeClickListener {
    private boolean mIsTwoPane;
    private BottomNavigationView mBottomNavigationView;
    private int mRecipeStepNumber;
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIsTwoPane = findViewById(R.id.tablet_linear_layout_id) != null;
        if (!mIsTwoPane) {
            // only create fragment if activity is started for the first time
            if(savedInstanceState==null) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_activity_frame_id, new RecipeListFragment())
                        .commit();
            }
        }
        else{
            mBottomNavigationView = (BottomNavigationView)
                    findViewById(R.id.bottom_navigation);
            mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    RecipeInstructionFragment fragment = new RecipeInstructionFragment();
                    switch (item.getItemId()) {
                        case R.id.action_previous:
                            if(mRecipeStepNumber>0)
                                mRecipeStepNumber--;
                            fragment.setRecipe(mRecipe,mRecipeStepNumber);
                            getFragmentManager().beginTransaction().replace(R.id.tablet_recipe_instruction_fragment_container_id,fragment).commit();
                            break;
                        case R.id.action_next:
                            if(mRecipe.getmRecipleStepList().size()-1>mRecipeStepNumber)
                                mRecipeStepNumber++;
                            fragment.setRecipe(mRecipe,mRecipeStepNumber);
                            getFragmentManager().beginTransaction().replace(R.id.tablet_recipe_instruction_fragment_container_id,fragment).commit();
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

            if (savedInstanceState!=null){
                mRecipeStepNumber = savedInstanceState.getInt("step");
                RecipeInstructionFragment recipeInstructionFragment = new RecipeInstructionFragment();
                recipeInstructionFragment.setRecipe(mRecipe,mRecipeStepNumber);
                getFragmentManager().beginTransaction().replace(R.id.tablet_recipe_instruction_fragment_container_id,recipeInstructionFragment).commit();
            }

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if(mIsTwoPane) {
            outState.putInt("step", mRecipeStepNumber);
            outState.putParcelable("recipe", mRecipe);
        }
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        if(!mIsTwoPane){
            Intent intent = new Intent(this,RecipeInstructionActivity.class);
            intent.putExtra("recipe",recipe);
            startActivity(intent);
        }
        else{
            mRecipe = recipe;
            mRecipeStepNumber = 0;
            RecipeInstructionFragment fragment = new RecipeInstructionFragment();
            fragment.setRecipe(recipe,mRecipeStepNumber);
            getFragmentManager().beginTransaction().replace(R.id.tablet_recipe_instruction_fragment_container_id,fragment).commit();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
