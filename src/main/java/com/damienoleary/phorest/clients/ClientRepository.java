package com.damienoleary.phorest.clients;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientRepository {

    public static ClientRepository createNull(List<Client> clients) {
        return new ClientRepository() {
            @Override
            public Client create(Client client) {
                return client;
            }

            @Override
            public List<Client> findAll() {
                return clients;
            }
        };
    }

    @PersistenceContext
    private EntityManager em;

    public Client create(Client client) {
        em.merge(client);
        return client;
    }

    public List<Client> findAll() {
        TypedQuery<Client> query = em.createQuery("select c from Client c", Client.class);
        return query.getResultList();
    }
}
