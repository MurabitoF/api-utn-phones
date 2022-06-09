package com.example.utnphones.repository;

import com.example.utnphones.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountIdAndDeleteAtNull(Long id);
}
