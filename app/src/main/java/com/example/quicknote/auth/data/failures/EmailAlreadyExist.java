package com.example.quicknote.auth.data.failures;

import com.example.quicknote.core.failures.Failure;

public class EmailAlreadyExist extends Failure {
    public EmailAlreadyExist(String ERROR_CODE) {
        super(ERROR_CODE, "Email already exist");
    }
}
