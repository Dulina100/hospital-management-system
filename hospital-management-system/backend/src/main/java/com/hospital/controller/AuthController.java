package com.hospital.controller;

import com.hospital.dto.LoginRequestDTO;
import com.hospital.dto.LoginResponseDTO;
import com.hospital.entity.User;
import com.hospital.entity.Role;
import com.hospital.service.AuthenticationService;
import com.hospital.service.UserService;
import com.hospital.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO response = authenticationService.authenticate(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register/patient")
    public ResponseEntity<String> registerPatient(@RequestBody User user) {
        userService.createUser(user, "PATIENT");
        return new ResponseEntity<>("Patient registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/register/doctor")
    public ResponseEntity<String> registerDoctor(@RequestBody User user) {
        userService.createUser(user, "DOCTOR");
        return new ResponseEntity<>("Doctor registered successfully", HttpStatus.CREATED);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return new ResponseEntity<>("Token is valid", HttpStatus.OK);
    }
}
