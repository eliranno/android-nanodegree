package com.example.elirannoach.project2_popular_movies_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

public class SettingsPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting_preference_fragment);
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int count = preferenceScreen.getPreferenceCount();
        for(int i=0;i<count;i++){
            Preference p = preferenceScreen.getPreference(i);
            setPreferenceListSummary(p,sharedPreferences.getString(p.getKey(),""));
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference p = findPreference(key);
        if(p!=null) {
            String value = sharedPreferences.getString(p.getKey(), "");
            setPreferenceListSummary(p, value);
        }
    }


    private void setPreferenceListSummary(Preference preference,String value){
        if (preference instanceof ListPreference){
            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(value);
            if(index>=0)
                listPreference.setSummary(listPreference.getEntries()[index]);
        }
    }
}
