package com.damienoleary.phorest.clients;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientControllerTest {

    private ClientController underTest;

    @BeforeEach
    public void setUp() {
        Client client = new Client();
        client.setId("exist");
        client.setBanned(false);
        client.setGender("Female");
        client.setEmail("email");
        client.setFirstName("First Name");
        client.setLastName("last name");
        client.setPhone("Phone");
        underTest = new ClientController(ClientService.createNull(List.of(client)));
    }
    @Test
    public void testFindById_notFound() {
        ResponseEntity<ClientDTO> actual = underTest.findById("doesnotexist");

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }

    @Test
    public void testFindById_found() {
        ResponseEntity<ClientDTO> actual = underTest.findById("exist");

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        ClientDTO body = actual.getBody();
        assertThat(body.getId()).isEqualTo("exist");
        assertThat(body.getEmail()).isEqualTo("email");
        assertThat(body.getBanned()).isEqualTo(false);
        assertThat(body.getPhone()).isEqualTo("Phone");
        assertThat(body.getFirstName()).isEqualTo("First Name");
        assertThat(body.getLastName()).isEqualTo("last name");

    }
}
