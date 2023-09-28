package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.CsvReaderService;
import java.util.List;

@RestController
public class CsvController {

    private final CsvReaderService csvReaderService;

    @Autowired
    public CsvController(CsvReaderService csvReaderService) {
        this.csvReaderService = csvReaderService;
    }

    @GetMapping("/read-csv")
    public List<String[]> readCsvFile() {
        String filePath = "./drugs_side_effects_drugs_com.csv"; // Update the file path
        return csvReaderService.readCsv(filePath);
    }
}
