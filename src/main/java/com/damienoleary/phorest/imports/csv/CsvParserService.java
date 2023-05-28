package com.damienoleary.phorest.imports.csv;

import com.damienoleary.phorest.BadRequestException;
import com.damienoleary.phorest.appointments.AppointmentCsvParser;
import com.damienoleary.phorest.clients.ClientCsvParser;
import com.damienoleary.phorest.appointments.Appointment;
import com.damienoleary.phorest.clients.Client;
import com.damienoleary.phorest.purchases.Purchase;
import com.damienoleary.phorest.services.Service;
import com.damienoleary.phorest.purchases.PurchaseCsvParser;
import com.damienoleary.phorest.services.ServiceCsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class CsvParserService {
    private final CsvParser<Client> clientCSVParser;
    private final CsvParser<Appointment> appointmentCSVParser;
    private final CsvParser<Service> serviceCSVParser;
    private final CsvParser<Purchase> purchaseCSVParser;

    @Autowired
    public CsvParserService(CsvParser<Client> clientCSVParser, CsvParser<Appointment> appointmentCSVParser,
                            CsvParser<Service> serviceCSVParser, CsvParser<Purchase> purchaseCSVParser) {
        this.clientCSVParser = clientCSVParser;
        this.appointmentCSVParser = appointmentCSVParser;
        this.serviceCSVParser = serviceCSVParser;
        this.purchaseCSVParser = purchaseCSVParser;
    }

    public CsvParserService() {
        this(new ClientCsvParser(), new AppointmentCsvParser(), new ServiceCsvParser(), new PurchaseCsvParser());
    }

    public List<Client> parse(CsvImportData data) {
        List<Client> clients = clientCSVParser.parse(data.getClients());
        Map<String, Client> clientsById = clients.stream()
                .collect(toMap(Client::getId, identity()));
        List<Appointment> appointments = appointmentCSVParser.parse(data.getAppointments());
        Map<String, Appointment> appointmentsById = appointments.stream()
                .collect(toMap(Appointment::getId, identity()));
        mergeAppointments(clientsById, appointments);
        List<Service> services = serviceCSVParser.parse(data.getServices());
        mergeServices(appointmentsById, services);
        validateAppointments(appointments);
        List<Purchase> purchases = purchaseCSVParser.parse(data.getPurchases());
        mergePurchases(appointmentsById, purchases);
        return clients;
    }

    private static void mergePurchases(Map<String, Appointment> appointmentsById, List<Purchase> purchases) {
        purchases.forEach(purchase -> {
            Appointment appointment = appointmentsById.get(purchase.getAppointment().getId());
            if (appointment == null) {
                throw new BadRequestException(String.format("Purchase %s has no appointment", purchase.getId()));
            }
            appointment.getPurchases().add(purchase);
            purchase.setAppointment(appointment);
        });
    }

    private static void validateAppointments(List<Appointment> appointments) {
        appointments.stream()
                .filter(appointment -> appointment.getServices().isEmpty())
                .findAny()
                .ifPresent(appointment -> {
                    throw new BadRequestException(String.format("Appointment %s has no services", appointment.getId()));
                });
    }

    private static void mergeServices(Map<String, Appointment> appointmentsById, List<Service> services) {
        services.forEach(service -> {
            Appointment appointment = appointmentsById.get(service.getAppointment().getId());
            if (appointment == null) {
                throw new BadRequestException(String.format("Service %s has no appointment", service.getId()));
            }
            appointment.getServices().add(service);
            service.setAppointment(appointment);
        });
    }

    private static void mergeAppointments(Map<String, Client> clientsById, List<Appointment> appointments) {
        appointments.forEach(appointment -> {
            Client client = clientsById.get(appointment.getClient().getId());
            if (client == null) {
                throw new BadRequestException(String.format("Appointment %s has no client", appointment.getId()));
            }
            client.getAppointments().add(appointment);
            appointment.setClient(client);
        });
    }
}
