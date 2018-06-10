package com.example.elirannoach.project2_popular_movies_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
    }
}
