package com.savushkin.Edi.service;

import com.savushkin.Edi.exceptions.DirectoryNotFoundException;
import com.savushkin.Edi.model.directory.NsSrvForm;
import com.savushkin.Edi.model.directory.NsWebOrg;
import com.savushkin.Edi.repositories.NsSrvFormRepository;
import com.savushkin.Edi.repositories.NsWebOrgRepository;
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

    public static NsSrvForm getNsSrvFormByKey(String key){
        try {
            return DirectoryService.NS_SRVFORM_MAP.get(key);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new DirectoryNotFoundException(String.format("Не удалось получить значения из справочника NS_SRVFORM по ключу %s", key));
        }
    }

    public static NsWebOrg getNsWebOrgByKey(String key){
        try {
            return DirectoryService.NS_WEBORG_MAP.get(key);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new DirectoryNotFoundException(String.format("Не удалось получить значения из справочника NS_WEBORG по ключу %s", key));
        }
    }



}
