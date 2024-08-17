package com.example.quicknote.auth.data.success;

import com.example.quicknote.core.failures.AuthResponse;

public class LoginSuccess extends AuthResponse {
    public LoginSuccess(String RES_CODE) {
        super(RES_CODE, "Logged In Successful 👍");
    }
}
