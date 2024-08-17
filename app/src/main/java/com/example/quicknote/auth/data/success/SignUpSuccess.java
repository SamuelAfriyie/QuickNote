package com.example.quicknote.auth.data.success;

import com.example.quicknote.core.failures.AuthResponse;

public class SignUpSuccess extends AuthResponse {
    public SignUpSuccess(String RES_CODE) {
        super(RES_CODE, "SignUp successful 👍");
    }
}
