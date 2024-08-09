package com.Hospital.Hospital.Management.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.Hospital.Hospital.Management.Entity.User;
import com.Hospital.Hospital.Management.Model.DoctorRegisterationModel;

public interface AdminService {

    public String registerDoctor(DoctorRegisterationModel doctorModel, MultipartFile imageFile) throws IOException;
    public List<User> getAllDoctors();
    public List<User> getAllPatient();
    public Boolean blacklist(String userId);
    public Boolean whitelist(String userId);
} 
