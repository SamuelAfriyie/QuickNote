package com.example.quicknote.auth.data.failures;

import com.example.quicknote.core.failures.AuthResponse;

public class UserNotFound extends AuthResponse {
    public UserNotFound(String ERROR_CODE) {
        super(ERROR_CODE, "User Not Found");
    }
}
