package com.damienoleary.phorest.clients;

import com.damienoleary.phorest.appointments.Appointment;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Client {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Boolean banned;
    private String gender;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Appointment> appointments = new ArrayList<>();

    public Client() {}

    public Client(ClientDTO dto) {
        setBanned(dto.getBanned());
        setEmail(dto.getEmail());
        setFirstName(dto.getFirstName());
        setLastName(dto.getLastName());
        setGender(dto.getGender());
        setPhone(dto.getPhone());
        setId(dto.getId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ClientDTO toDTO() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setBanned(banned);
        clientDTO.setEmail(email);
        clientDTO.setFirstName(firstName);
        clientDTO.setLastName(lastName);
        clientDTO.setGender(gender);
        clientDTO.setPhone(phone);
        clientDTO.setId(id);
        return clientDTO;
    }
}
