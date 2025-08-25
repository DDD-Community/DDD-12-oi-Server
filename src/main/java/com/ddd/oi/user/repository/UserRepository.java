package com.ddd.oi.user.repository;

import com.ddd.oi.user.domain.ProviderInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ddd.oi.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByProviderInfoAndProviderId(ProviderInfo provider, String providerId);
    Optional<User> findByEmail(String email);
}
