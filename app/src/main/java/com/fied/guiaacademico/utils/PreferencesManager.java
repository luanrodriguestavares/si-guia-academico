package com.fied.guiaacademico.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    private static final String PREF_NAME = "GuiaAcademicoPrefs";
    private static final String KEY_FIRST_RUN = "first_run";
    private static PreferencesManager instance;
    private final SharedPreferences sharedPreferences;

    private PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized PreferencesManager getInstance(Context context) {
        if (instance == null) {
            instance = new PreferencesManager(context.getApplicationContext());
        }
        return instance;
    }

    public boolean isFirstRun() {
        return sharedPreferences.getBoolean(KEY_FIRST_RUN, true);
    }

    public void setFirstRun(boolean firstRun) {
        sharedPreferences.edit().putBoolean(KEY_FIRST_RUN, firstRun).apply();
    }
}