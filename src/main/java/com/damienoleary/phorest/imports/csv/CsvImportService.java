package com.damienoleary.phorest.imports.csv;

import com.damienoleary.phorest.clients.ClientService;
import com.damienoleary.phorest.clients.Client;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CsvImportService {

    private final CsvParserService parserService;
    private final ClientService clientService;

    @Autowired
    public CsvImportService(CsvParserService parserService, ClientService clientService) {
        this.parserService = parserService;
        this.clientService = clientService;
    }

    public void processImport(@Valid CSVImportData importData) {
        List<Client> clients = parserService.parse(importData);
        clientService.create(clients);
    }
}
