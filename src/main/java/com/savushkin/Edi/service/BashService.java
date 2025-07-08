package com.savushkin.Edi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Service
public class BashService {

    private final UserDetailsService userDetailsService;

    @Value("${app.bash.gitea}")
    private String BASH_GITEA_SCRIPT;

    @Value("${app.bash.gitea}")
    private String BASH_RESTART_SCRIPT;

    @Autowired
    public BashService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    public void pushWebSrvScript(String fileName, String directory){

        String username = userDetailsService.getUserDetails().getUsername();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(BASH_GITEA_SCRIPT, username, fileName, directory);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            String output = readProcessOutput(process);

            int exitCode = process.waitFor();

            if(exitCode != 0){
                log.error("Bash pushWebSrvScript exited with code: {}", exitCode);
                log.error(output);
            } else {
                log.info("Bash pushWebSrvScript exited with code: {}", exitCode);
            }

        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
        }
    }

    public void restartSrv() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", BASH_RESTART_SCRIPT);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            String output = readProcessOutput(process);

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                log.error("Bash restartsrv script exited with error: {}", exitCode);
                log.error(output);
                throw new RuntimeException("Server restart error");
            }
            log.info("Bash restartsrv exited with code: {}", exitCode);
        } catch (Exception e) {
            log.error("Eror restartsrv: {}", e.getMessage());
            throw new RuntimeException("Server restart error: " + e.getMessage());
        }
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

}
