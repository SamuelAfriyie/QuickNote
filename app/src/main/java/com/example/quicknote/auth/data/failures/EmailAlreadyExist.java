package com.example.quicknote.auth.data.failures;

import com.example.quicknote.core.failures.AuthResponse;

public class EmailAlreadyExist extends AuthResponse {
    public EmailAlreadyExist(String ERROR_CODE) {
        super(ERROR_CODE, "Email already exist");
    }
}
