package com.Hospital.Hospital.Management.Service.Implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Hospital.Hospital.Management.Entity.Profile;
import com.Hospital.Hospital.Management.Model.DoctorRegisterationModel;
import com.Hospital.Hospital.Management.Repository.ProfileRepository;
import com.Hospital.Hospital.Management.Service.ProfileService;
import com.Hospital.Hospital.Management.Service.Utility.ImageService;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ImageService imageService;

    @Override
    public String getDoctorNameBytype(String type) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getDoctorNameBytype'");
    }

    @Override
    public List<DoctorRegisterationModel> getDoctor(String doctorId) {
        List<DoctorRegisterationModel> list = new ArrayList<>();
        List<Profile> profiles = profileRepository.findByDoctorId(doctorId);
        try {
            if(!profiles.isEmpty()){
                Profile profile = profiles.get(0);
                DoctorRegisterationModel doctorModel = new DoctorRegisterationModel();
                doctorModel.setPhoto(imageService.getEncodedImageFromDirectory(profile.getPhoto()));
                doctorModel.setFirstName(profile.getUser().getFirstName());
                doctorModel.setLastName(profile.getUser().getLastName());
                doctorModel.setSpecialization(profile.getSpecialization());
                doctorModel.setEducation(profile.getEducation());
                doctorModel.setExperience(profile.getExperience());
                doctorModel.setAbout(profile.getAbout());
    
                list.add(doctorModel);
            }
            return list;
            
        } catch (Exception e) {
            throw new UnsupportedOperationException("Doctor not found.");
        }
    }
    
}
