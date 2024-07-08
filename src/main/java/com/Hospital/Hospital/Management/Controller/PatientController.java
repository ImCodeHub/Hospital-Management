package com.Hospital.Hospital.Management.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.Hospital.Hospital.Management.Entity.Appoinment;
import com.Hospital.Hospital.Management.Entity.User;
import com.Hospital.Hospital.Management.Service.Implement.AppoinmentServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("api/v1/patient")
@PreAuthorize("hasRole('PATIENT')")
public class PatientController {
    
    @Autowired
    private AppoinmentServiceImpl appoinmentServiceImpl;

    @PostMapping("appoinment")
    public ResponseEntity<String> bookAppoinment(@AuthenticationPrincipal User user){
        String response = appoinmentServiceImpl.bookAppoinment(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // to get list of appoinment.
    @GetMapping("allAppoinment")
    public ResponseEntity<List<Appoinment>> allAppoinments(){
        List<Appoinment> list = appoinmentServiceImpl.getAllAppoinments();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    

    
}
