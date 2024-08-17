package com.example.quicknote.auth.data.failures;

import com.example.quicknote.core.failures.AuthResponse;

public class NoAuthenticatedUser extends AuthResponse {
    public NoAuthenticatedUser(String ERROR_CODE) {
        super(ERROR_CODE, "No Authenticated User");
    }
}
