package com.Hospital.Hospital.Management.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Hospital.Hospital.Management.Entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
    // find type
    @Query(value = "SELECT type FROM profile", nativeQuery = true)
    public List<String> findTypes();

    // find by type
    @Query(value = "SELECT * FROM profile WHERE type = :type", nativeQuery = true)
    public List<Profile> findByType(@Param("type") String type);

    // find doctor by id
    @Query(value= "SELECT * FROM PROFILE WHERE user_id =:id", nativeQuery = true)
    public List<Profile> findByDoctorId(@Param("id") String id);

}
