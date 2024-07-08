package com.Hospital.Hospital.Management.Exception;

public class CustomException {

    public static class UserNotFoundException extends RuntimeException {

        public UserNotFoundException(String message) {
            super(message);
        }

    }

    public static class InvalidEmailException extends RuntimeException {
        public InvalidEmailException(String message) {
            super(message);
        }
    }

    public static class EmailAlreadyExistException extends RuntimeException {
        public EmailAlreadyExistException(String message) {
            super(message);
        }
    }

    public static class MobileNoAlreadyExistException extends RuntimeException{
        public MobileNoAlreadyExistException(String message){
            super(message);
        }

    }

    public static class IncorrectPasswordException extends RuntimeException{
        public IncorrectPasswordException(String message){
            super(message);
        }
    }

    public static class AppoinmentNotBookedException extends RuntimeException{
        public AppoinmentNotBookedException(String message){
            super(message);
        }
    }
    public static class AppoinmentNotFoundException extends RuntimeException{
        public AppoinmentNotFoundException(String message){
            super(message);
        }
    }

    
}
