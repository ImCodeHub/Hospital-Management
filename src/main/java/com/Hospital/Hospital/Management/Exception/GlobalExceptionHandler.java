package com.Hospital.Hospital.Management.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.Hospital.Hospital.Management.Exception.CustomException.EmailAlreadyExistException;
import com.Hospital.Hospital.Management.Exception.CustomException.IncorrectPasswordException;
import com.Hospital.Hospital.Management.Exception.CustomException.InvalidEmailException;
import com.Hospital.Hospital.Management.Exception.CustomException.MobileNoAlreadyExistException;
import com.Hospital.Hospital.Management.Exception.CustomException.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException exception , WebRequest request){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<?> handleInvalidEmailException(InvalidEmailException exception, WebRequest request){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<?> handleEmailAlreadyExistException(EmailAlreadyExistException exception, WebRequest request){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MobileNoAlreadyExistException.class)
    public ResponseEntity<?> handleMobileNoAlreadyExistException(MobileNoAlreadyExistException exception, WebRequest request){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<?> handleIncorrectPasswordException(IncorrectPasswordException exception, WebRequest request){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.UNAUTHORIZED);
    }

}
