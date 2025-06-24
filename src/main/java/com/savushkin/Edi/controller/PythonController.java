package com.savushkin.Edi.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/python")
@Slf4j
public class PythonController {


    @GetMapping("/exec")
    public ResponseEntity<String> runPythonScript() {
        try {
            String command = "python3 /projects/dynamic_srv/scripts/scriptPy.py";

            Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }


            int exitCode = process.waitFor();

            if (exitCode == 0) {
                return ResponseEntity.ok(output.toString());
            } else {
                log.error("Error while executing python script {}", exitCode);
                return ResponseEntity.status(500)
                        .body("Script failed with exit code: " + exitCode);
            }
        } catch (Exception e) {
            log.error("Error executing python script", e);
            return ResponseEntity.status(500)
                    .body("Error executing script: " + e.getMessage());
        }
    }


}
