package com.damienoleary.phorest.imports.csv;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public interface CSVParserTest {
    default String loadTestFile(String fileName) {
        try {
            return Files.readString(Paths.get(this.getClass().getResource(fileName).toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to load test file: " + fileName, e);
        }
    }
}
