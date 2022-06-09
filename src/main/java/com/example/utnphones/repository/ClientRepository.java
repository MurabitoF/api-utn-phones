package com.example.utnphones.repository;

import com.example.utnphones.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Page<Client> findAllByDeleteAtNull(Pageable pageable);
    Optional<Client> findByAccountIdAndDeleteAtNull(Long id);
    Optional<Client> findByPhoneNumber(String phoneNumber);
}
