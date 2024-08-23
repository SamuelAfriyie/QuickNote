package com.example.quicknote.auth.data.failures;

import com.example.quicknote.core.failures.Failure;

public class DatabaseNotFound extends Failure {

    public DatabaseNotFound(String ERROR_CODE, String ERROR_MSG) {
        super(ERROR_CODE, ERROR_MSG);
    }
}
