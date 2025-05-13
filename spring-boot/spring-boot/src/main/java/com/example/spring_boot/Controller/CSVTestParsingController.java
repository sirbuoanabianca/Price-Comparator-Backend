package com.example.spring_boot.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_boot.DTO.FirstDTOExample;
import com.example.spring_boot.Services.CSVParserExample;

@RestController
@RequestMapping("/api/csv")
public class CSVTestParsingController {
    
    @Autowired
    private CSVParserExample csvParserService;

    @GetMapping("/parse-CSV")
    public ResponseEntity<?> loadFromClasspath(@RequestParam String filename) {
        try {
            List<FirstDTOExample> products = csvParserService.parseCSV(filename);
            products.forEach(System.out::println);

            return ResponseEntity.ok(products);
        } catch (IOException e) {
            e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error parsing CSV: " + e.getMessage());
        }
    }
}

