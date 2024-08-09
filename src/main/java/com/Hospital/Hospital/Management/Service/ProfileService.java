package com.Hospital.Hospital.Management.Service;

import java.util.List;

import com.Hospital.Hospital.Management.Model.DoctorRegisterationModel;

public interface ProfileService {
    public String getDoctorNameBytype(String type);
    public List<DoctorRegisterationModel> getDoctor(String doctorId); 
}
