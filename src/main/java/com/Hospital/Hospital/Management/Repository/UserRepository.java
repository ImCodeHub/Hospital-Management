package com.Hospital.Hospital.Management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Hospital.Hospital.Management.Entity.User;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User,String>{
    
    Optional<User>findByEmail(String email);

    Optional<User> findByMobileNo(String mobile);

    Optional<User> findByUserId(String userId);

    @Query(value ="SELECT * FROM User WHERE role = :role", nativeQuery = true)
    List<User> findByRole(@Param("role") String string);
}       