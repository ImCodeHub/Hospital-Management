package com.Hospital.Hospital.Management.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hospital.Hospital.Management.Entity.Appoinment;

@Repository
public interface AppoinmentRepository extends JpaRepository<Appoinment, String> {
    public Optional<Appoinment> findByAppoinmentId(String id);
} 
