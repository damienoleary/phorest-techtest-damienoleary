package com.damienoleary.phorest.clients;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepository {

    public static ClientRepository createNull(List<Client> clients) {
        return new ClientRepository() {
            @Override
            public Client create(Client client) {
                clients.add(client);
                return client;
            }

            @Override
            public List<Client> findAll() {
                return clients;
            }

            @Override
            public Optional<Client> findById(String id) {
                return clients.stream()
                        .filter(c -> c.getId().equals(id))
                        .findFirst();
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

    public Optional<Client> findById(String id) {
        TypedQuery<Client> query = em.createQuery("select c from Client c where c.id = ?1", Client.class);
        query.setParameter(1, id);
        List<Client> clients = query.getResultList();
        if (clients.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(clients.get(0));
    }
}
