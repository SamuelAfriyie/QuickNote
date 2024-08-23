package com.example.quicknote.auth.data.failures;

import com.example.quicknote.core.failures.Failure;

public class UserNotFound extends Failure {
    public UserNotFound(String ERROR_CODE) {
        super(ERROR_CODE, "User Not Found");
    }
}
