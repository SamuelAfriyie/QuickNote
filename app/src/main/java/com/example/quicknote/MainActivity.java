package com.example.quicknote;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quicknote.auth.presentation.login.ui.LoginActivity;
import com.example.quicknote.common.presentation.auth.AuthStateViewModel;
import com.example.quicknote.common.presentation.utils.NavHost;
import com.example.quicknote.note.presentation.ui.NoteActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    AuthStateViewModel authStateViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authStateViewModel.getLastLoggedInUser();

        authStateViewModel.getCurrentAuthState().observe(this, v -> {
            if (v) {
                NavHost.navigateTo(this, NoteActivity.class);
            } else {
                NavHost.navigateTo(this, LoginActivity.class);
            }
            finish();
        });
    }
}