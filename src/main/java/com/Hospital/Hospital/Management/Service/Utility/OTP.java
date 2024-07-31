package com.Hospital.Hospital.Management.Service.Utility;

import java.util.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.Data;

@Service
@Component
public class OTP {
    private static final Map<String, OTPInfo> otpMap = new HashMap<>();
    private static final long OTP_VALIDITY_INT_MINUTES = 2;

    @Data
    public static class OTPInfo{
        private String otp;
        private long generatedTime;

        public OTPInfo(String otp, long generatedTime){
            this.otp = otp;
            this.generatedTime = generatedTime;
        }
        

    }

    public static String generateOtp(String email){
        Random random = new Random();

        int randomNumber = random.nextInt(999999);
        long currentTime = System.currentTimeMillis(); 
        String otp = Integer.toString(randomNumber);

        while(otp.length()<6){
            otp = "0"+ otp;
        }

        otpMap.put(email, new OTPInfo(otp, currentTime));
        
        return otp;
    }

    public static Boolean validateOtp(String userEmail, String enteredOtp){
        OTPInfo otpInfo = otpMap.get(userEmail);
        if(otpInfo == null){
           return false; 
        }

        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - otpInfo.getGeneratedTime();

        if(elapsedTime > TimeUnit.MINUTES.toMillis(OTP_VALIDITY_INT_MINUTES)){
            return false;
        }

        return otpInfo.getOtp().equals(enteredOtp);
    }

}
