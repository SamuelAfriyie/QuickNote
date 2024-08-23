package com.example.quicknote.auth.presentation.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quicknote.auth.data.repo.AuthRepository;
import com.example.quicknote.auth.data.success.LoginSuccess;
import com.example.quicknote.auth.domain.User;
import com.example.quicknote.common.presentation.auth.AuthStateViewModel;
import com.example.quicknote.core.Utils.Response;
import com.example.quicknote.core.failures.Failure;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<Response<LoginSuccess, Failure>> _loginStatus = new MutableLiveData<>();
    public LiveData<Response<LoginSuccess, Failure>> loginStatus = _loginStatus;

    @Inject
    public LoginViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void login(String email, String password) {
        _loginStatus.setValue(authRepository.loginUser(new User(email, password)));
    }
}
