package com.Hospital.Hospital.Management.Service;

import java.util.List;

import com.Hospital.Hospital.Management.Entity.Appoinment;
import com.Hospital.Hospital.Management.Entity.User;

public interface AppoinmentService {

    public String bookAppoinment(User user);

    public List<Appoinment> getAllAppoinments();

    // public List<Appoinment> getRecentAppoinment();
} 

