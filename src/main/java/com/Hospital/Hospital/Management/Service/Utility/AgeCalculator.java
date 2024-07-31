package com.Hospital.Hospital.Management.Service.Utility;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Service;

@Service
public class AgeCalculator {
    public static int calculateAge(LocalDate dateOfBirth){
        LocalDate today = LocalDate.now();
        Period age = Period.between(dateOfBirth, today);
        return age.getYears();
    }
}
