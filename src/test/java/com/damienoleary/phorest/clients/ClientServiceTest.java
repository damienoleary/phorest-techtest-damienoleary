package com.damienoleary.phorest.clients;

import com.damienoleary.phorest.appointments.Appointment;
import com.damienoleary.phorest.purchases.Purchase;
import com.damienoleary.phorest.services.Service;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientServiceTest {
    ClientService clientService = ClientService.createNull();

    @Test
    public void testSave_accumulatesPoints() {
        Client client = new Client();
        Appointment appointment = new Appointment();
        appointment.setClient(client);
        client.getAppointments().add(appointment);
        Service service = new Service();
        appointment.getServices().add(service);
        service.setAppointment(appointment);
        service.setLoyaltyPoints(5);
        Purchase purchase = new Purchase();
        purchase.setLoyaltyPoints(10);
        appointment.getPurchases().add(purchase);
        purchase.setAppointment(appointment);

        Client saved = clientService.create(client);

        assertThat(saved.getLoyaltyPoints()).isEqualTo(15);
    }

    @Test
    public void testSave_updatesPoints() {
        Client client = new Client();
        Appointment appointment = new Appointment();
        appointment.setClient(client);
        client.getAppointments().add(appointment);
        Service service = new Service();
        appointment.getServices().add(service);
        service.setAppointment(appointment);
        service.setLoyaltyPoints(5);
        Purchase purchase = new Purchase();
        purchase.setLoyaltyPoints(10);
        appointment.getPurchases().add(purchase);
        purchase.setAppointment(appointment);
        Client saved = clientService.create(client);
        saved.getAppointments().get(0).getPurchases().get(0).setLoyaltyPoints(8);
        saved = clientService.create(saved);

        assertThat(saved.getAppointments().get(0).getPurchases().get(0).getLoyaltyPoints()).isEqualTo(8);
        assertThat(saved.getLoyaltyPoints()).isEqualTo(13);
    }
}
