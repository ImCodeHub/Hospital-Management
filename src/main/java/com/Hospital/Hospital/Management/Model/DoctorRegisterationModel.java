package com.Hospital.Hospital.Management.Model;

import com.Hospital.Hospital.Management.Entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRegisterationModel {

    private String firstName;

    private String lastName;

    private String gender;

    private String email;

    private String mobileNo;

    private String password;

    private Role role;

    private String photo;

    private String education;

    private String specialization;

    private String type;

    private Long experience;

    private String about;
}
