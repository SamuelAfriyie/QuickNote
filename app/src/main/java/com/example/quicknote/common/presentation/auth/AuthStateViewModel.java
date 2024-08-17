package com.example.quicknote.common.presentation.auth;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.quicknote.auth.data.repo.AuthRepository;
import com.example.quicknote.auth.domain.User;
import com.example.quicknote.core.Utils.Response;
import com.example.quicknote.core.failures.AuthResponse;

import javax.inject.Inject;

public class AuthStateViewModel extends AndroidViewModel {
    public static User userCache;
    private final AuthRepository authRepository;
    private final MutableLiveData<Boolean> currentAuthStatus = new MutableLiveData<>(false);

    @Inject
    public AuthStateViewModel(@NonNull Application application, AuthRepository authRepository) {
        super(application);
        this.authRepository = authRepository;
    }

    public LiveData<Boolean> getCurrentAuthState() {
        return currentAuthStatus;
    }

    public void logout(String email) {
        Response<Integer, AuthResponse> res = authRepository.updateLoginStatus(email, false);
        currentAuthStatus.setValue(res.isSuccess());
    }

    public void getLastLoggedInUser() {
        Response<User, AuthResponse> res = authRepository.fetchCurrentLoggedInUser();
        if(res.isSuccess()) {
            Response.Success<User, AuthResponse> successState = (Response.Success<User, AuthResponse>) res;
            userCache = successState.getValue();
            currentAuthStatus.setValue(true);
            Log.d( "User", userCache.toString());
        } else {
//            Response.Failure<User, AuthResponse> failureState = (Response.Failure<User, AuthResponse>) res;
            currentAuthStatus.setValue(false);
        }
    }
}
