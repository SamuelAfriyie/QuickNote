package com.example.quicknote.auth.data.success;

import com.example.quicknote.core.failures.Failure;

public class SignUpSuccess extends Failure {
    public SignUpSuccess(String RES_CODE) {
        super(RES_CODE, "SignUp successful 👍");
    }
}
