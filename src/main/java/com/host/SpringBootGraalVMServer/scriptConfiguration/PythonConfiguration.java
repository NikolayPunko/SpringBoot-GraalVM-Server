package com.host.SpringBootGraalVMServer.scriptConfiguration;

import com.host.SpringBootGraalVMServer.model.ScriptContext;
import com.host.SpringBootGraalVMServer.service.PythonService;
import org.graalvm.polyglot.Source;

import java.io.File;
import java.io.IOException;

public class PythonConfiguration {

    private static Source sourceFile1;

    static {
        assignSource();
    }

    public static void assignSource(){ //метод для обновления скрипта без перезагрузки приложения
        try {
            sourceFile1 = Source
                    .newBuilder("python", new File("/home/javadev2/projects/SpringBoot-GraalVM-Python-Gateway/src/main/resources/static/Scripts/TestPython.py"))
                    .build();
        } catch (IOException e) {
            System.out.println("===Script not found!===");
            throw new RuntimeException(e);
        }
    }

    public static PythonService getPythonService(ScriptContext scriptContext) {
        return scriptContext
                .getContext()
                .eval(sourceFile1)
                .getContext()
                .getBindings("python")
                .getMember("ConnectorImpl")
                .as(PythonService.class);
    }

}
