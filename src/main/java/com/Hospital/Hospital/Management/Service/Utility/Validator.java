package com.Hospital.Hospital.Management.Service.Utility;

import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Hospital.Hospital.Management.Entity.Role;
import com.Hospital.Hospital.Management.Entity.User;
import com.Hospital.Hospital.Management.Repository.UserRepository;

@Service
public class Validator {

    @Autowired
    private UserRepository userRepository;

    public boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+$";
        boolean isValid = email.matches(emailRegex);
        return isValid;
    }

    public boolean isEmailUnique(String email) {
        Optional<User> optional = userRepository.findByEmail(email);
        boolean isUnique = optional.isEmpty();
        return isUnique;
    }

    public boolean isMobileNoUniue(String mobile) {
        Optional<User> optional = userRepository.findByMobileNo(mobile);
        boolean isUnique = optional.isEmpty();
        return isUnique;
    }

    public String generateID(Role role) {
        String prefix = "";
        switch (role) {
            case DOCTOR:
                prefix = "DR";
                break;
            case PATIENT:
                prefix = "PT";
                break;
            case NURSE:
                prefix = "NR";
                break;
            case ADMIN:
                prefix = "AD";
                break;

            default:
                return null;

        }
        return generateRandomId(prefix);

    }

    private String generateRandomId(String prefix) {
        String userid = prefix + generateRandomNumber(8);

        Optional<User> optional = userRepository.findByUserId(userid);
        if (optional.isPresent()) {
            generateRandomId(prefix);
        } else {
            return userid;
        }
        throw new RuntimeException("Unique id not generated.");
    }

    public String generateRandomNumber(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i <= length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
