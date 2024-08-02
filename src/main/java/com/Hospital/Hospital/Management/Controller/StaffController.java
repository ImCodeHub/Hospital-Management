package com.Hospital.Hospital.Management.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/v1/staff")
@PreAuthorize("hasRole('STAFF')")
public class StaffController {
    
    
}
