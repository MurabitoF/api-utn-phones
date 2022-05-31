package com.example.utnphones.service;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {
    public Page<Client> getAllClients(Pageable pageable);
    public Client getClientById(Long id) throws NotFoundEntityException;
    public Client saveNewClient(Client client);
    public Client updateClient(Long id, Client client) throws NotFoundEntityException;
    public void deleteClient(Long id) throws NotFoundEntityException;
}
