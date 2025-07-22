package com.savushkin.Edi.controller;

import com.savushkin.Edi.dto.NewScriptDTO;
import com.savushkin.Edi.dto.ResponseApp;
import com.savushkin.Edi.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class ScriptController {

    private final ScriptService scriptService;
    private final FileService fileService;
    private final RedisService redisService;
    private final RequestService requestService;
    private final BashService bashService;

    @Autowired
    public ScriptController(ScriptService scriptService, FileService fileService, RedisService redisService, RequestService requestService, BashService bashService) {
        this.scriptService = scriptService;
        this.fileService = fileService;
        this.redisService = redisService;
        this.requestService = requestService;
        this.bashService = bashService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/api/restartsrv")
    public ResponseEntity<?> restartSrv() {
        log.warn("Restart srv req!");
        bashService.restartSrv();
        return ResponseEntity.ok(new ResponseApp("ok", ""));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/setform")
    public ResponseEntity<?> addScriptFile(@RequestBody NewScriptDTO newScriptDTO) {
        log.info("Script setform req: {}", newScriptDTO);
        try {
            fileService.addScriptFile(newScriptDTO);
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.badRequest().body(new ResponseApp("400", "Error adding file! " + e.getMessage()));
        }
        return ResponseEntity.ok(new ResponseApp("ok", ""));
    }

    @GetMapping(value = "/api/getresult/{id}")
    public ResponseEntity<?> getResult(@PathVariable String id) {
        log.info("Script getresult req: {}", id);
        long startTimeParsing = System.nanoTime();
        String result = redisService.findById(id);
        long endTimeParsing = System.nanoTime();
        Long durParsing = (endTimeParsing - startTimeParsing);
        System.out.println(durParsing);

        if(result==null){
            return ResponseEntity.ok(new ResponseApp("proceeded", ""));
        }
        return ResponseEntity.ok(result);
    }


    @PostMapping(value = "/**")
    public ResponseEntity<?> handlePostRequests(HttpServletRequest request,
                                     @RequestBody String body) {
        log.info("Script exec req: {}", body);
        String partOfUrl = requestService.getPartOfUrl(request);
        requestService.checkAccess(partOfUrl);

        String className = defineFilename(partOfUrl);

        String result = scriptService.executeFile(partOfUrl, className, body);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }


    public String defineFilename(String partOfUrl) {
        StringBuilder className = new StringBuilder();
        String[] parts = partOfUrl.split("/");
        for (int i = 0; i < parts.length; i++) {
            try {
                parts[i] = parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1).toLowerCase();
                className.append(parts[i]);
            } catch (Exception e) {
                continue;
            }
        }
        return className.toString();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/classpath")
    public String classpath() {
        return System.getProperty("java.class.path");
    }


}
