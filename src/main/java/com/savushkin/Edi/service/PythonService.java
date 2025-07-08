package com.savushkin.Edi.service;

import com.savushkin.Edi.dto.PyCreateScriptDTO;
import com.savushkin.Edi.dto.PyExecReqDTO;
import com.savushkin.Edi.dto.PyExecRespDTO;
import com.savushkin.Edi.exceptions.PyScriptException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.nio.file.attribute.PosixFilePermission.*;

@Slf4j
@Service
public class PythonService {

    @Value("${app.directory.scripts}")
    private String SCRIPT_DIRECTORY;

    public PyExecRespDTO executeScript(PyExecReqDTO execReqDTO) {
        validateScriptPath(SCRIPT_DIRECTORY + execReqDTO.getFilename());

        Process process = null;
        try {


            String[] command = {"/bin/bash", "-c", "python3 " + SCRIPT_DIRECTORY + execReqDTO.getFilename() + " " + String.join(" ", execReqDTO.getParameters())};
            process = startProcess(command);

            String output = readProcessOutput(process);

            boolean finished = process.waitFor(1, TimeUnit.MINUTES);

            if (!finished) {
                throw new PyScriptException("Script timed out", output);
            }

            int exitCode = process.exitValue();
            if (exitCode != 0) {
                throw new PyScriptException("Script failed with exit code: " + exitCode, output);
            }

            return new PyExecRespDTO(true, output, "");

        } catch (IOException e) {
            log.error("IO Error executing script", e);
            throw new PyScriptException("IO Error: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new PyScriptException("Execution interrupted", e);
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    private void validateScriptPath(String path) {
        if (!Files.exists(Paths.get(path))) {
            throw new PyScriptException("Script file not found");
        }
    }

    private Process startProcess(String[] command) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        return processBuilder.start();
    }

    private String readProcessOutput(Process process) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        return output.toString();
    }

    public String createScript(PyCreateScriptDTO scriptDTO) {
        validateCreateRequest(scriptDTO);
        Path scriptPath = prepareScriptFile(scriptDTO);
        writeScriptContent(scriptPath, scriptDTO.getFile());
        return "Script created successfully at: " + scriptDTO.getFilename();
    }

    private void validateCreateRequest(PyCreateScriptDTO scriptDTO) {
        if (scriptDTO.getFilename() == null || scriptDTO.getFile() == null) {
            throw new PyScriptException("Filename and file are required");
        }

        if (!scriptDTO.getFilename().endsWith(".py")) {
            throw new PyScriptException("Filename must have .py extension");
        }

        if (scriptDTO.getFile().isEmpty()) {
            throw new PyScriptException("Uploaded file is empty");
        }
    }

    private Path prepareScriptFile(PyCreateScriptDTO scriptDTO) {
        try {
            Files.createDirectories(Paths.get(SCRIPT_DIRECTORY));
            return Paths.get(SCRIPT_DIRECTORY, scriptDTO.getFilename());
        } catch (IOException e) {
            throw new PyScriptException("Failed to create scripts directory: " + e.getMessage());
        }
    }

    private void writeScriptContent(Path path, MultipartFile file) {
        try {
            // Сохраняем загруженный файл
            file.transferTo(path);
            setFilePermissions(path);
        } catch (IOException e) {
            throw new PyScriptException("Failed to write script file: " + e.getMessage());
        }
    }

    private void setFilePermissions(Path path) {
        try {
            Files.setPosixFilePermissions(path,
                    Set.of(OWNER_READ, OWNER_WRITE, OWNER_EXECUTE));
        } catch (UnsupportedOperationException e) {
            log.warn("Cannot set POSIX permissions on this system");
        } catch (IOException e) {
            throw new PyScriptException("Failed to set file permissions: " + e.getMessage());
        }
    }


}
