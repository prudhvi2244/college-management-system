package com.cms.in.repository;


import com.cms.in.entity.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistration,Integer> {
    Optional<UserRegistration> findByUsername(String username);
}
