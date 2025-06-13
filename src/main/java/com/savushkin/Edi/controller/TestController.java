package com.savushkin.Edi.controller;

import com.savushkin.Edi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
        testService.test();
        return "OK";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/test2")
    public String test2() {
//        testService.test();
        return "OK";
    }

    @GetMapping("/create-file")
    public ResponseEntity<?> CreateFile() {

        String fileName = "example.txt"; // Имя файла
        String content = "File for test."; // Содержимое файла

        try {
            // Создание файла
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("Файл создан: " + file.getName());
            } else {
                System.out.println("Файл уже существует.");
            }

            // Запись содержимого в файл
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
                System.out.println("Содержимое записано в файл.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Ошибка при создании файла: " + e.getMessage());
        }
        return ResponseEntity.ok("Файл успешно создан и записан.");

    }


}
