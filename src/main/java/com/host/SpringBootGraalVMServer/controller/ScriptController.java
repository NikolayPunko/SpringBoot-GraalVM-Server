package com.host.SpringBootGraalVMServer.controller;

import com.host.SpringBootGraalVMServer.model.Person;
import com.host.SpringBootGraalVMServer.model.ScriptPayload;
import com.host.SpringBootGraalVMServer.scriptConfiguration.PythonConfiguration;
import com.host.SpringBootGraalVMServer.service.ScriptService;
import com.host.SpringBootGraalVMServer.service.ScriptServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ScriptController {

    private final ScriptServiceTest scriptServiceTest;
    private final ScriptService scriptService;

    @Autowired
    public ScriptController(ScriptServiceTest scriptServiceTest, ScriptService scriptService) {
        this.scriptServiceTest = scriptServiceTest;
        this.scriptService = scriptService;
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

    @GetMapping("/method1")
    public int testMethod1() {
        return scriptServiceTest.getMethod1();
    }

    @GetMapping("/method2")
    public String testMethod2() {
        return scriptServiceTest.getMethod2();
    }


    @GetMapping("/method3")
    public String testMethod3() {
        return scriptServiceTest.getMethod3();
    }

    @GetMapping("/method4")
    public Person testMethod4() {
        return scriptServiceTest.getMethod4();
    }

}
