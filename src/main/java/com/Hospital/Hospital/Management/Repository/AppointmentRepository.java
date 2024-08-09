package com.Hospital.Hospital.Management.Repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Hospital.Hospital.Management.Entity.Appointment;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(value="SELECT * FROM Appointment WHERE appointment_id=:id", nativeQuery = true)
    public Optional<Appointment> findByAppointmentId(@Param("id") String id);

    @Query(value = "SELECT * FROM Appointment WHERE YEAR(appointment_date)= :year AND MONTH(appointment_date)= :month AND (patient_id = :userId OR doctor_id = :userId )", nativeQuery = true)
    public List<Appointment> findByYearAndMonth(@Param("year") int year, @Param("month") int month, @Param("userId") String userId);

}
