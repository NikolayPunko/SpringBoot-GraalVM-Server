package com.host.SpringBootGraalVMServer.controller;

import com.host.SpringBootGraalVMServer.model.Person;
import com.host.SpringBootGraalVMServer.service.ScriptServiceTest;
import com.host.SpringBootGraalVMServer.service.TestService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

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

    @GetMapping(value = "/testDB")
    public String testDB() {
        return testService.transferDB();
    }

    @GetMapping(value = "/testDB2")
    public String testDB2() {
        return testService.transferDB2();
    }

    @GetMapping(value = "/testDB3")
    public String testDB3() {

        try {
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File("/projects/graalvm_srv/SpringBoot-GraalVM-Server/src/main/java/com/host/SpringBootGraalVMServer/util").toURI().toURL()});
            Class<?> dynamicClass = classLoader.loadClass("DynamicClass");
            Object instance = dynamicClass.getDeclaredConstructor().newInstance();
            Method method = dynamicClass.getMethod("transferDB3", String.class); // Предполагается, что метод называется "execute"
            String str = (String) method.invoke(instance, "jdbc:sqlserver://10.35.0.5;databaseName=naswms;encrypt=true;trustServerCertificate=true");
            return "Метод execute() выполнен для класса: " + "DynamicClass" + "\n"+ str;
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка при выполнении: " + e.getMessage();
        }


    }
}
