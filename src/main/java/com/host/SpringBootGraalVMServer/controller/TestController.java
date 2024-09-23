package com.host.SpringBootGraalVMServer.controller;

import com.host.SpringBootGraalVMServer.model.Person;
import com.host.SpringBootGraalVMServer.service.ScriptServiceTest;
import com.host.SpringBootGraalVMServer.service.TestService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@RestController
public class TestController {

    private final ScriptServiceTest scriptServiceTest;
    private final TestService testService;

    @Autowired
    public TestController(ScriptServiceTest scriptServiceTest, TestService testService) {
        this.scriptServiceTest = scriptServiceTest;
        this.testService = testService;
    }

    @GetMapping(value = "/**")
    public String handleGetRequests() {
        System.out.println("GET request intercepted!");
        return "GET request intercepted!";
    }

    @PostMapping(value = "/**")
    public String handlePostRequests(HttpServletRequest request,
                                     @RequestBody Person person) {

        System.out.println(request.getRequestURL());

        String requestURI = request.getRequestURI(); // Получаем полный URI
        String contextPath = request.getContextPath(); // Получаем контекстный путь
        String partOfUrl = requestURI.substring(contextPath.length()); // Получаем часть URL после хоста

        System.out.println(requestURI);
        System.out.println(partOfUrl);

        System.out.println("POST request intercepted!");

        return "POST request intercepted!";
    }


    @GetMapping(value = "/test")
    public String test() {
        return testService.runScript();
    }

    @GetMapping(value = "/test3")
    public String test3() {
        return testService.runScript3();
    }


}
