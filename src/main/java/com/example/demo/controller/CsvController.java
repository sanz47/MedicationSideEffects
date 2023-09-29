package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CsvReaderService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/csv")
public class CsvController {

    private final CsvReaderService csvReaderService;

    @Autowired
    public CsvController(CsvReaderService csvReaderService) {
        this.csvReaderService = csvReaderService;
    }

    @GetMapping("/read-csv")
    public List<String[]> readCsvFile() throws IOException {
        String filePath = "D:\\IUT\\demo\\src\\main\\resources\\drugs_side_effects.csv"; // No need for the classpath prefix
        return csvReaderService.readCsv(filePath);
    }
}
