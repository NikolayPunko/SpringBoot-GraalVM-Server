package com.host.SpringBootGraalVMServer.controller;

import com.host.SpringBootGraalVMServer.model.Person;
import com.host.SpringBootGraalVMServer.scriptConfiguration.PythonConfiguration;
import com.host.SpringBootGraalVMServer.service.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ScriptController {

    private final ScriptService scriptService;

    @Autowired
    public ScriptController(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    @GetMapping("/runzap")
    public ResponseEntity<?> runzap() {

        return ResponseEntity.ok("test!");
    }

    @GetMapping("/reset-source-script") //обновить скрипты
    public void resetSourceScript() {
        PythonConfiguration.assignSource();
    }

    @GetMapping("/method1")
    public int testMethod1() {
        return scriptService.getMethod1();
    }

    @GetMapping("/method2")
    public String testMethod2() {
        return scriptService.getMethod2();
    }


    @GetMapping("/method3")
    public String testMethod3() {
        return scriptService.getMethod3();
    }

    @GetMapping("/method4")
    public Person testMethod4() {
        return scriptService.getMethod4();
    }

}
