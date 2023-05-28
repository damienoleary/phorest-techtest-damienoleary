package com.damienoleary.phorest.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository repository;

    public static ClientService createNull() {
        return new ClientService(ClientRepository.createNull());
    }

    @Autowired
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Client create(Client client) {
        client.updateLoyaltyPoints();
        return repository.create(client);
    }

    @Transactional
    public List<Client> create(List<Client> clients) {
        return clients.stream()
                .map(this::create)
                .toList();
    }
}
