package com.host.SpringBootGraalVMServer.service;

import com.host.SpringBootGraalVMServer.model.directory.NsSrvForm;
import com.host.SpringBootGraalVMServer.repositories.NsSrvFormRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class DirectoryService {

    public static Map<String, NsSrvForm> NS_SRVFORM_MAP = new HashMap<>();

    private final NsSrvFormRepository nsSrvFormRepository;

    @Autowired
    public DirectoryService(NsSrvFormRepository nsSrvFormRepository) {
        this.nsSrvFormRepository = nsSrvFormRepository;
    }


    @PostConstruct
    private void postConstruct() {
        for (NsSrvForm obj : nsSrvFormRepository.findAll()) {
            NS_SRVFORM_MAP.put(obj.getPath().trim(), obj);
        }
    }




}
