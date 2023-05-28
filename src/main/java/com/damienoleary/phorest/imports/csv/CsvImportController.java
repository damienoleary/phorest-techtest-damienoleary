package com.damienoleary.phorest.imports.csv;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/import/csv")
public class CsvImportController {
    private final CsvImportService service;

    @Autowired
    public CsvImportController(CsvImportService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> upload(@Valid @RequestBody CSVImportData importData) {
        service.processImport(importData);
        return ResponseEntity.noContent().build();
    }
}
