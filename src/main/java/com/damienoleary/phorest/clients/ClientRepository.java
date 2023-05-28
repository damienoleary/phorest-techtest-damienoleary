package com.damienoleary.phorest.clients;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientRepository {

    public static ClientRepository createNull() {
        return new ClientRepository() {
            public Client create(Client client) {
                return client;
            }
        };
    }

    @PersistenceContext
    private EntityManager em;

    public Client create(Client client) {
        em.merge(client);
        return client;
    }
}
