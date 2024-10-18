package com.savushkin.Edi.service;

import com.savushkin.Edi.dto.NewScriptDTO;
import com.savushkin.Edi.model.RequestObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Base64;


@Slf4j
@Service
public class FileService {

    private final String FILE_DIRECTORY = "/projects/graalvm_srv/scripts/";
    private final String BC_DIRECTORY = "src/main/java/com/savushkin/Edi/bc/";

    private final BashService bashService;

    @Autowired
    public FileService(BashService bashService) {
        this.bashService = bashService;
    }


    public void compileFile(String filePath, String fileName) {
        // Получаем компилятор
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        // Указываем путь к директории, где будут сохранены скомпилированные классы
        String outputDir = "/projects/graalvm_srv/scripts/"; // Замените на нужный путь
        new File(outputDir).mkdirs(); // Создаем директорию, если она не существует

        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null)) {
            // Указываем целевую директорию для скомпилированных классов
            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(new File(outputDir)));

            // Указываем файл с исходным кодом
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(Arrays.asList(filePath));

            // Компилируем
            boolean success = compiler.getTask(null, fileManager, null, null, null, compilationUnits).call();

            if (success) {
                System.out.println("Compilation successful!");
            } else {
                System.out.println("Compilation failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void compileFile(String filePath) {
//        System.out.println(System.getProperty("java.class.path"));
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

    public String executeClass(String className, String methodName, RequestObj payload) {
        try {
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File(FILE_DIRECTORY).toURI().toURL()});
            Class<?> dynamicClass = classLoader.loadClass(className);
            Object instance = dynamicClass.getDeclaredConstructor().newInstance();
            Method method = dynamicClass.getMethod(methodName, RequestObj.class);
            String str = (String) method.invoke(instance, payload);
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
//        compileFile(directory + fileName, fileName);
        compileFile(directory + fileName);

//        bashService.pushWebSrvScript( fileName);
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
