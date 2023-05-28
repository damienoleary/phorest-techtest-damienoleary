package com.damienoleary.phorest.imports.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public interface CSVParser<T> {
    List<T> parse(String csv);

    default <T> List<T> parse(String csv, Class<? extends CSVMapping<T>> mappingClass) {
        if (StringUtils.isNotBlank(csv)) {
            List<CSVMapping<T>> mapped = new CsvToBeanBuilder(new StringReader(csv))
                    .withType(mappingClass)
                    .build().parse();
            return mapped.stream()
                    .map(CSVMapping::getEntity)
                    .toList();
        }
        return new ArrayList<>();
    }
}
