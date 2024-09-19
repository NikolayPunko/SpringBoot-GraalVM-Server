package com.host.SpringBootGraalVMServer.controller;

import com.host.SpringBootGraalVMServer.model.Person;
import com.host.SpringBootGraalVMServer.service.ScriptServiceTest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@RestController
public class TestController {

    private final ScriptServiceTest scriptServiceTest;

    @Autowired
    public TestController(ScriptServiceTest scriptServiceTest) {
        this.scriptServiceTest = scriptServiceTest;
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





}
