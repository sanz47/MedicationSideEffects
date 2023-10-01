package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class PythonScriptController {
    private static final Logger logger = LoggerFactory.getLogger(PythonScriptController.class);

    @GetMapping("/runPythonScript")
    public String runPythonScript(@RequestParam String input) throws IOException, InterruptedException {
        // Define the Python script location
        String pythonScriptPath = "src/main/script/model.py";

        logger.info("Received input: {}", input);

        // Log that the Python script execution is starting
        logger.info("Starting the Python script execution.");

        // Build the command to execute the Python script
        ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath);
        processBuilder.redirectErrorStream(true);

        // Start the process
        Process process = processBuilder.start();
        logger.info("Python script process started.");

        // Provide input to the Python script
        process.getOutputStream().write(input.getBytes());
        process.getOutputStream().close();

        // Read the output of the Python script
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        // Log the output of the Python script
        logger.info("Python Script Output:\n{}", output.toString());

        // Wait for the Python script to complete
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            return output.toString();
        } else {
            logger.error("Error running Python script.");
            return "Error running Python script";
        }
    }
}
