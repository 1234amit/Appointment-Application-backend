package com.example.Appointment.admin;

import com.example.Appointment.admin.dto.*;
import com.example.Appointment.auth.dto.UserDto;
import com.example.Appointment.doctor.Doctor;
import com.example.Appointment.doctor.DoctorRepository;
import com.example.Appointment.user.Role;
import com.example.Appointment.user.User;
import com.example.Appointment.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

//    public CreateDoctorResponse createDoctor(CreateDoctorRequest r) {
//        if (userRepository.existsByEmail(r.getEmail())) throw new IllegalArgumentException("Email already used");
//        User u = new User();
//        u.setFullName(r.getFullName());
//        u.setEmail(r.getEmail());
//        u.setPassword(passwordEncoder.encode(r.getPassword()));
//        u.setRole(Role.DOCTOR);
//        userRepository.save(u);
//        Doctor d = new Doctor();
//        d.setUser(u);
//        d.setSpecialty(r.getSpecialty());
//        d.setPhone(r.getPhone());
//        d.setAbout(r.getAbout());
//        doctorRepository.save(d);
//        DoctorDto dto = new DoctorDto(d.getId(), d.getSpecialty(), d.getPhone(), d.getAbout(), toUserDto(u));
//        return new CreateDoctorResponse("Doctor created", dto);
//    }

    @Transactional
    public CreateDoctorResponse createDoctor(CreateDoctorRequest r) {
        if (userRepository.existsByEmail(r.getEmail())) {
            return new CreateDoctorResponse("Email already used", null);
        }


        User u = new User();
        u.setFullName(r.getFullName());
        u.setEmail(r.getEmail());
        u.setPassword(passwordEncoder.encode(r.getPassword()));
        u.setRole(Role.DOCTOR);

        Doctor d = new Doctor();
        d.setUser(u);
        d.setSpecialty(r.getSpecialty());
        d.setPhone(r.getPhone());
        d.setAbout(r.getAbout());

        doctorRepository.save(d);

        DoctorDto dto = new DoctorDto(
                d.getId(),
                d.getSpecialty(),
                d.getPhone(),
                d.getAbout(),
                new UserDto(u.getId(), u.getFullName(), u.getEmail(), u.getRole().name())
        );

        return new CreateDoctorResponse("Doctor created", dto);
    }




    //get all users
    public List<UserDto> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::toUserDto)
                .collect(Collectors.toList());
    }

    //update users
    public CreateUserResponse updateUser(Long userId, CreateUserRequest r){
        User u = userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("User not found"));
        if(r.getFullName() != null) u.setFullName(r.getFullName());
        if(r.getEmail() != null) u.setEmail(r.getEmail());
        if (r.getPassword() != null) u.setPassword(passwordEncoder.encode(r.getPassword()));
        if (r.getRole() != null) u.setRole(Role.valueOf(r.getRole().toUpperCase()));
        userRepository.save(u);
        return new CreateUserResponse("User updated successfully", toUserDto(u));
    }

    //delete users
    public void deleteUser(Long userId){
        User u = userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("user not found"));
        userRepository.delete(u);
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

    //update doctors
    public CreateDoctorResponse updateDoctor(Long doctorId, CreateDoctorRequest r){
        Doctor d = doctorRepository.findById(doctorId)
                .orElseThrow(()->new IllegalArgumentException("Doctor not found"));
        User u = d.getUser();
        if (r.getFullName() != null) u.setFullName(r.getFullName());
        if (r.getEmail() != null) u.setEmail(r.getEmail());
        if (r.getPassword() != null) u.setPassword(passwordEncoder.encode(r.getPassword()));
        userRepository.save(u);
        if (r.getSpecialty() != null) d.setSpecialty(r.getSpecialty());
        if (r.getPhone() != null) d.setPhone(r.getPhone());
        if (r.getAbout() != null) d.setAbout(r.getAbout());
        doctorRepository.save(d);

        DoctorDto dto = new DoctorDto(d.getId(), d.getSpecialty(), d.getPhone(), d.getAbout(), toUserDto(u));
        return new CreateDoctorResponse("Doctor updated successfully", dto);
    }

    public void deleteDoctor(Long doctorId) {
        Doctor d = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        doctorRepository.delete(d); // user will be deleted automatically due to cascade
    }



}
