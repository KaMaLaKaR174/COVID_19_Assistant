package com.example.kamalakar.covidtracker.Fragments;

import android.os.Bundle;

import com.example.kamalakar.covidtracker.R;

import androidx.preference.PreferenceFragmentCompat;



public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences,rootKey);
    }
}
