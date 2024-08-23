package com.example.quicknote.common.presentation.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.quicknote.R;

public class ThemeHelper {

    private static final String PREFS_NAME = "theme_prefs";
    private static final String KEY_THEME = "selected_theme";

    public static void applyTheme(String themeName, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_THEME, themeName);
        editor.apply();

        if (themeName.equals("light")) {
            context.setTheme(R.style.Theme_QuickNote_WithActionBar);
        } else {
            context.setTheme(R.style.Theme_QuickNote_WithActionBar);
        }
    }

    public static void loadTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String themeName = sharedPreferences.getString(KEY_THEME, "light");
        applyTheme(themeName, context);
    }
}