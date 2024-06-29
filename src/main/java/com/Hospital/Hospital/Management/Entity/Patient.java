package com.Hospital.Hospital.Management.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="patient")
public class Patient extends User {
    public static final boolean isEmpty = false;

    @Id
    @Column(name="patient_id", unique = true, nullable = false)
    private String patientId;
    
    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String gender;
    
    @Column(nullable = false)
    private String address;
}
