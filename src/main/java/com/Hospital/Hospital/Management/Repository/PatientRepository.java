package com.Hospital.Hospital.Management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hospital.Hospital.Management.Entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String>{

}
