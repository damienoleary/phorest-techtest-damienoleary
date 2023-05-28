package com.damienoleary.phorest.clients;

import com.damienoleary.phorest.appointments.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static java.util.Comparator.*;
import static java.util.function.Predicate.not;

@Service
public class ClientService {
    private final ClientRepository repository;

    public static ClientService createNull(List<Client> clients) {
        return new ClientService(ClientRepository.createNull(clients));
    }

    @Autowired
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Client create(Client client) {
        return repository.create(client);
    }

    @Transactional
    public List<Client> create(List<Client> clients) {
        return clients.stream()
                .map(this::create)
                .toList();
    }

    public List<Client> findTop(Integer limit, LocalDate startDate) {
        return repository.findAll().stream()
                .filter(not(Client::getBanned))
                .filter(c -> c.getAppointments().stream()
                        .anyMatch(a -> !startDate.isAfter(a.getEndTime().toLocalDate())))
                .sorted(comparing(c -> countLoyaltyPoints(c, startDate), reverseOrder()))
                .limit(limit)
                .toList();
    }

    private Integer countLoyaltyPoints(Client client, LocalDate startDate) {
        return client.getAppointments().stream()
                .filter(a -> !a.getEndTime().toLocalDate().isBefore(startDate))
                .map(Appointment::calculateLoyaltyPoints)
                .reduce(Integer::sum)
                .orElse(0);
    }
}
