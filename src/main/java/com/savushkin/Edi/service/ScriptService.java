package com.savushkin.Edi.service;

import com.savushkin.Edi.exceptions.DirectoryNotFoundException;
import com.savushkin.Edi.model.RequestObj;
import com.savushkin.Edi.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ScriptService {

    private final FileService fileService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public ScriptService(FileService fileService, UserDetailsService userDetailsService) {
        this.fileService = fileService;
        this.userDetailsService = userDetailsService;
    }

    public String executeFile(String className, String bodyReq){

        RequestObj payload = buildPayload();
        payload.setBody(bodyReq);

        String result = fileService.executeClass(className, "main", payload);
        return result;
    }

    private RequestObj buildPayload() {
        RequestObj payload = new RequestObj();

        User user = userDetailsService.getUserDetails().getPerson();
        String orgName = user.getOrgName();

        try {
            String gln = DirectoryService.NS_WEBORG_MAP.get(orgName).getGln();
            payload.setGln(gln);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new DirectoryNotFoundException(String.format("Не удалось получить значения из справочника NS_WEBORG по ключу %s", orgName));
        }

        return payload;
    }















}
