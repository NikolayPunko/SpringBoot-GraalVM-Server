package com.savushkin.Edi.controller;

import com.savushkin.Edi.dto.PyExecReqDTO;
import com.savushkin.Edi.dto.PyExecRespDTO;
import com.savushkin.Edi.dto.PyCreateScriptDTO;
import com.savushkin.Edi.exceptions.PyScriptException;
import com.savushkin.Edi.service.PythonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/python")
@Slf4j
public class PythonController {

    private final PythonService pythonService;

    @Autowired
    public PythonController(PythonService pythonService) {
        this.pythonService = pythonService;
    }

    @PostMapping("/exec")
    public ResponseEntity<PyExecRespDTO> executeScript(@RequestBody PyExecReqDTO execReqDTO) {
        log.info("Python exec req: {}", execReqDTO);
        try {
            PyExecRespDTO response = pythonService.executeScript(execReqDTO);
            return ResponseEntity.ok(response);
        } catch (PyScriptException e) {
            log.error(e.toString());
            return ResponseEntity.status(500)
                    .body(new PyExecRespDTO(false, e.getOutput(), e.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAndExecuteScript(@RequestPart("filename") String filename,
                                                    @RequestPart("file") MultipartFile file) {
        log.info("Python create req deprecated: {}", filename);
        try {
            PyCreateScriptDTO scriptDTO = new PyCreateScriptDTO();
            scriptDTO.setFilename(filename);
            scriptDTO.setFile(file);

            String result = pythonService.createScript(scriptDTO);
            return ResponseEntity.ok(new PyExecRespDTO(true, result, ""));
        } catch (PyScriptException e) {
            log.error(e.toString());
            return ResponseEntity.status(500)
                    .body(new PyExecRespDTO(false, e.getOutput(), e.getMessage()));
        }
    }


}
