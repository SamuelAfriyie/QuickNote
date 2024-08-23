package com.example.quicknote.auth.data.repo;

import com.example.quicknote.auth.data.success.LoginSuccess;
import com.example.quicknote.auth.data.success.SignUpSuccess;
import com.example.quicknote.auth.domain.User;
import com.example.quicknote.core.Utils.Response;
import com.example.quicknote.core.failures.Failure;

public interface AuthRepository {

    public Response<LoginSuccess, Failure> loginUser(User user);

    public Response<SignUpSuccess, Failure> createUser(User user);
    public Response<User, Failure> fetchCurrentLoggedInUser();
    public Response<Integer, Failure> updateLoginStatus(String email, boolean isLoggedIn);
}
