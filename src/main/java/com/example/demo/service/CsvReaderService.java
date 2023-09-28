package com.example.demo.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.List;

@Service
public class CsvReaderService {

    public List<String[]> readCsv(String filePath) {
        try (FileReader fileReader = new FileReader(filePath);
             CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build()) {
            return csvReader.readAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
