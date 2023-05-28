package com.damienoleary.phorest.imports.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public interface CsvParser<T> {
    List<T> parse(String csv);

    default List<T> parse(String csv, Class<? extends CsvMapping<T>> mappingClass) {
        if (StringUtils.isNotBlank(csv)) {
            List<CsvMapping<T>> mapped = new CsvToBeanBuilder<CsvMapping<T>>(new StringReader(csv))
                    .withType(mappingClass)
                    .build().parse();
            return mapped.stream()
                    .map(CsvMapping::getEntity)
                    .toList();
        }
        return new ArrayList<>();
    }
}
