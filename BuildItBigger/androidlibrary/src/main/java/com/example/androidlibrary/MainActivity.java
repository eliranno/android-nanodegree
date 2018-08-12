package com.example.androidlibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {
    private TextView jokeTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        jokeTextView = findViewById(R.id.joke_text_view);
        String jokeStr = getIntent().getStringExtra("JOKE");
        jokeTextView.setText(jokeStr);
    }
}
