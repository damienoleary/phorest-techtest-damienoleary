package com.damienoleary.phorest.services;

import com.damienoleary.phorest.appointments.Appointment;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Service {
    @Id
    private String id;
    private String name;
    private BigDecimal price;
    private int loyaltyPoints;
    @ManyToOne(optional = false)
    private Appointment appointment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
