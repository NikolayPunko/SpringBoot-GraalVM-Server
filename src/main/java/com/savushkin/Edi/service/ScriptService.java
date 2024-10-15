package com.savushkin.Edi.service;

import com.savushkin.Edi.exceptions.DirectoryNotFoundException;
import com.savushkin.Edi.model.RequestObj;
import com.savushkin.Edi.model.User;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Slf4j
@Service
public class ScriptService {

    private final FileService fileService;
    private final KafkaService kafkaService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public ScriptService(FileService fileService, KafkaService kafkaService, UserDetailsService userDetailsService) {
        this.fileService = fileService;
        this.kafkaService = kafkaService;
        this.userDetailsService = userDetailsService;
    }

    public String executeFile(String partOfUrl, String className, String bodyReq) {

        String orgName = userDetailsService.getUserDetails().getPerson().getOrgName();
        String executePlace = getExecutePlace(partOfUrl);
        String gln = getGln(orgName);


        if (executePlace.equalsIgnoreCase("KAFKA")) {

            String token = generateWebSrvToken();

            JSONObject json = new JSONObject();
            json.put("method", className);
            json.put("token", token);
            json.put("body", bodyReq);
            json.put("gln", gln);

            kafkaService.sendMessage(json.toString(),"websrv");

            return token;

        } else {
            RequestObj payload = new RequestObj();
            payload.setBody(bodyReq);
            payload.setGln(gln);

            String result = fileService.executeClass(className, "main", payload);
            return result;
        }

    }


    private String getExecutePlace(String partOfUrl){
        try {
            return DirectoryService.NS_SRVFORM_MAP.get(partOfUrl).getExecute().trim();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DirectoryNotFoundException(String.format("Не удалось получить значения из справочника NS_SRVFORM по ключу %s", partOfUrl));
        }
    }

    private String getGln(String orgName){
        try {
            return DirectoryService.NS_WEBORG_MAP.get(orgName).getGln();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DirectoryNotFoundException(String.format("Не удалось получить значения из справочника NS_WEBORG по ключу %s", orgName));
        }
    }

    private String generateWebSrvToken(){
        UUID uuid = UUID.randomUUID();
        return "websrv-" + uuid.toString().substring(0, 20);
    }

}
