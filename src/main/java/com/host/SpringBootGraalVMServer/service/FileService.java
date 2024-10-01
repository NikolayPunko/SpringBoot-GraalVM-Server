package com.host.SpringBootGraalVMServer.service;

import com.host.SpringBootGraalVMServer.dto.NewScriptDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Base64;


@Slf4j
@Service
public class FileService {

    private final String FILE_DIRECTORY = "/projects/graalvm_srv/scripts/";
    private final String BC_DIRECTORY = "src/main/java/com/host/SpringBootGraalVMServer/bc/";


    public void compileFile(String filePath) {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("/projects/graalvm_srv/scripts/logCompile.log");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(null, outputStream, outputStream, filePath);
        if (result != 0) {
            throw new RuntimeException("Ошибка компиляции " + filePath);
        }

    }

    public String executeClass(String className, String methodName, String json) {
        try {
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File(FILE_DIRECTORY).toURI().toURL()});
            Class<?> dynamicClass = classLoader.loadClass(className);
            Object instance = dynamicClass.getDeclaredConstructor().newInstance();
            Method method = dynamicClass.getMethod(methodName, String.class);
            String str = (String) method.invoke(instance, json);
            return str;
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
            return "Ошибка при выполнении: " + e.getMessage();
        }
    }


    public void addScriptFile(NewScriptDTO newScript) throws IOException {

        String fileName = new StringBuilder()
                .append(newScript.getName())
                .append(".")
                .append(newScript.getLang())
                .toString();


        String encodedScript = decodedBase64(newScript.getScript());

        String directory = getDirectoryByType(newScript);

        createFileAndWrite(directory + fileName, encodedScript);
        compileFile(directory + fileName);

    }

    public String getDirectoryByType(NewScriptDTO scriptDTO) {
        return switch (scriptDTO.getType()) {
            case "method" -> FILE_DIRECTORY;
            case "bc" -> BC_DIRECTORY;
            default -> throw new RuntimeException("Поле \"type\" не определено!");
        };
    }

    private void createFileAndWrite(String path, String content) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            byte[] strToBytes = content.getBytes();
            fileOutputStream.write(strToBytes);
        } catch (IOException e) {
            log.error("Не удалось записать в файл " + path);
            throw new RuntimeException(e);
        }
    }

    private String decodedBase64(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }


}
