package com.example.elirannoach.bakingapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.elirannoach.bakingapp.R;
import com.example.elirannoach.bakingapp.ui.RecipeListFragment;

public class MainActivity extends AppCompatActivity {
    private boolean mIsTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIsTwoPane = findViewById(R.id.)
        getFragmentManager().beginTransaction()
                .add(R.id.main_activity_frame_id,new RecipeListFragment())
                .commit();
    }
}
