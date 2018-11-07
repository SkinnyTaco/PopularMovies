package com.maurdan.flaco.udacitynd_project2_popularmovies.fragments;

import com.maurdan.flaco.udacitynd_project2_popularmovies.R;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_movie_sort);
    }
}
