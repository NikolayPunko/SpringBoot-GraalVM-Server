package com.host.SpringBootGraalVMServer.controller;

import com.host.SpringBootGraalVMServer.dto.NewScriptDTO;
import com.host.SpringBootGraalVMServer.dto.ResponseApp;
import com.host.SpringBootGraalVMServer.model.Person;
import com.host.SpringBootGraalVMServer.model.ScriptPayload;
import com.host.SpringBootGraalVMServer.scriptConfiguration.PythonConfiguration;
import com.host.SpringBootGraalVMServer.service.FileService;
import com.host.SpringBootGraalVMServer.service.ScriptService;
import com.host.SpringBootGraalVMServer.service.ScriptServiceTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class ScriptController {

    private final ScriptServiceTest scriptServiceTest;
    private final ScriptService scriptService;
    private final FileService fileService;

    @Autowired
    public ScriptController(ScriptServiceTest scriptServiceTest, ScriptService scriptService, FileService fileService) {
        this.scriptServiceTest = scriptServiceTest;
        this.scriptService = scriptService;
        this.fileService = fileService;
    }

    @GetMapping("/runzap")
    public ResponseEntity<?> runzap() {
        ScriptPayload result = scriptService.getmark();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/reset-source-script") //обновить скрипты
    public void resetSourceScript() {
        PythonConfiguration.assignSource();
    }

//    @PostMapping("/setform")
//    public ResponseEntity<?> addScriptFile(@RequestBody NewScriptDTO newScriptDTO){
//        try {
//            fileService.addScriptFile(newScriptDTO);
//        } catch (Exception e){
//            return ResponseEntity.badRequest().body("Ошибка добавления скрипта!");
//        }
//        return ResponseEntity.ok("Скрипт добавлен!");
//    }

//    @PostMapping("/addClass/{className}")
//    public String addClass(@PathVariable String className, @RequestBody String classCode) {
//        return fileService.addFile(className, classCode);
//    }
//
//    @PostMapping("/compileClass/{className}")
//    public String compileClass(@PathVariable String className) {
//        return fileService.compileFile(className);
//    }
//
//    @GetMapping("/execute/{className}")
//    public String executeClass(@PathVariable String className) {
//        return fileService.executeClass(className, "main");
//    }

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

//    @PostMapping("/compile")
//    public ResponseEntity<?> compileFile(@RequestBody NewScriptDTO newScriptDTO) {
//        try {
//            fileService.compile(newScriptDTO);
//        } catch (Exception e) {
//            log.error(e.toString());
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body(new ResponseApp("400","Ошибка компилирования файла! " + e.getMessage()));
//        }
//        return ResponseEntity.ok(new ResponseApp("ok",""));
//    }

}
