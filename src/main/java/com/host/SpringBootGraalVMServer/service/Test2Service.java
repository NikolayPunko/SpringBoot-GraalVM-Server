package com.host.SpringBootGraalVMServer.service;

import com.host.SpringBootGraalVMServer.model.ScriptContext;
import com.host.SpringBootGraalVMServer.model.ScriptPayload;
import com.host.SpringBootGraalVMServer.pool.ContextPool;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class Test2Service {


//    public String runScript(){
//        return runScript1() +"\n" + runScript2();
//    }

    public String runScript1(){

        long start = System.currentTimeMillis();

        Context context = Context.newBuilder("python")
                .allowHostAccess(HostAccess.ALL)
                .allowAllAccess(true)
                .option("python.ForceImportSite", "true")
                .engine(Engine.create())
                .build();


        try {


            for (int i = 0; i < 10000; i++) {
                Source sourceFile1 = Source
                        .newBuilder("python", new File("/projects/graalvm_srv/SpringBoot-GraalVM-Server/src/main/resources/static/Scripts/test.py"))
                        .build();
                context.eval(sourceFile1);
                Value function = context.getBindings("python").getMember("main"); //выбираем метод который нужен
                ScriptPayload result = function.execute(new ScriptPayload("req", "resp", "connection")).as(ScriptPayload.class); //выполняем
            }
            long finish = System.currentTimeMillis();

            return "Результат многократной компиляции (цикл 10000): " + (finish - start);
        }
        catch(IOException e) {
            log.error(e.toString());
            e.printStackTrace();
            return "Ошибка" + e.toString();
        }
    }

    public String runScript2(){

        long start = System.currentTimeMillis();

        Context context = Context.newBuilder("python")
                .allowHostAccess(HostAccess.ALL)
                .allowAllAccess(true)
                .option("python.ForceImportSite", "true")
                .engine(Engine.create())
                .build();


        try {

            Source sourceFile1 = Source
                    .newBuilder("python", new File("/projects/graalvm_srv/SpringBoot-GraalVM-Server/src/main/resources/static/Scripts/test.py"))
                    .build();

            context.eval(sourceFile1);

            for (int i = 0; i < 10000; i++) {

                Value function = context.getBindings("python").getMember("main"); //выбираем метод который нужен
                ScriptPayload result = function.execute(new ScriptPayload("req", "resp", "connection")).as(ScriptPayload.class); //выполняем

            }

            long finish = System.currentTimeMillis();

            return "Результат компиляции 1 раз (цикл 10000): " + (finish - start);
        }
        catch(IOException e) {
            log.error(e.toString());
            e.printStackTrace();
            return "Ошибка" + e.toString();
        }
    }






    public String runScript(){

//        ScriptContext scriptContext = ContextPool.borrowContext();
//        Context context = scriptContext.getContext();

        Context context = Context.newBuilder("python")
                .allowHostAccess(HostAccess.ALL)
                .allowAllAccess(true)
                .option("python.ForceImportSite", "true")
                .engine(Engine.create())
                .build();;
        try

        {
            Source sourceFile1 = Source
                    .newBuilder("python", new File("/projects/graalvm_srv/SpringBoot-GraalVM-Server/src/main/resources/static/Scripts/test2.py"))
                    .build();

            context.eval(sourceFile1);

//            context.getBindings("python").putMember("zap", new ScriptPayload("req", "resp", "connection"));
            Value function = context.getBindings("python").getMember("main"); //выбираем метод который нужен
            ScriptPayload result = function.execute(new ScriptPayload("req", "resp", "connection")).as(ScriptPayload.class); //выполняем
            System.out.println("Результат: " + result.toString());

            return "Результат: " + result;
        }
        catch(IOException e) {
            log.error(e.toString());
            e.printStackTrace();
            return "Ошибка" + e.toString();
        }
//        finally {
//            ContextPool.returnContext(scriptContext);
//        }
    }



}
