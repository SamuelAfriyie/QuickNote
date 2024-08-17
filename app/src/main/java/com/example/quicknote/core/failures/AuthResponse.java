package com.example.quicknote.core.failures;

public abstract class AuthResponse {
    private final String RES_CODE;
    private final String RES_MSG;

    public AuthResponse(String RES_CODE, String RES_MSG) {
        this.RES_CODE = RES_CODE;
        this.RES_MSG = RES_MSG;
    }

    public String getRES_CODE() {
        return RES_CODE;
    }

    public String getRES_MSG() {
        return RES_MSG;
    }
}
