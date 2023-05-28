package com.damienoleary.phorest.clients;

import com.damienoleary.phorest.BadRequestException;
import com.damienoleary.phorest.appointments.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public Optional<Client> findById(String id) {
        return repository.findById(id);
    }

    @Transactional
    public void update(Client client) {
        Client existing = findById(client.getId())
                .orElseThrow(() ->
                        new BadRequestException(String.format("Client with id %s does not exist", client.getId())));
        existing.setPhone(client.getPhone());
        existing.setLastName(client.getLastName());
        existing.setGender(client.getGender());
        existing.setEmail(client.getEmail());
        existing.setBanned(client.getBanned());
        existing.setFirstName(client.getFirstName());
    }
}
