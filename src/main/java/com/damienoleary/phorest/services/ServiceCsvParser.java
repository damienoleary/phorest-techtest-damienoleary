package com.damienoleary.phorest.services;

import com.damienoleary.phorest.appointments.Appointment;
import com.damienoleary.phorest.imports.csv.CsvMapping;
import com.damienoleary.phorest.imports.csv.CsvParser;
import com.opencsv.bean.CsvBindByName;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ServiceCsvParser implements CsvParser<Service> {
    @Override
    public List<Service> parse(String csv) {
        return parse(csv, ServiceOpenCSVMapping.class);
    }

    public static class ServiceOpenCSVMapping implements CsvMapping<Service> {

        @CsvBindByName(column = "id")
        public String id;
        @CsvBindByName(column = "appointment_id")
        public String appointmentId;
        @CsvBindByName(column = "name")
        public String name;
        @CsvBindByName(column = "price")
        public BigDecimal price;
        @CsvBindByName(column = "loyalty_points")
        public Integer loyaltyPoints;

        @Override
        public Service getEntity() {
            Service service = new Service();
            service.setId(id);
            service.setName(name);
            service.setLoyaltyPoints(loyaltyPoints);
            service.setPrice(price);
            Appointment appointment = new Appointment();
            appointment.setId(appointmentId);
            service.setAppointment(appointment);
            return service;
        }
    }
}
