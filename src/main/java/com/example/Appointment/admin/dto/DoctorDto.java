package com.example.Appointment.admin.dto;

import com.example.Appointment.auth.dto.UserDto;

public class DoctorDto {
    private Long id;
    private String specialty;
    private String phone;
    private String about;
    private UserDto user;

    public DoctorDto() {}
    public DoctorDto(Long id, String specialty, String phone, String about, UserDto user) {
        this.id = id; this.specialty = specialty; this.phone = phone; this.about = about; this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAbout() { return about; }
    public void setAbout(String about) { this.about = about; }
    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }
}
