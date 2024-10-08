package com.Hospital.Hospital.Management.Model;

import java.time.LocalDate;

import com.Hospital.Hospital.Management.Entity.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

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

    private int age;
    
    private String doctorId;

    private String doctor;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private LocalDate appointmentDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private LocalDate dateOfBooking;

    private String appointmentTime;

    private String location;

    private AppointmentStatus status;
}
