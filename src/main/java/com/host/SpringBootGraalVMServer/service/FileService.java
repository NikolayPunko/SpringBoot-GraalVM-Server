package com.host.SpringBootGraalVMServer.service;

import com.host.SpringBootGraalVMServer.dto.NewScriptDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
public class FileService {

    private final String FILE_DIRECTORY = "src/main/resources/static/Scripts/"; //поменять путь под linux!!!

    public void addScriptFile(NewScriptDTO newScript) throws IOException {

        String fileName = new StringBuilder()
                .append(newScript.getFId())
                .append(newScript.getPath())
                .append(".")
                .append(newScript.getLang())
                .toString()
                .replace("/","_");

        String encodedScript = decodedBase64(newScript.getScript());

        createFileAndWrite(FILE_DIRECTORY+fileName, encodedScript);

    }

    private void createFileAndWrite(String path, String content){
        try(FileOutputStream fileOutputStream = new FileOutputStream(path)){
            byte[] strToBytes = content.getBytes();
            fileOutputStream.write(strToBytes);
        } catch (IOException e) {
            log.error("Не удалось записать в файл " + path);
            throw new RuntimeException(e);
        }
    }

    private String decodedBase64(String encodedString){
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }



}
