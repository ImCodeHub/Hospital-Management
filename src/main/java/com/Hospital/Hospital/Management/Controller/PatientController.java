package com.Hospital.Hospital.Management.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.Hospital.Hospital.Management.Entity.Appointment;
import com.Hospital.Hospital.Management.Entity.User;
import com.Hospital.Hospital.Management.Model.AppointmentModel;
import com.Hospital.Hospital.Management.Service.Implement.AppointmentServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin
@RestController
@RequestMapping("api/v1/patient")
@PreAuthorize("hasRole('PATIENT')")
public class PatientController {
    
    @Autowired
    private AppointmentServiceImpl appointmentServiceImpl;

    @GetMapping("get-name")
    public String getUserName(@AuthenticationPrincipal User user){
        return user.getFirstName();
    }

    @PostMapping("appointment")  
    public ResponseEntity<String> bookAppoinment(@RequestBody Appointment appointment , @AuthenticationPrincipal User user){
        String response = appointmentServiceImpl.bookAppointment(appointment,user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // to get list of appoinment.
    @GetMapping("allAppoinment")
    public ResponseEntity<List<Appointment>> allAppoinments(){
        List<Appointment> list = appointmentServiceImpl.getAllAppointments();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("appoinment/recent")
    public ResponseEntity<List<AppointmentModel>> recentAppoinment(@AuthenticationPrincipal User user){
        List<AppointmentModel> list = appointmentServiceImpl.getRecentAppointment(user.getUserId());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("appoinment/cancel/{id}")
    public ResponseEntity<String> cancelAppoinment(@PathVariable String id){
        String response = appointmentServiceImpl.cancelAppointment(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
