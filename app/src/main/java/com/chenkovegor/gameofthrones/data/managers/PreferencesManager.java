package com.chenkovegor.gameofthrones.data.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesManager {

    private final String DOWNLOADED_DATA_KEY = "DOWNLOADED_DATA_KEY";

    private SharedPreferences mSharedPreferences;

    public PreferencesManager(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isDataDownloaded(){
        return mSharedPreferences.getBoolean(DOWNLOADED_DATA_KEY, false);
    }

    public void dataDownloadComplete(){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(DOWNLOADED_DATA_KEY, true);
        editor.apply();
    }

}
