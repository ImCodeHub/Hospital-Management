package com.Hospital.Hospital.Management.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctor")
public class Doctor extends User{
    @Id
    @Column(name = "doctor_id")
    private String doctorId;

    @NotNull
    private String speciality;

    @NotNull
    @Column(length = 500)
    private String discription;

    @NotNull
    @Column(name = "date_of_joining")
    private LocalDate dateOfJoining;

    @Enumerated(EnumType.STRING)
    private Role role;
}
