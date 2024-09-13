package com.host.SpringBootGraalVMServer.scriptConfiguration;

import com.host.SpringBootGraalVMServer.model.ScriptContext;
import com.host.SpringBootGraalVMServer.service.PythonService;
import com.host.SpringBootGraalVMServer.service.PythonServiceTest;
import org.graalvm.polyglot.Source;

import java.io.File;
import java.io.IOException;

public class PythonConfiguration {

    private static Source sourceFile1;
    private static Source sourceFile2;

    static {
        assignSource();
    }

    public static void assignSource(){ //метод для обновления скрипта без перезагрузки приложения
        try {
            sourceFile1 = Source
                    .newBuilder("python", new File("/projects/graalvm_srv/SpringBoot-GraalVM-Server/src/main/resources/static/Scripts/TestPython.py"))
                    .build();

            sourceFile2 = Source
                    .newBuilder("python", new File("/projects/graalvm_srv/SpringBoot-GraalVM-Server/src/main/resources/static/Scripts/z_getmark.py"))
                    .build();
        } catch (IOException e) {
            System.out.println("===Script not found!===");
            throw new RuntimeException(e);
        }
    }

    public static PythonServiceTest getPythonServiceTest(ScriptContext scriptContext) {
        return scriptContext
                .getContext()

                .eval(sourceFile1)
                .getContext()
                .getBindings("python")
                .getMember("ConnectorImpl")
                .as(PythonServiceTest.class);
    }

    public static PythonService getPythonService(ScriptContext scriptContext) {
        return scriptContext
                .getContext()

                .eval(sourceFile2)
                .getContext()
                .getBindings("python")
                .getMember("ConnectorImpl")
                .as(PythonService.class);
    }

}
