package com.example.quicknote.auth.presentation.signup.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.quicknote.auth.data.success.SignUpSuccess;
import com.example.quicknote.auth.presentation.login.ui.LoginActivity;
import com.example.quicknote.auth.presentation.signup.SignUpViewModel;
import com.example.quicknote.common.presentation.utils.NavHost;
import com.example.quicknote.core.Utils.Response;
import com.example.quicknote.core.failures.Failure;
import com.example.quicknote.databinding.ActivitySignUpBinding;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpActivity extends AppCompatActivity {
    private SignUpViewModel signUpViewModel;
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot()); // Make sure this layout file exists
        signUpViewModel = new ViewModelProvider(SignUpActivity.this).get(SignUpViewModel.class);

        binding.txtOption.setOnClickListener(v -> {
            NavHost.navigateTo(SignUpActivity.this, LoginActivity.class);
        });

        binding.btnSignup.setOnClickListener(v -> {
            handleSignUp(
                    Objects.requireNonNull(binding.etUsername.getText()).toString(),
                    Objects.requireNonNull(binding.etEmail.getText()).toString(),
                    Objects.requireNonNull(binding.etPassword.getText()).toString(),
                    Objects.requireNonNull(binding.etConfirmPassword.getText()).toString()
            );
        });

        signUpViewModel.signUpStatus.observe(this, this::observeSignUpStatus);
    }
    private void handleSignUp(String name, String email, String password, String confirmPassword) {
        String validateMsg = validateInput(name, email, password, confirmPassword);
        if (validateMsg == null) {
            signUpViewModel.signUp(name, email, password);
        } else {
            Toast.makeText(this, validateMsg, Toast.LENGTH_SHORT).show();
        }
    }
    private void observeSignUpStatus(final Response<SignUpSuccess, Failure> v) {
        if (v.isSuccess()) {
            Response.Success<SignUpSuccess, Failure> success = (Response.Success<SignUpSuccess, Failure>) v;
            Failure res = success.getValue();
            NavHost.navigateTo(this, LoginActivity.class);
            Toast.makeText(this, res.getRES_MSG(), Toast.LENGTH_SHORT).show();
        } else {
            Response.Failure<SignUpSuccess, Failure> failure = (Response.Failure<SignUpSuccess, Failure>) v;
            Failure res = failure.getValue();
            Toast.makeText(this, res.getRES_MSG(), Toast.LENGTH_SHORT).show();
        }
    }
    private String validateInput(String name, String email, String password, String confirmPassword ) {
        if (name.isBlank() || name.isEmpty()) {
            return "Username field cannot be empty!";
        }

        if (email.isBlank() || email.isEmpty()) {
            return "Email field cannot be empty!";
        }

        if (password.isEmpty() || password.isBlank()) {
            return "Password field cannot be empty";
        }

        if (!password.equals(confirmPassword)) {
            return "Password Mismatch";
        }
        return null;
    }
}