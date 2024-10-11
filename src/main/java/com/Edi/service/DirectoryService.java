package com.Edi.service;

import com.Edi.model.directory.NsSrvForm;
import com.Edi.model.directory.NsWebOrg;
import com.Edi.repositories.NsSrvFormRepository;
import com.Edi.repositories.NsWebOrgRepository;
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
    public static Map<String, NsWebOrg> NS_WEBORG_MAP = new HashMap<>();

    private final NsSrvFormRepository nsSrvFormRepository;
    private final NsWebOrgRepository nsWebOrgRepository;

    @Autowired
    public DirectoryService(NsSrvFormRepository nsSrvFormRepository, NsWebOrgRepository nsWebOrgRepository) {
        this.nsSrvFormRepository = nsSrvFormRepository;
        this.nsWebOrgRepository = nsWebOrgRepository;
    }


    @PostConstruct
    private void postConstruct() {
        for (NsSrvForm obj : nsSrvFormRepository.findAll()) {
            NS_SRVFORM_MAP.put(obj.getPath().trim(), obj.trim());
        }

        for (NsWebOrg obj: nsWebOrgRepository.findAll()) {
            NS_WEBORG_MAP.put(obj.getOrgName().trim(), obj.trim());
        }
    }




}
