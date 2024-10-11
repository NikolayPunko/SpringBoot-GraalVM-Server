package com.host.SpringBootGraalVMServer.controller;

import com.host.SpringBootGraalVMServer.dto.NewScriptDTO;
import com.host.SpringBootGraalVMServer.dto.ResponseApp;
import com.host.SpringBootGraalVMServer.service.FileService;
import com.host.SpringBootGraalVMServer.service.RequestService;
import com.host.SpringBootGraalVMServer.service.ScriptService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class ScriptController {

    private final ScriptService scriptService;
    private final FileService fileService;

    private final RequestService requestService;

    @Autowired
    public ScriptController(ScriptService scriptService, FileService fileService, RequestService requestService) {
        this.scriptService = scriptService;
        this.fileService = fileService;
        this.requestService = requestService;
    }

    @GetMapping("/runzap")
    public ResponseEntity<?> runzap() {
//        ScriptPayload result = scriptService.getmark();
        return ResponseEntity.ok("result");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/setform")
    public ResponseEntity<?> addScriptFile(@RequestBody NewScriptDTO newScriptDTO) {
        try {
            fileService.addScriptFile(newScriptDTO);
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ResponseApp("400","Ошибка добавления файла! " + e.getMessage()));
        }
        return ResponseEntity.ok(new ResponseApp("ok",""));
    }


    @PostMapping(value = "/**")
    public String handlePostRequests(HttpServletRequest request,
                                     @RequestBody String body) {
        String partOfUrl = requestService.getPartOfUrl(request);
        requestService.checkAccess(partOfUrl);

        String className = defineFilename(partOfUrl);

        return scriptService.executeFile(className, body);
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


}
