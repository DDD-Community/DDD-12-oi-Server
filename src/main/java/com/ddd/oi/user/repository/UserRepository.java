package com.ddd.oi.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ddd.oi.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
