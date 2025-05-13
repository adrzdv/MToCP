package com.adrzdv.mtocp;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class App extends Application {
    private static App instance;
    private static Toast currentToast;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate();
        instance = this;
    }

    public static void showToast(Context context, String message) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        currentToast.show();
    }
}
