package com.Hospital.Hospital.Management.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.Hospital.Hospital.Management.Exception.CustomException.AppoinmentNotBookedException;
import com.Hospital.Hospital.Management.Exception.CustomException.AppoinmentNotFoundException;
import com.Hospital.Hospital.Management.Exception.CustomException.EmailAlreadyExistException;
import com.Hospital.Hospital.Management.Exception.CustomException.IncorrectPasswordException;
import com.Hospital.Hospital.Management.Exception.CustomException.InvalidEmailException;
import com.Hospital.Hospital.Management.Exception.CustomException.MobileNoAlreadyExistException;
import com.Hospital.Hospital.Management.Exception.CustomException.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static class ErrorResponse{
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException exception , WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<?> handleInvalidEmailException(InvalidEmailException exception, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<?> handleEmailAlreadyExistException(EmailAlreadyExistException exception, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MobileNoAlreadyExistException.class)
    public ResponseEntity<?> handleMobileNoAlreadyExistException(MobileNoAlreadyExistException exception, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<?> handleIncorrectPasswordException(IncorrectPasswordException exception, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AppoinmentNotBookedException.class)
    public ResponseEntity<?> handleAppoinmentNotBookedException(AppoinmentNotBookedException exception, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AppoinmentNotFoundException.class)
    public ResponseEntity<?> handleAppoinmentNotFoundException(AppoinmentNotFoundException exception, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.UNAUTHORIZED);
    }

}
