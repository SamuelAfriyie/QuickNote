package com.example.quicknote.auth.presentation.signup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quicknote.auth.data.repo.AuthRepository;
import com.example.quicknote.auth.data.success.SignUpSuccess;
import com.example.quicknote.auth.domain.User;
import com.example.quicknote.core.Utils.Response;
import com.example.quicknote.core.failures.AuthResponse;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SignUpViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<Response<SignUpSuccess, AuthResponse>> _signUpStatus = new MutableLiveData<>();
    public LiveData<Response<SignUpSuccess, AuthResponse>> signUpStatus = _signUpStatus;

    @Inject
    public SignUpViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void signUp(String username, String email, String password) {
        _signUpStatus.setValue(authRepository.createUser(new User(username, email, password)));
    }

}
