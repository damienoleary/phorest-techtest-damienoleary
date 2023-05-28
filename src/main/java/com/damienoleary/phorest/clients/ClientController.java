package com.damienoleary.phorest.clients;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable("id") String id) {
        return service.findById(id)
                .map(Client::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity update(@RequestBody ClientDTO client) {
        service.update(new Client(client));
        return ResponseEntity.noContent().build();
    }
}
