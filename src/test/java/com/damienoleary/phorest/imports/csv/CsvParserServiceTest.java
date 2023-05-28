package com.damienoleary.phorest.imports.csv;

import com.damienoleary.phorest.BadRequestException;
import com.damienoleary.phorest.appointments.Appointment;
import com.damienoleary.phorest.clients.Client;
import com.damienoleary.phorest.purchases.Purchase;
import com.damienoleary.phorest.services.Service;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CsvParserServiceTest {

    private final CsvParserService underTest = new CsvParserService();

    @Test
    public void testParse_nullArg() {
        assertThrows(NullPointerException.class,
            () -> underTest.parse(null)
        );
    }

    @Test
    public void testParse_emptyArg() {
        underTest.parse(new CSVImportData());
    }

    @Test
    public void testParse_clientHasNoAppointments_noExceptionThrown() {
        CSVImportData data = new CSVImportData();
        data.setClients(loadCSV("/com/damienoleary/phorest/clients/single_valid.csv"));

        underTest.parse(data);
    }

    @Test
    public void testParse_appointmentHasNoClient_exceptionThrown() {
        CSVImportData data = new CSVImportData();
        data.setAppointments(loadCSV("/com/damienoleary/phorest/appointments/single_valid.csv"));

        BadRequestException ex = assertThrows(BadRequestException.class,
                ()-> underTest.parse(data)
        );

        assertThat(ex).hasMessage("Appointment 7416ebc3-12ce-4000-87fb-82973722ebf4 has no client");
    }

    @Test
    public void testParse_serviceHasNoAppointment_exceptionThrown() {
        CSVImportData data = new CSVImportData();
        data.setServices(loadCSV("/com/damienoleary/phorest/services/single_valid.csv"));

        BadRequestException ex = assertThrows(BadRequestException.class,
                ()-> underTest.parse(data)
        );

        assertThat(ex).hasMessage("Service f1fc7009-0c44-4f89-ac98-5de9ce58095c has no appointment");
    }

    @Test
    public void testParse_purchaseHasNoAppointment_exceptionThrown() {
        CSVImportData data = new CSVImportData();
        data.setPurchases(loadCSV("/com/damienoleary/phorest/purchases/single_valid.csv"));

        BadRequestException ex = assertThrows(BadRequestException.class,
                ()-> underTest.parse(data)
        );

        assertThat(ex).hasMessage("Purchase d2d3b92d-f9b5-48c5-bf31-88c28e3b73ac has no appointment");
    }

    @Test
    public void testParse_appointmentHasNoServices_exceptionThrown() {
        CSVImportData data = new CSVImportData();
        data.setClients(loadCSV("/com/damienoleary/phorest/clients/single_valid.csv"));
        data.setAppointments(loadCSV("/com/damienoleary/phorest/appointments/single_valid.csv"));

        BadRequestException ex = assertThrows(BadRequestException.class,
                ()-> underTest.parse(data)
        );

        assertThat(ex).hasMessage("Appointment 7416ebc3-12ce-4000-87fb-82973722ebf4 has no services");
    }

    @Test
    public void testParse_appointmentHasNoPurchases_noExceptionThrown() {
        CSVImportData data = new CSVImportData();
        data.setClients(loadCSV("/com/damienoleary/phorest/clients/single_valid.csv"));
        data.setAppointments(loadCSV("/com/damienoleary/phorest/appointments/single_valid.csv"));
        data.setServices(loadCSV("/com/damienoleary/phorest/services/single_valid.csv"));

        underTest.parse(data);
    }

    @Test
    public void testParse_minimalValid() {
        CSVImportData data = new CSVImportData();
        data.setClients(loadCSV("/com/damienoleary/phorest/clients/single_valid.csv"));
        data.setAppointments(loadCSV("/com/damienoleary/phorest/appointments/single_valid.csv"));
        data.setServices(loadCSV("/com/damienoleary/phorest/services/single_valid.csv"));
        data.setPurchases(loadCSV("/com/damienoleary/phorest/purchases/single_valid.csv"));

        List<Client> actual = underTest.parse(data);

        assertThat(actual).hasSize(1);
        Client client = actual.get(0);
        assertThat(client.getId()).isEqualTo("e0b8ebfc-6e57-4661-9546-328c644a3764");
        assertThat(client.getAppointments()).hasSize(1);
        Appointment appointment = client.getAppointments().get(0);
        assertThat(appointment.getId()).isEqualTo("7416ebc3-12ce-4000-87fb-82973722ebf4");
        assertThat(appointment.getServices()).hasSize(1);
        Service service = appointment.getServices().get(0);
        assertThat(service.getId()).isEqualTo("f1fc7009-0c44-4f89-ac98-5de9ce58095c");
        assertThat(appointment.getPurchases()).hasSize(1);
        Purchase purchase = appointment.getPurchases().get(0);
        assertThat(purchase.getId()).isEqualTo("d2d3b92d-f9b5-48c5-bf31-88c28e3b73ac");
    }

    private String loadCSV(String path) {
        try {
            return Files.readString(Paths.get(this.getClass().getResource(path).toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to load test file: " + path, e);
        }
    }
}