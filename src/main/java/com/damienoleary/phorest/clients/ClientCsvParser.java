package com.damienoleary.phorest.clients;

import com.damienoleary.phorest.imports.csv.CsvMapping;
import com.damienoleary.phorest.imports.csv.CsvParser;
import com.opencsv.bean.CsvBindByName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientCsvParser implements CsvParser<Client> {
    Logger logger = LoggerFactory.getLogger(ClientCsvParser.class);
    @Override
    public List<Client> parse(String csv) {
        logger.debug("Parsing csv");
        return parse(csv, ClientOpenCsvMapping.class);
    }

    public static class ClientOpenCsvMapping implements CsvMapping<Client> {
        @CsvBindByName(column = "id")
        public String id;
        @CsvBindByName(column = "first_name")
        public String firstName;
        @CsvBindByName(column = "last_name")
        public String lastName;
        @CsvBindByName(column = "email")
        public String email;
        @CsvBindByName(column = "phone")
        public String phone;
        @CsvBindByName(column = "banned")
        public boolean banned;

        @CsvBindByName(column = "gender")
        public String gender;

        public Client getEntity() {
            Client client = new Client();
            client.setId(id);
            client.setFirstName(firstName);
            client.setLastName(lastName);
            client.setEmail(email);
            client.setPhone(phone);
            client.setBanned(banned);
            client.setGender(gender);
            return client;
        }
    }
}
