package com.host.SpringBootGraalVMServer.service;

import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class TestService {


    public String runScript(){
        Context context = Context.create("python");
        try

        {
            Source sourceFile1 = Source
                    .newBuilder("python", new File("/projects/graalvm_srv/SpringBoot-GraalVM-Server/src/main/resources/static/Scripts/test.py"))
                    .build();


            context.eval(sourceFile1);

            Value function = context.getBindings("python").getMember("my_function"); //выбираем метод который нужен
            String result = function.execute(12, "аргумент2").asString(); //выполняем
            System.out.println("Результат: " + result);

            return "Результат: " + result;
        }
        catch(IOException e) {
            log.error(e.toString());
            e.printStackTrace();
            return "Ошибка" + e.toString();
        }
    }



}
