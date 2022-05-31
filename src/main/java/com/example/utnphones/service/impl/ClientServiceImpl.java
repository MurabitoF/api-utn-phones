package com.example.utnphones.service.impl;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Client;
import com.example.utnphones.repository.ClientRepository;
import com.example.utnphones.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Page<Client> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @Override
    public Client getClientById(Long id) throws NotFoundEntityException {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Client"));
    }

    @Override
    public Client saveNewClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(Long id, Client client) throws NotFoundEntityException {
        Client updatedClient = this.getClientById(id);

        if(!client.getFirstName().isEmpty() && !client.getFirstName().equals(updatedClient.getFirstName())){
            updatedClient.setFirstName(client.getFirstName());
        }

        if(!client.getSurname().isEmpty() && !client.getSurname().equals(updatedClient.getSurname())){
            updatedClient.setSurname(client.getSurname());
        }

        if(!Objects.equals(updatedClient.getCity(), client.getCity())){
            updatedClient.setCity(client.getCity());
        }

        return clientRepository.save(updatedClient);
    }

    @Override
    public void deleteClient(Long id) throws NotFoundEntityException {
        Client client = this.getClientById(id);
        client.setDeleteAt(LocalDateTime.now());
        clientRepository.save(client);
    }
}
