package com.Hospital.Hospital.Management.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hospital.Hospital.Management.Entity.User;
import com.Hospital.Hospital.Management.Model.AppointmentModel;

@CrossOrigin
@RestController
@RequestMapping("api/v1/doctor")
@PreAuthorize("hasRole('DOCTOR')")
public class DoctorController {
    
    @GetMapping("allAppointment")
    public ResponseEntity<List<AppointmentModel>> getAllAppointment(@AuthenticationPrincipal User user){
        return new ResponseEntity<>(null);
    }
}
