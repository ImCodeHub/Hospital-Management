package com.Hospital.Hospital.Management.Service.Implement;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Hospital.Hospital.Management.Entity.Profile;
import com.Hospital.Hospital.Management.Entity.User;
import com.Hospital.Hospital.Management.Exception.CustomException.EmailAlreadyExistException;
import com.Hospital.Hospital.Management.Exception.CustomException.InvalidEmailException;
import com.Hospital.Hospital.Management.Exception.CustomException.MobileNoAlreadyExistException;
import com.Hospital.Hospital.Management.Exception.CustomException.UserNotFoundException;
import com.Hospital.Hospital.Management.Model.DoctorRegisterationModel;
import com.Hospital.Hospital.Management.Repository.ProfileRepository;
import com.Hospital.Hospital.Management.Repository.UserRepository;
import com.Hospital.Hospital.Management.Service.AdminService;
import com.Hospital.Hospital.Management.Service.Utility.EmailService;
import com.Hospital.Hospital.Management.Service.Utility.ImageService;
import com.Hospital.Hospital.Management.Service.Utility.Validator;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private Validator validator;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String registerDoctor(DoctorRegisterationModel doctorModel, MultipartFile imageFile) throws IOException {
        // create imge path.
        String imagePath = imageService.saveImage(imageFile);
        try {
            String email = doctorModel.getEmail();
            String mobile = doctorModel.getMobileNo();
            String uniqueId = validator.generateID(doctorModel.getRole());

            if (!validator.isValidEmail(email)) {
                throw new InvalidEmailException("Email is invalid kinldy Enter valid E-mail id");
            }

            if (!validator.isEmailUnique(email)) {
                throw new EmailAlreadyExistException("the Email is already Registered.");
            }

            if (!validator.isMobileNoUniue(mobile)) {
                throw new MobileNoAlreadyExistException("Mobile number is already exist.");
            }

            User user = new User();
            user.setUserId(uniqueId);
            user.setFirstName(doctorModel.getFirstName());
            user.setLastName(doctorModel.getLastName());
            user.setEmail(email);
            user.setMobileNo(mobile);
            user.setGender(doctorModel.getGender());
            user.setPassword(doctorModel.getPassword());
            user.setRole(doctorModel.getRole());
            user.setBlacklisted(false);
            user.setPassword(passwordEncoder.encode(doctorModel.getPassword()));

            userRepository.save(user);

            Profile profile = new Profile();
            profile.setAbout(doctorModel.getAbout());
            profile.setEducation(doctorModel.getEducation());
            profile.setExperience(doctorModel.getExperience());
            profile.setSpecialization(doctorModel.getSpecialization());
            profile.setType(doctorModel.getType());
            profile.setUser(user);

            // set image path
            profile.setPhoto(imagePath);

            profileRepository.save(profile);

            return "Doctor has been Registered.";

        } catch (RuntimeException e) {
            logger.error("Error registering doctor: {}", e.getMessage(), e);
            throw new RuntimeException ("Doctor not Register.",e);
        }

    }

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
        if (optional.isPresent()) {
            User user = optional.get();
            user.setBlacklisted(true); // Set blacklisted status to true
            userRepository.save(user); // Save the updated user entity
            return true;
        }
        throw new UserNotFoundException("User not found by this ID: " + userId);
    }

    @Override
    public Boolean whitelist(String userId) {
        Optional<User> optional = userRepository.findByUserId(userId);
        if (optional.isPresent()) {
            User user = optional.get();
            user.setBlacklisted(false); // Set blacklisted status to true
            userRepository.save(user); // Save the updated user entity
            return true;
        }
        throw new UserNotFoundException("User not found by this ID: " + userId);
    }

}
