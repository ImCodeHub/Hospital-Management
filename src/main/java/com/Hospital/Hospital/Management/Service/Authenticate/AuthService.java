package com.Hospital.Hospital.Management.Service.Authenticate;

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
import com.Hospital.Hospital.Management.Service.Utility.Validator;

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

    public AuthenticationResponse register(RegisterRequest registerRequest) {

        String email = registerRequest.getEmail();
        String mobile = registerRequest.getMobileNo();
        String uniqueId = validator.generateID(registerRequest.getRole());

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
                .speciality(registerRequest.getSpeciality())
                .discription(registerRequest.getDiscription())
                .address(registerRequest.getAddress())
                .role(registerRequest.getRole())
                .build();

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

}
