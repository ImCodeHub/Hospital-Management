package com.Hospital.Hospital.Management.Service.Authenticate;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Hospital.Hospital.Management.Entity.User;
import com.Hospital.Hospital.Management.Exception.CustomException.EmailAlreadyExistException;
import com.Hospital.Hospital.Management.Exception.CustomException.IncorrectPasswordException;
import com.Hospital.Hospital.Management.Exception.CustomException.InvalidEmailException;
import com.Hospital.Hospital.Management.Exception.CustomException.MobileNoAlreadyExistException;
import com.Hospital.Hospital.Management.Exception.CustomException.UserNotFoundException;
import com.Hospital.Hospital.Management.Model.AuthenticationRequest;
import com.Hospital.Hospital.Management.Model.RegisterRequest;
import com.Hospital.Hospital.Management.Repository.UserRepository;
import com.Hospital.Hospital.Management.Service.Utility.EmailService;
import com.Hospital.Hospital.Management.Service.Utility.OTP;
import com.Hospital.Hospital.Management.Service.Utility.Validator;

import jakarta.mail.MessagingException;
import lombok.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final Validator validator;

    @Autowired
    private final EmailService emailService;

    public AuthenticationResponse register(RegisterRequest registerRequest) {

        String email = registerRequest.getEmail();
        String mobile = registerRequest.getMobileNo();
        String uniqueId = validator.generateID(registerRequest.getRole());
        String subject = "Registration at Hospital";
        String text = emailService.greetingMail(registerRequest.getFirstName());

        if (!validator.isValidEmail(email)) {
            throw new InvalidEmailException("Email is invalid kinldy Enter valid E-mail id");
        }

        if (!validator.isEmailUnique(email)) {
            throw new EmailAlreadyExistException("the Email is already Registered.");
        }

        if (!validator.isMobileNoUniue(mobile)) {
            throw new MobileNoAlreadyExistException("Mobile number is already exist.");
        }

        var user = User.builder()
                .userId(uniqueId)
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .gender(registerRequest.getGender())
                .mobileNo(mobile)
                .blacklisted(false)
                .email(email)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole())
                .build();
        try {
            emailService.sendEmail(email, subject, text);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            // First step: Validate the request
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(
                            () -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));

            // Check if the user is blacklisted
            if (user.isBlacklisted()) {
                throw new RuntimeException("User is blacklisted and cannot log in.");
            }
            // Second step: Verify the user is present in the database
            // Verify whether the user is present in the database and the password is
            // correct
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));

            // Generate the JWT token
            String jwtToken = jwtService.generateToken(user);

            // Return the response with the access token
            return AuthenticationResponse.builder().accessToken(jwtToken).build();

        } catch (BadCredentialsException e) {
            // handle incorrect password exception
            throw new IncorrectPasswordException("Incorrect password. please try again.");
        } catch (UsernameNotFoundException e) {
            // handle user not found exception
            throw new UserNotFoundException("User not found with provided Email.");
        } catch (AuthenticationException e) {
            // handle authentication failed
            throw new RuntimeException("Authentication failed. Please try again.");
        }
    }

    public String getOtp(String email) {
        Boolean response = validator.isEmailAvailable(email);
        if (response) {
            String otp = OTP.generateOtp(email); // get otp using generateOtp method.
            // sending email

            String subject = "E-mail for OTP varification"; // this is mail subject

            String text = emailService.otpMail(otp); // this is mail text body.

            try {
                emailService.sendEmail(email, subject, text);
            } catch (MessagingException e) {
                throw new RuntimeException("Failed to send email", e);
            }
            return "OTP has been sent to your E-mail id";

        } else {
            throw new UserNotFoundException("User not found by this E-mail");
        }
    }

    public Boolean varifyOtp(String userEmail, String enteredOtp) {
        Boolean response = OTP.validateOtp(userEmail, enteredOtp);
        return response;
    }

    public Boolean updatePassword(String userEmail, String newPassword) {
        Optional<User> optional = userRepository.findByEmail(userEmail);
        if (optional.isPresent()) {
            User user = optional.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            // return true if password has updated.
            return true;
        } else {
            throw new UsernameNotFoundException("User not found with email: " + userEmail);
        }
    }

}
