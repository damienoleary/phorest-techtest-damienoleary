package com.damienoleary.phorest.purchases;

import com.damienoleary.phorest.appointments.Appointment;
import com.damienoleary.phorest.imports.csv.CSVMapping;
import com.damienoleary.phorest.imports.csv.CSVParser;
import com.opencsv.bean.CsvBindByName;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PurchaseCSVParser implements CSVParser<Purchase> {
    @Override
    public List<Purchase> parse(String csv) {
        return parse(csv, PurchaseOpenCSVMapping.class);
    }

    public static class PurchaseOpenCSVMapping implements CSVMapping<Purchase> {
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
        public Purchase getEntity() {
            Purchase purchase = new Purchase();
            purchase.setId(id);
            purchase.setName(name);
            purchase.setPrice(price);
            purchase.setLoyaltyPoints(loyaltyPoints);
            Appointment appointment = new Appointment();
            appointment.setId(appointmentId);
            purchase.setAppointment(appointment);
            return purchase;
        }
    }
}
