package com.example.toma.androidsapt10.vo;

public class AccountResponse
{
    private String username;
    private String password;
    private boolean isSuccess;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountResponse(String token, String username, String password, Boolean isSuccess) {
        this.username = username;
        this.password = password;
        this.isSuccess = isSuccess;
        this.token = token;
    }

    public AccountResponse()
    {

    }
}
