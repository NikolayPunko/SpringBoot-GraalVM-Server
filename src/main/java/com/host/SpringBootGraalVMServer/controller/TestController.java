package com.host.SpringBootGraalVMServer.controller;

import com.host.SpringBootGraalVMServer.model.ScriptPayload;
import com.host.SpringBootGraalVMServer.service.FileService;
import com.host.SpringBootGraalVMServer.service.ScriptServiceTest;
import com.host.SpringBootGraalVMServer.service.TestService;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
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

    private final FileService fileService;

    @Autowired
    public TestController(ScriptServiceTest scriptServiceTest, TestService testService, FileService fileService) {
        this.scriptServiceTest = scriptServiceTest;
        this.testService = testService;
        this.fileService = fileService;
    }

    @GetMapping(value = "/**")
    public String handleGetRequests() {
        System.out.println("GET request intercepted!");
        return "GET request intercepted!";
    }

    @PostMapping(value = "/**")
    public String handlePostRequests(HttpServletRequest request,
                                     @RequestBody ScriptPayload scriptPayload) {

        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String partOfUrl = requestURI.substring(contextPath.length());

        System.out.println(partOfUrl);

        JSONObject jsonObject = new JSONObject(scriptPayload);

        String className = defineFilename(partOfUrl);


        String result = fileService.executeClass(className, "main", jsonObject.toString());

        return result;
    }

    public String defineFilename(String partOfUrl){
        StringBuilder className = new StringBuilder();
        String[] parts = partOfUrl.toString().split("/");
        for (int i = 0; i < parts.length; i++) {
            try {
                parts[i] = parts[i].substring(0,1).toUpperCase() + parts[i].substring(1).toLowerCase();
                className.append(parts[i]);
            } catch (Exception e) {
                continue;
            }
        }
        return className.toString();
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
            Method method = dynamicClass.getMethod("test"); // Предполагается, что метод называется "execute"
            String str = (String) method.invoke(instance);
            return "Метод execute() выполнен для класса: " + "DynamicClass" + "\n"+ str;
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка при выполнении: " + e.getMessage();
        }


    }
}
