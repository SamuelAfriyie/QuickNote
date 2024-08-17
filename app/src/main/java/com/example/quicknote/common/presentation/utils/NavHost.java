package com.example.quicknote.common.presentation.utils;
import android.content.Context;
import android.content.Intent;
public class NavHost {
    public static void navigateTo(Context context, Class<?> targetActivity) {
        Intent intent = new Intent(context, targetActivity);
        context.startActivity(intent);
    }

    public static Intent navigate(Context context, Class<?> targetActivity) {
        return new Intent(context, targetActivity);
    }
}


