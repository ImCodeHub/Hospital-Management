package com.Hospital.Hospital.Management.Controller;

import java.util.List;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Hospital.Hospital.Management.Model.DoctorRegisterationModel;

import com.Hospital.Hospital.Management.Entity.User;
import com.Hospital.Hospital.Management.Service.Implement.AdminServiceImpl;

@CrossOrigin
@RestController
@RequestMapping("api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private AdminServiceImpl adminServiceImpl;


    @PostMapping("registerDoctor")
    public ResponseEntity<String> register(@RequestPart DoctorRegisterationModel doctorModel, @RequestPart MultipartFile imageFile ){
        try{
            String response = adminServiceImpl.registerDoctor(doctorModel, imageFile);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(IOException e){
            return new ResponseEntity<>("Failed to register doctor due to I/O error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
