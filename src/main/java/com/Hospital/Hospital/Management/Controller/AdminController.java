package com.Hospital.Hospital.Management.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hospital.Hospital.Management.Entity.User;
import com.Hospital.Hospital.Management.Service.Implement.AdminServiceImpl;

@RestController
@RequestMapping("api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @GetMapping("doctors")
    public ResponseEntity<List<User>> getDoctorList(){
        List<User> list = adminServiceImpl.getAllDoctors();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping("patient")
    public ResponseEntity<List<User>> getPatientList(){
        List<User> list = adminServiceImpl.getAllPatient();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("blacklist/{id}")
    public ResponseEntity<Boolean> blackListUser(@PathVariable("id") String userId){
        Boolean response = adminServiceImpl.blacklist(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("whitelist/{id}")
    public ResponseEntity<Boolean> whiteListUser(@PathVariable("id") String userId){
        Boolean response = adminServiceImpl.whitelist(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
