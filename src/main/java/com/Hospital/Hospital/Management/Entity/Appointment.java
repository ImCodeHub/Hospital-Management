package com.Hospital.Hospital.Management.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="appointment_id")
    private String appointmentId;

    @ManyToOne
    @JoinColumn(name="patient_id")
    private User patient;

    private String userName;

    private String reason;

    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name="doctor_id")
    private User doctor;

    @Column(name="appointment_date")
    private LocalDate appointmentDate;

    @Column(name="date_of_booking")
    private LocalDate dateOfBooking;

    @Column(name = "appointment_time")
    private String appointmentTime;

    @Column(name="location")
    private String location;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @PrePersist
    private void onCreate(){
        this.dateOfBooking = LocalDate.now();
    }

}
