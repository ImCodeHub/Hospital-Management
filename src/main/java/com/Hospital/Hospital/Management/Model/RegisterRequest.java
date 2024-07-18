package com.Hospital.Hospital.Management.Model;

import java.time.LocalDate;

import com.Hospital.Hospital.Management.Entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    
    private String userId;

    private String firstName;

    private String lastName;

    private String gender;

    private String email;

    private String mobileNo;

    private String password;

    private String speciality;

    private String discription;

    private LocalDate dateOfJoining;

    private Role role;
}
