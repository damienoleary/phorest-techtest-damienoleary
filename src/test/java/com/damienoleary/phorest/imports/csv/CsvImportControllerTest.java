package com.damienoleary.phorest.imports.csv;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CsvImportControllerTest {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Test
    void testUpload_success() {
        CsvImportData request = new CsvImportData();
        request.setClients(loadTextFile("clients.txt"));
        request.setAppointments(loadTextFile("appointments.txt"));
        request.setPurchases(loadTextFile("purchases.txt"));
        request.setServices(loadTextFile("services.txt"));
        RequestEntity<CsvImportData> requestEntity = new RequestEntity<>(request, HttpMethod.POST,
                URI.create("http://localhost:" + port + "/api/v1/import/csv"));
        ParameterizedTypeReference<Map<String, List<String>>> responseType = new ParameterizedTypeReference<>() {
        };

        ResponseEntity<Map<String, List<String>>> actual = template.exchange(requestEntity, responseType);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
    }

    private String loadTextFile(String name) {
        try {
            return Files.readString(Paths.get(this.getClass().getResource(name).toURI()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}