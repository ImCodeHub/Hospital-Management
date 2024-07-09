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
    private String appoinmentId;

    private String patient;

    private String reason;

    private String doctor;

    private LocalDate appoinmentDate;

    private LocalDate dateOfBooking;

    private String location;

    private AppointmentStatus status;
}
