package com.example.spring_boot.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_boot.DTO.CSVParsingRequest;
import com.example.spring_boot.Services.CSVParserService;

@RestController
@RequestMapping("/csv")
public class CSVParserController {
    
    @Autowired
    private CSVParserService csvParserService;

    @PostMapping("/parse-CSV")
    public ResponseEntity<?> loadFromClasspath(@RequestBody CSVParsingRequest request) {
        try {
            csvParserService.parseCSV(request.getFilename());
            return ResponseEntity.ok("CSV file parsed successfully and data saved to database.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error parsing CSV: " + e.getMessage());
        }
    }
}

