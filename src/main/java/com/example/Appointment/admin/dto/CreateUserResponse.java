package com.example.Appointment.admin.dto;

import com.example.Appointment.auth.dto.UserDto;

public class CreateUserResponse {
    private String message;
    private UserDto user;

    public CreateUserResponse() {}
    public CreateUserResponse(String message, UserDto user) {
        this.message = message; this.user = user;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }
}
