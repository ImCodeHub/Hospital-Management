package com.Hospital.Hospital.Management.Repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Hospital.Hospital.Management.Entity.Appointment;


@Repository
public interface AppoinmentRepository extends JpaRepository<Appointment, String> {
    public Optional<Appointment> findByAppoinmentId(String id);

    @Query(value = "SELECT * FROM Appoinment WHERE YEAR(date_of_booking)= :year AND MONTH(date_of_booking)= :month AND (patient_id = :userId OR doctor_id = :userId )", nativeQuery = true)
    public List<Appointment> findByYearAndMonth(@Param("year") int year, @Param("month") int month, @Param("userId") String userId);
}
