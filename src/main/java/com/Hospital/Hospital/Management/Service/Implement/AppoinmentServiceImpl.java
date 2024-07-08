package com.Hospital.Hospital.Management.Service.Implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Hospital.Hospital.Management.Entity.Appoinment;
import com.Hospital.Hospital.Management.Entity.AppoinmentStatus;
import com.Hospital.Hospital.Management.Entity.User;
import com.Hospital.Hospital.Management.Exception.CustomException.AppoinmentNotBookedException;
import com.Hospital.Hospital.Management.Exception.CustomException.AppoinmentNotFoundException;
import com.Hospital.Hospital.Management.Repository.AppoinmentRepository;
import com.Hospital.Hospital.Management.Service.AppoinmentService;
import com.Hospital.Hospital.Management.Service.Utility.Validator;

@Service
public class AppoinmentServiceImpl implements AppoinmentService {

    @Autowired
    private AppoinmentRepository appoinmentRepository;

    @Autowired
    private Validator validator;

    public String generateAppoinmentId() {
        String appoinmentId = validator.generateRandomNumber(5);
        Optional<Appoinment> optional = appoinmentRepository.findByAppoinmentId(appoinmentId);
        if (optional.isPresent()) {
            generateAppoinmentId();
        } else {
            return appoinmentId;
        }
        throw new RuntimeException("Id not generated");
    }

    @Override
    public String bookAppoinment(User user) {
        try {
            String appoinmentId = generateAppoinmentId();
            Appoinment appoinment = new Appoinment();
            appoinment.setAppoinmentId(appoinmentId);
            appoinment.setPatient(user);
            appoinment.setStatus(AppoinmentStatus.PENDING);
            // to save in db
            appoinmentRepository.save(appoinment);
            return "your appoinment has been booked.";
        } catch (AppoinmentNotBookedException e) {
            throw new AppoinmentNotBookedException("appoinment not booked please try again.");
        }

    }

    @Override
    public List<Appoinment> getAllAppoinments() {
        List<Appoinment> list = appoinmentRepository.findAll();
        if(list.isEmpty()){
            throw new AppoinmentNotFoundException("Appoinment not found.");
        }else{
            return list;
        }
    }

    // @Override
    // public List<Appoinment> getRecentAppoinment() {
       
    // }
}
