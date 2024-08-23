package com.example.quicknote.auth.presentation.login.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.quicknote.auth.data.success.LoginSuccess;
import com.example.quicknote.auth.presentation.login.LoginViewModel;
import com.example.quicknote.auth.presentation.signup.ui.SignUpActivity;
import com.example.quicknote.common.presentation.auth.AuthStateViewModel;
import com.example.quicknote.common.presentation.utils.NavHost;
import com.example.quicknote.core.Utils.Response;
import com.example.quicknote.core.failures.Failure;
import com.example.quicknote.databinding.ActivityLoginBinding;
import com.example.quicknote.note.presentation.ui.NoteActivity;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {
    @Inject
    AuthStateViewModel authStateViewModel;
    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot()); // Make sure this layout file exists
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.txtOption.setOnClickListener(v -> NavHost.navigateTo(this, SignUpActivity.class));
        binding.btnLogin.setOnClickListener(v -> handleLogin(Objects.requireNonNull(binding.etEmail.getText()).toString(), Objects.requireNonNull(binding.etPasswordI.getText()).toString()));

        loginViewModel.loginStatus.observe(this, this::observeLoginStatus);
    }

    private void handleLogin(String email, String password) {
        String validateMsg = validateInput(email, password);
        if (validateMsg == null) {
            loginViewModel.login(email, password);
        } else {
            Toast.makeText(this, validateMsg, Toast.LENGTH_SHORT).show();
        }
    }

    private String validateInput(String email, String password) {
        if (email.isBlank() || email.isEmpty()) {
            return "Email field cannot be empty!";
        }

        if (password.isEmpty() || password.isBlank()) {
            return "Password field cannot be empty";
        }

        return null;
    }

    private void observeLoginStatus(final Response<LoginSuccess, Failure> v) {
        if (v.isSuccess()) {
            Response.Success<LoginSuccess, Failure> success = (Response.Success<LoginSuccess, Failure>) v;
            authStateViewModel.getLastLoggedInUser();
            NavHost.navigateTo(this, NoteActivity.class);
            Toast.makeText(this, success.getValue().getRES_MSG(), Toast.LENGTH_SHORT).show();
        } else {
            Response.Failure<LoginSuccess, Failure> failure = (Response.Failure<LoginSuccess, Failure>) v;
            Toast.makeText(this, failure.getValue().getRES_MSG(), Toast.LENGTH_SHORT).show();
        }
    }
}