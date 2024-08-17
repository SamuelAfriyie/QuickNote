package com.example.quicknote.auth.data.repo;

import com.example.quicknote.auth.data.success.LoginSuccess;
import com.example.quicknote.auth.data.success.SignUpSuccess;
import com.example.quicknote.auth.domain.User;
import com.example.quicknote.core.Utils.Response;
import com.example.quicknote.core.failures.AuthResponse;

public interface AuthRepository {

    public Response<LoginSuccess, AuthResponse> loginUser(User user);

    public Response<SignUpSuccess, AuthResponse> createUser(User user);
    public Response<User, AuthResponse> fetchCurrentLoggedInUser();
    public Response<Integer, AuthResponse> updateLoginStatus(String email, boolean isLoggedIn);
}
