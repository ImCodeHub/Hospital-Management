package com.Hospital.Hospital.Management.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hospital.Hospital.Management.Entity.User;


public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User>findByEmail(String email);

    Optional<User> findByMobileNo(String mobile);

    Optional<User> findByUserId(String userId);

}
