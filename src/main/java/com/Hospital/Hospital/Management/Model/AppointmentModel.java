package com.Hospital.Hospital.Management.Model;

import java.time.LocalDate;

import com.Hospital.Hospital.Management.Entity.AppointmentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentModel {
    private String appointmentId;

    private String patient;

    private String reason;

    private LocalDate dateOfBirth;
    
    private String doctor;

    private LocalDate appointmentDate;

    private LocalDate dateOfBooking;

    private String appointmentTime;

    private String location;

    private AppointmentStatus status;
}
