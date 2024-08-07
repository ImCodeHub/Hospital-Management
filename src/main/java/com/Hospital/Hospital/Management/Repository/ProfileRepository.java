package com.Hospital.Hospital.Management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hospital.Hospital.Management.Entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
}
