package com.damienoleary.phorest.clients;

import com.damienoleary.phorest.BadRequestException;
import com.damienoleary.phorest.appointments.Appointment;
import com.damienoleary.phorest.purchases.Purchase;
import com.damienoleary.phorest.services.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientServiceTest {
    ClientService underTest;

    @BeforeEach
    public void setUp() {
        Client client1 = new Client();
        client1.setBanned(false);
        client1.setId("client1");
        Client client2 = new Client();
        client2.setBanned(false);
        client2.setId("client2");
        Appointment appointment1 = appointment("2019-01-01", 20, 10);
        client2.setAppointments(List.of(appointment1));
        Client client3 = new Client();
        client3.setBanned(false);
        client3.setId("client3");
        Appointment appointment3 = appointment("2020-01-01", 20, 1);
        client3.setAppointments(List.of(appointment3));
        Client client4 = new Client();
        client4.setBanned(true);
        client4.setId("client4");
        Appointment appointment4 = appointment("2018-06-01", 20, 1);
        client4.setAppointments(List.of(appointment4));
        underTest = ClientService.createNull(List.of(client1, client2, client3, client4));
    }

    private Appointment appointment(String date, int servicePoints, int purchasePoints) {
        Appointment appointment = new Appointment();
        appointment.setEndTime(LocalDate.parse(date).atTime(0, 0));
        appointment.setServices(List.of(serviceWithPoints(servicePoints)));
        appointment.setPurchases(List.of(purchaseWithPoints(purchasePoints)));
        return appointment;
    }

    private Service serviceWithPoints(int points) {
        Service service = new Service();
        service.setLoyaltyPoints(points);
        return service;
    }

    private Purchase purchaseWithPoints(int points) {
        Purchase purchase = new Purchase();
        purchase.setLoyaltyPoints(points);
        return purchase;
    }

    @Test
    public void testFindTop_noAppointmentsAfterStartDate() {
        List<Client> clients = underTest.findTop(100, LocalDate.of(2022, 1, 1));

        assertThat(clients).isEmpty();
    }

    @Test
    public void testFindTop_oneAppointmentAfterStartDate() {
        List<Client> clients = underTest.findTop(100, LocalDate.of(2020, 1, 1));

        assertThat(clients).hasSize(1);
        Client client = clients.get(0);
        assertThat(client.getId()).isEqualTo("client3");
    }

    @Test
    public void testFindTop_twoAppointmentsAfterStartDate() {
        List<Client> clients = underTest.findTop(100, LocalDate.of(2019, 1, 1));

        assertThat(clients).hasSize(2);
        assertThat(clients.get(0).getId()).isEqualTo("client2");
        assertThat(clients.get(1).getId()).isEqualTo("client3");
    }

    @Test
    public void testFindTop_ignoresBannedClients() {
        List<Client> clients = underTest.findTop(100, LocalDate.of(2018, 1, 1));

        assertThat(clients).hasSize(2);
        assertThat(clients.get(0).getId()).isEqualTo("client2");
        assertThat(clients.get(1).getId()).isEqualTo("client3");
    }

    @Test
    public void testFindById_notPresent() {
        Optional<Client> actual = underTest.findById("doesnotexist");

        assertThat(actual).isEmpty();
    }

    @Test
    public void testFindById_present() {
        Optional<Client> actual = underTest.findById("client1");

        assertThat(actual).isPresent();
        assertThat(actual.get().getId()).isEqualTo("client1");
    }

    @Test
    public void testUpdate_notExists_exceptionThrown() {
        Client client = new Client();
        client.setId("doesnotexist");

        BadRequestException ex = assertThrows(BadRequestException.class, () -> underTest.update(client));
    }

    @Test
    public void testUpdate_exists_updated() {
        Client client = new Client();
        client.setId("client1");
        client.setFirstName("Updated FirstName");
        client.setEmail("Updated Email");
        client.setGender("Updated Gender");
        client.setBanned(true);
        client.setPhone("Updated phone");

        underTest.update(client);
    }
}
