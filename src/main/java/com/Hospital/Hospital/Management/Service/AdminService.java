package com.Hospital.Hospital.Management.Service;

import java.util.List;

import com.Hospital.Hospital.Management.Entity.User;

public interface AdminService {

    public List<User> getAllDoctors();
    public List<User> getAllPatient();
    public Boolean blacklist(String userId);
    public Boolean whitelist(String userId);
} 
