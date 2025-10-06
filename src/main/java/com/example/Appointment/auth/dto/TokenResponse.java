package com.example.Appointment.auth.dto;

public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private String message;      // <-- new
    private UserDto user;


    public TokenResponse() { }

    public TokenResponse(String accessToken, String refreshToken, String message, UserDto user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.message = message;
        this.user = user;
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

    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }
}
