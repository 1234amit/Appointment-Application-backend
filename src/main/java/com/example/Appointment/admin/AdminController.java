package com.example.Appointment.admin;

import com.example.Appointment.admin.dto.*;
import com.example.Appointment.auth.dto.ApiMessage;
import com.example.Appointment.auth.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;
    public AdminController(AdminService adminService){this.adminService = adminService;}
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(adminService.createUser(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/doctors")
    public ResponseEntity<CreateDoctorResponse> createDoctor(@Valid @RequestBody CreateDoctorRequest request) {
        return ResponseEntity.ok(adminService.createDoctor(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> viewAllUsers(){
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    // ✅ Update user
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}")
    public ResponseEntity<CreateUserResponse> updateUser(@PathVariable Long id,
                                                         @Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(adminService.updateUser(id, request));
    }

    //delete user
    // ✅ Delete user
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiMessage> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok(new ApiMessage("User deleted successfully"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDto>> viewAllDoctors(){
        return ResponseEntity.ok(adminService.getAllDoctor());
    }

    // ✅ Update doctor
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/doctors/{id}")
    public ResponseEntity<CreateDoctorResponse> updateDoctor(@PathVariable Long id,
                                                             @Valid @RequestBody CreateDoctorRequest request) {
        return ResponseEntity.ok(adminService.updateDoctor(id, request));
    }

    // ✅ Delete doctor
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<ApiMessage> deleteDoctor(@PathVariable Long id) {
        adminService.deleteDoctor(id);
        return ResponseEntity.ok(new ApiMessage("Doctor deleted successfully"));
    }


}
