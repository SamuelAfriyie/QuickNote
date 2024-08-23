package com.example.quicknote.auth.data.failures;

import com.example.quicknote.core.failures.Failure;

public class NoAuthenticatedUser extends Failure {
    public NoAuthenticatedUser(String ERROR_CODE) {
        super(ERROR_CODE, "No Authenticated User");
    }
}
