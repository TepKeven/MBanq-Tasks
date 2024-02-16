package com.tepkeven.bankservices.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tepkeven.bankservices.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
 
    Optional<User> findByPhone(String phone);
}
