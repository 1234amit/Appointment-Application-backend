package com.example.Appointment.admin.dto;

public class CreateDoctorRequest {
    private String fullName;
    private String email;
    private String password;
    private String specialty;
    private String phone;
    private String about;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAbout() { return about; }
    public void setAbout(String about) { this.about = about; }
}
