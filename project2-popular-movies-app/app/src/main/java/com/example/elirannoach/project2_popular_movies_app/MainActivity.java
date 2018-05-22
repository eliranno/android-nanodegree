package com.example.elirannoach.project2_popular_movies_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListHandler {

    Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToast = new Toast(this);
    }

    @Override
    public void handleConnectionError() {
        mToast.setText(R.string.connection_error);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    public void handleProcessingDataError() {
        mToast.setText(R.string.process_data_error);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    public void handleData(List<Movie> movieList) {

    }
}
