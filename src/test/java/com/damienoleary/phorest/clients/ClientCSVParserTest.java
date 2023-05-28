package com.damienoleary.phorest.clients;

import com.damienoleary.phorest.imports.csv.CSVParserTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientCSVParserTest implements CSVParserTest {

    private final ClientCsvParser underTest = new ClientCsvParser();

    @Test
    public void testParse_nullArg_returnsEmptyList() {
        List<Client> actual = underTest.parse(null);

        assertThat(actual).isEmpty();
    }

    @Test
    public void testParse_emptyArg_returnsEmptyList() {
        List<Client> actual = underTest.parse("");

        assertThat(actual).isEmpty();
    }

    @Test
    public void testParse_singleRow_returnsEntity() throws Exception {
        String csv = loadTestFile("single_valid.csv");

        List<Client> actual = underTest.parse(csv);

        assertThat(actual).hasSize(1);
        Client client = actual.get(0);
        assertThat(client.getId()).isEqualTo("e0b8ebfc-6e57-4661-9546-328c644a3764");
        assertThat(client.getFirstName()).isEqualTo("Dori");
        assertThat(client.getLastName()).isEqualTo("Dietrich");
        assertThat(client.getEmail()).isEqualTo("patrica@keeling.net");
        assertThat(client.getPhone()).isEqualTo("(272) 301-6356");
        assertThat(client.getBanned()).isFalse();
        assertThat(client.getGender()).isEqualTo("Male");
    }
}