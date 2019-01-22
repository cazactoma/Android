package com.example.toma.androidsapt10.vo;

import android.support.annotation.NonNull;

public class TokenRequest {
    private String token;

    public TokenRequest(@NonNull String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
