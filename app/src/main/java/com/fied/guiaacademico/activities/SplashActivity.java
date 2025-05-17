package com.fied.guiaacademico.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.fied.guiaacademico.R;
import com.fied.guiaacademico.database.DatabaseHelper;
import com.fied.guiaacademico.utils.PreferencesManager;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        PreferencesManager preferencesManager = PreferencesManager.getInstance(this);
        if (preferencesManager.isFirstRun()) {
            DatabaseHelper.getInstance(this);
            preferencesManager.setFirstRun(false);
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_DELAY);
    }
}