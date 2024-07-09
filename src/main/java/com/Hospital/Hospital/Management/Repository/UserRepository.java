package com.Hospital.Hospital.Management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Hospital.Hospital.Management.Entity.Role;
import com.Hospital.Hospital.Management.Entity.User;

import jakarta.transaction.Transactional;

import java.util.*;

public interface UserRepository extends JpaRepository<User,String>{
    
    Optional<User>findByEmail(String email);

    Optional<User> findByMobileNo(String mobile);

    Optional<User> findByUserId(String userId);

    @Query(value ="SELECT * FROM User WHERE role = :role", nativeQuery = true)
    List<User> findByRole(@Param("role") String string);

    // @Transactional
    // @Modifying
    // @Query(value = "DELETE FROM User WHERE user_id = :userId", nativeQuery = true)
    void deleteById(String userId);



}
