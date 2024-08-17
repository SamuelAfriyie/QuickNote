package com.example.quicknote.auth.data.failures;

import com.example.quicknote.core.failures.AuthResponse;

public class UserNameOrPassword extends AuthResponse {
    public UserNameOrPassword(String ERROR_CODE) {
        super(ERROR_CODE, "Username or Password Incorrect 👍");
    }
}
