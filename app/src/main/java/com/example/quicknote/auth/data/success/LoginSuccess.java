package com.example.quicknote.auth.data.success;

import com.example.quicknote.core.failures.Failure;

public class LoginSuccess extends Failure {
    public LoginSuccess(String RES_CODE) {
        super(RES_CODE, "Logged In Successful 👍");
    }
}
