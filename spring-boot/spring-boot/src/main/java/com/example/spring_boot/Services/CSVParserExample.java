package com.example.spring_boot.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.spring_boot.DTO.FirstDTOExample;

@Service
public class CSVParserExample {
    public List<FirstDTOExample> parseCSV(String filename) throws IOException {
        List<FirstDTOExample> products = new ArrayList<>();

        ClassPathResource resource = new ClassPathResource("data/" + filename);
        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder()
                        .setDelimiter(';')
                        .setHeader()
                        .setIgnoreHeaderCase(true)
                        .setTrim(true)
                        .build())) {

            for (CSVRecord record : csvParser) {
                FirstDTOExample product = new FirstDTOExample();
                product.setId(record.get(0));
                product.setName(record.get(1));
                product.setCategory(record.get(2));
                product.setBrand(record.get(3));
                product.setQuantity(Double.parseDouble(record.get(4)));
                product.setUnit(record.get(5));
                product.setPrice(Double.parseDouble(record.get(6)));
                product.setCurrency(record.get(7));

                products.add(product);
            }
        }

        return products;
    }
}

