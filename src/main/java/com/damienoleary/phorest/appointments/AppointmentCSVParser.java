package com.damienoleary.phorest.appointments;

import com.damienoleary.phorest.clients.Client;
import com.damienoleary.phorest.imports.csv.CSVMapping;
import com.damienoleary.phorest.imports.csv.CSVParser;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Component
public class AppointmentCSVParser implements CSVParser<Appointment> {
    @Override
    public List<Appointment> parse(String csv) {
        return parse(csv, AppointmentOpenCSVMapping.class);
    }

    public static class AppointmentOpenCSVMapping implements CSVMapping<Appointment> {
        @CsvBindByName(column = "id")
        public String id;
        @CsvBindByName(column = "client_id")
        public String clientId;
        @CsvBindByName(column = "start_time")
        @CsvDate("yyyy-MM-dd HH:mm:ss Z")
        public ZonedDateTime startTime;
        @CsvBindByName(column = "end_time")
        @CsvDate("yyyy-MM-dd HH:mm:ss Z")
        public ZonedDateTime endTime;

        @Override
        public Appointment getEntity() {
            Appointment appointment = new Appointment();
            appointment.setId(id);
            appointment.setStartTime(startTime.toLocalDateTime());
            appointment.setEndTime(endTime.toLocalDateTime());
            Client client = new Client();
            client.setId(clientId);
            appointment.setClient(client);
            return appointment;
        }
    }
}
