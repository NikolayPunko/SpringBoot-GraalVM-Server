package com.savushkin.Edi.service;

import com.savushkin.Edi.dto.PyCreateScriptDTO;
import com.savushkin.Edi.dto.PyExecRespDTO;
import com.savushkin.Edi.exceptions.PyScriptException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.nio.file.attribute.PosixFilePermission.*;

@Slf4j
@Service
public class PythonService {

    @Value("${app.directory.scripts}")
    private String SCRIPT_DIRECTORY;

    public PyExecRespDTO executeScript(PyCreateScriptDTO scriptDTO) {
        validateScriptPath(SCRIPT_DIRECTORY + scriptDTO.getFilename());

        Process process = null;
        try {
            String[] command = {"/bin/bash", "-c", "python3 " + SCRIPT_DIRECTORY + scriptDTO.getFilename()};
            process = startProcess(command);

            String output = readProcessOutput(process);

            boolean finished = waitForProcess(process, 1, TimeUnit.MINUTES);
            if (!finished) {
                throw new PyScriptException("Script timed out");
            }

            int exitCode = process.exitValue();
            if (exitCode != 0) {
                throw new PyScriptException("Script failed with exit code: " + exitCode);
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

    private boolean waitForProcess(Process process, long timeout, TimeUnit unit) throws InterruptedException {
        return process.waitFor(timeout, unit);
    }

    public String createScript(PyCreateScriptDTO scriptDTO) {
        validateCreateRequest(scriptDTO);
        Path scriptPath = prepareScriptFile(scriptDTO);
        writeScriptContent(scriptPath, scriptDTO.getContent());
        return "Script created successfully at: " + scriptPath;
    }

    private void validateCreateRequest(PyCreateScriptDTO scriptDTO) {
        if (scriptDTO.getFilename() == null || scriptDTO.getContent() == null) {
            throw new PyScriptException("Filename and content are required");
        }

        if (!scriptDTO.getFilename().endsWith(".py")) {
            throw new PyScriptException("Filename must have .py extension");
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

    private void writeScriptContent(Path path, String content) {
        try {
            Files.write(path, content.getBytes(), StandardOpenOption.CREATE);
            setFilePermissions(path);
        } catch (IOException e) {
            throw new PyScriptException("Failed to write script file: " + e.getMessage());
        }
    }

    private void setFilePermissions(Path path) throws IOException {
        try {
            Files.setPosixFilePermissions(path,
                    Set.of(OWNER_READ, OWNER_WRITE, OWNER_EXECUTE));
        } catch (UnsupportedOperationException e) {
            log.warn("Cannot set POSIX permissions on this system");
        }
    }


}
