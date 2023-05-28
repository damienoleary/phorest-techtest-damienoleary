package com.damienoleary.phorest.services;

import com.damienoleary.phorest.imports.csv.CSVParserTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ServiceCSVParserTest implements CSVParserTest {
    ServiceCsvParser underTest = new ServiceCsvParser();

    @Test
    public void testParse_nullArg_emptyListReturned() {
        List<Service> actual = underTest.parse(null);

        assertThat(actual).isEmpty();
    }

    @Test
    public void testParse_emptyArg_emptyListReturned() {
        List<Service> actual = underTest.parse("");

        assertThat(actual).isEmpty();
    }

    @Test
    public void testParse_singleRow_entityReturned() {
        String csv = loadTestFile("single_valid.csv");

        List<Service> actual = underTest.parse(csv);

        assertThat(actual).hasSize(1);
        Service service = actual.get(0);
        assertThat(service.getId()).isEqualTo("f1fc7009-0c44-4f89-ac98-5de9ce58095c");
        assertThat(service.getName()).isEqualTo("Full Head Colour");
        assertThat(service.getPrice()).isEqualTo("85.0");
        assertThat(service.getLoyaltyPoints()).isEqualTo(80);
        assertThat(service.getAppointment().getId()).isEqualTo("7416ebc3-12ce-4000-87fb-82973722ebf4");
    }

}