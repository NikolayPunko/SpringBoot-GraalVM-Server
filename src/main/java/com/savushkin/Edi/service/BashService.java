package com.savushkin.Edi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Slf4j
@Service
public class BashService {

    private final UserDetailsService userDetailsService;

    @Autowired
    public BashService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    public void pushWebSrvScript(String fileName, String directory){

        String username = userDetailsService.getUserDetails().getUsername();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("/projects/graalvm_srv/pushWebSrvScript.sh", username, fileName, directory);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }

            int exitCode = process.waitFor();
            log.info("Bash script exited with code: {}", exitCode);

        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
        }
    }

}
