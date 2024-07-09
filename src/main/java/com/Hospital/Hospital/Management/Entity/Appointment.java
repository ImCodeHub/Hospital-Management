package com.Hospital.Hospital.Management.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name="appoinment")
public class Appointment {
    @Id
    @Column(name="appoinment_id")
    private String appoinmentId;

    @ManyToOne
    @JoinColumn(name="patient_id")
    private User patient;

    private String reason;

    @ManyToOne
    @JoinColumn(name="doctor_id")
    private User doctor;

    @Column(name="appoinment_date")
    private LocalDate appoinmentDate;

    @Column(name="date_of_booking")
    private LocalDate dateOfBooking;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @PrePersist
    private void onCreate(){
        this.dateOfBooking = LocalDate.now();
    }

}
