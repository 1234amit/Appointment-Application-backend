package com.example.Appointment.admin.dto;
public class CreateDoctorResponse {
    private String message;
    private DoctorDto doctor;

    public CreateDoctorResponse() {}
    public CreateDoctorResponse(String message, DoctorDto doctor) {
        this.message = message; this.doctor = doctor;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public DoctorDto getDoctor() { return doctor; }
    public void setDoctor(DoctorDto doctor) { this.doctor = doctor; }
}
