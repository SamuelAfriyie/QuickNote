package com.example.quicknote.auth.data.failures;

import com.example.quicknote.core.failures.Failure;

public class UserNameOrPassword extends Failure {
    public UserNameOrPassword(String ERROR_CODE) {
        super(ERROR_CODE, "Username or Password Incorrect 👍");
    }
}
