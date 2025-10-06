package com.example.Appointment.doctor;

import com.example.Appointment.user.User;
import jakarta.persistence.*;

//@Entity
//public class Doctor {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
////    @OneToOne(optional = false)
////    private User user;
//    private String specialty;
//    private String phone;
//    private String about;
//
////    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
////    @JoinColumn(name = "user_id", referencedColumnName = "id")
////    private User user;
//
//    @OneToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;
//
//    //getter and setter
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//    public User getUser() { return user; }
//    public void setUser(User user) { this.user = user; }
//    public String getSpecialty() { return specialty; }
//    public void setSpecialty(String specialty) { this.specialty = specialty; }
//    public String getPhone() { return phone; }
//    public void setPhone(String phone) { this.phone = phone; }
//    public String getAbout() { return about; }
//    public void setAbout(String about) { this.about = about; }
//}


@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String specialty;
    private String phone;
    private String about;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAbout() { return about; }
    public void setAbout(String about) { this.about = about; }
}

