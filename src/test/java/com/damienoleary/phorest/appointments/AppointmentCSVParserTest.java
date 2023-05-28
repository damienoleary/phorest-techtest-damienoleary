package com.damienoleary.phorest.appointments;

import com.damienoleary.phorest.clients.Client;
import com.damienoleary.phorest.imports.csv.CSVParserTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AppointmentCSVParserTest implements CSVParserTest {

    AppointmentCsvParser underTest = new AppointmentCsvParser();

    @Test
    public void testParse_nullArg_emptyListReturned() {
        List<Appointment> actual = underTest.parse(null);

        assertThat(actual).isEmpty();
    }

    @Test
    public void testParse_emptyArg_emptyListReturned() {
        List<Appointment> actual = underTest.parse("");

        assertThat(actual).isEmpty();
    }

    @Test
    public void testParse_singleRow_entityReturned() {
        String csv = loadTestFile("single_valid.csv");

        List<Appointment> actual = underTest.parse(csv);

        assertThat(actual).hasSize(1);
        Appointment appointment = actual.get(0);
        assertThat(appointment.getId()).isEqualTo("7416ebc3-12ce-4000-87fb-82973722ebf4");
        assertThat(appointment.getStartTime()).isEqualTo("2016-02-07T17:15");
        assertThat(appointment.getEndTime()).isEqualTo("2016-02-07T20:15");
        Client client = appointment.getClient();
        assertThat(client.getId()).isEqualTo("e0b8ebfc-6e57-4661-9546-328c644a3764");
    }

}