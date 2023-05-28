package com.damienoleary.phorest.appointments;

import com.damienoleary.phorest.clients.Client;
import com.damienoleary.phorest.purchases.Purchase;
import com.damienoleary.phorest.services.Service;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Appointment {
    @Id
    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @ManyToOne(optional = false)
    private Client client;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Purchase> purchases = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Service> services = new ArrayList<>();

    public int calculateLoyaltyPoints() {
        int serviceLoyaltyPoints = getServices().stream()
                .map(Service::getLoyaltyPoints)
                .reduce(Integer::sum)
                .orElse(0);
        int purchaseLoyaltyPoints = getPurchases().stream()
                .map(Purchase::getLoyaltyPoints)
                .reduce(Integer::sum)
                .orElse(0);
        return serviceLoyaltyPoints + purchaseLoyaltyPoints;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
