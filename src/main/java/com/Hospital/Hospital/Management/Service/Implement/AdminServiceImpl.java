package com.Hospital.Hospital.Management.Service.Implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Hospital.Hospital.Management.Entity.User;
import com.Hospital.Hospital.Management.Exception.CustomException.UserNotFoundException;
import com.Hospital.Hospital.Management.Repository.UserRepository;
import com.Hospital.Hospital.Management.Service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllDoctors() {
        List<User> list = userRepository.findByRole("DOCTOR");
        return list;
    }

    @Override
    public List<User> getAllPatient() {
        List<User> list = userRepository.findByRole("PATIENT");
        return list;
    }

    @Override
    public Boolean blacklist(String userId) {
        Optional<User> optional = userRepository.findByUserId(userId);
        if(optional.isPresent()){
            User user = optional.get();
            user.setBlacklisted(true); // Set blacklisted status to true
            userRepository.save(user); // Save the updated user entity
            return true;
        }
        throw new UserNotFoundException("User not found by this ID: "+userId);
    }

    @Override
    public Boolean whitelist(String userId){
        Optional<User> optional = userRepository.findByUserId(userId);
        if(optional.isPresent()){
            User user = optional.get();
            user.setBlacklisted(false); // Set blacklisted status to true
            userRepository.save(user); // Save the updated user entity
            return true;
        }
        throw new UserNotFoundException("User not found by this ID: "+userId);
    }
}
