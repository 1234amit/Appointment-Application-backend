package com.example.Appointment.auth.dto;

public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private String message;      // <-- new


    public TokenResponse() { }

    public TokenResponse(String accessToken, String refreshToken, String message) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.message = message;
    }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
