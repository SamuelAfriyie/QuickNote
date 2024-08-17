package com.example.quicknote;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class StartApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize other libraries or global state here
    }
}
