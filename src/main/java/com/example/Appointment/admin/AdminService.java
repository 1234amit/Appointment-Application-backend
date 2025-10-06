package com.example.Appointment.admin;

import com.example.Appointment.admin.dto.*;
import com.example.Appointment.auth.dto.UserDto;
import com.example.Appointment.doctor.Doctor;
import com.example.Appointment.doctor.DoctorRepository;
import com.example.Appointment.user.Role;
import com.example.Appointment.user.User;
import com.example.Appointment.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(UserRepository userRepository, DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private UserDto toUserDto(User u) {
        return new UserDto(u.getId(), u.getFullName(), u.getEmail(), u.getRole().name());
    }

    public CreateUserResponse createUser(CreateUserRequest r) {
        if (userRepository.existsByEmail(r.getEmail())) throw new IllegalArgumentException("Email already used");
        Role role = r.getRole() == null ? Role.USER : Role.valueOf(r.getRole().toUpperCase());
        User u = new User();
        u.setFullName(r.getFullName());
        u.setEmail(r.getEmail());
        u.setPassword(passwordEncoder.encode(r.getPassword()));
        u.setRole(role);
        userRepository.save(u);
        return new CreateUserResponse("User created", toUserDto(u));
    }

    public CreateDoctorResponse createDoctor(CreateDoctorRequest r) {
        if (userRepository.existsByEmail(r.getEmail())) throw new IllegalArgumentException("Email already used");
        User u = new User();
        u.setFullName(r.getFullName());
        u.setEmail(r.getEmail());
        u.setPassword(passwordEncoder.encode(r.getPassword()));
        u.setRole(Role.DOCTOR);
        userRepository.save(u);
        Doctor d = new Doctor();
        d.setUser(u);
        d.setSpecialty(r.getSpecialty());
        d.setPhone(r.getPhone());
        d.setAbout(r.getAbout());
        doctorRepository.save(d);
        DoctorDto dto = new DoctorDto(d.getId(), d.getSpecialty(), d.getPhone(), d.getAbout(), toUserDto(u));
        return new CreateDoctorResponse("Doctor created", dto);
    }

    public List<UserDto> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::toUserDto)
                .collect(Collectors.toList());
    }

    //get all doctors
    public List<DoctorDto> getAllDoctor(){
        return doctorRepository.findAll()
                .stream()
                .map(d->new DoctorDto(
                        d.getId(),
                        d.getSpecialty(),
                        d.getPhone(),
                        d.getAbout(),
                        toUserDto(d.getUser())
                ))
                .collect(Collectors.toList());
    }


}
