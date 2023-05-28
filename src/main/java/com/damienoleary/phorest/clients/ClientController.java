package com.damienoleary.phorest.clients;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping("/top")
    public List<ClientDTO> findTop(@RequestParam(value = "start_date", required = true) LocalDate startDate,
                                   @RequestParam(value = "limit", required = true) Integer limit) {
        return service.findTop(limit, startDate).stream()
                .map(Client::toDTO)
                .toList();
    }
}
