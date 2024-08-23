package com.example.quicknote.common.presentation.utils;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quicknote.R;

public class NavHost {
    public static void navigateTo(Context context, Class<?> targetActivity) {
        Intent intent = new Intent(context, targetActivity);
        context.startActivity(intent);
    }

    public static Intent navigate(Context context, Class<?> targetActivity) {
        return new Intent(context, targetActivity);
    }

    public static boolean loadFragment(Fragment fragment) {
        // Replace the current fragment with the selected one
        FragmentManager fragmentManager = fragment.getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        return true;
    }
}


