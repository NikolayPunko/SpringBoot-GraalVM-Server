package com.host.SpringBootGraalVMServer.controller;

import com.host.SpringBootGraalVMServer.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

@ControllerAdvice
@RestController
public class TestController {

    private final TestService testService;

    private final FileService fileService;

    private final RequestService requestService;

    private final ScriptService scriptService;


    @Autowired
    public TestController(TestService testService, FileService fileService, RequestService requestService, ScriptService scriptService) {
        this.testService = testService;
        this.fileService = fileService;
        this.requestService = requestService;
        this.scriptService = scriptService;
    }





    @GetMapping(value = "/test")
    public String test() {

        System.out.println(DirectoryService.NS_SRVFORM_MAP.get("/disp/zttn").getRoleList());
//        testService.test();
        return "OK";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/test2")
    public String test2() {
//        testService.test();
        return "OK";
    }


}
