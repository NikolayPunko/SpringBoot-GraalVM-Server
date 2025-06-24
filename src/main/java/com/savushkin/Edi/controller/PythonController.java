package com.savushkin.Edi.controller;


import com.savushkin.Edi.dto.PythonScriptDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/python")
@Slf4j
public class PythonController {


    @PostMapping("/exec")
    public ResponseEntity<String> runPythonScript(@RequestBody PythonScriptDTO scriptDTO) {
        try {
            String command = "python3 " + scriptDTO.getPath();

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
