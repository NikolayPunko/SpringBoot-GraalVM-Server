package com.host.SpringBootGraalVMServer.service;

import com.host.SpringBootGraalVMServer.model.ScriptPayload;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Service
public class TestService {


//    public String runScript(){
//        return runScript1() +"\n" + runScript2();
//    }

    public String runScript3(){

        Context context = Context.newBuilder("python")
                .allowHostAccess(HostAccess.ALL)
                .allowAllAccess(true)
                .option("python.ForceImportSite", "true")
                .engine(Engine.create())
                .build();;
        try

        {
            Source sourceFile1 = Source
                    .newBuilder("python", new File("/projects/graalvm_srv/SpringBoot-GraalVM-Server/src/main/resources/static/Scripts/test3.py"))
                    .build();

            context.eval(sourceFile1);

//            context.getBindings("python").putMember("zap", new ScriptPayload("req", "resp", "connection"));
            Value function = context.getBindings("python").getMember("main"); //выбираем метод который нужен
            ScriptPayload result = function.execute(new ScriptPayload("req", "resp", null)).as(ScriptPayload.class); //выполняем
            System.out.println("Результат: " + result.toString());

            return "Результат: " + result;
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
            ScriptPayload result = function.execute(new ScriptPayload("req", "resp", null)).as(ScriptPayload.class); //выполняем
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


    public String transferDB(){

//        ScriptContext scriptContext = ContextPool.borrowContext();
//        Context context = scriptContext.getContext();

        Context context = Context.newBuilder("python")
                .allowHostAccess(HostAccess.ALL)
                .allowAllAccess(true)
                .option("python.ForceImportSite", "true")
                .engine(Engine.create())
                .build();;

        try {

            Source sourceFile1 = Source
                    .newBuilder("python", new File("/projects/graalvm_srv/SpringBoot-GraalVM-Server/src/main/resources/static/Scripts/db.py"))
                    .build();

            Connection connection = DriverManager.getConnection("jdbc:sqlserver://10.35.0.5;databaseName=naswms;encrypt=true;trustServerCertificate=true"
                    , "nas"
                    , "Nas2024$");



            context.eval(sourceFile1);

//            context.getBindings("python").putMember("zap", new ScriptPayload("req", "resp", "connection"));
            Value function = context.getBindings("python").getMember("main"); //выбираем метод который нужен
            ResultSet result = function.execute(new ScriptPayload("req", "resp", connection)).as(ResultSet.class); //выполняем
            System.out.println("Результат: " + result.getString("USERNAME"));

            return "Результат: " + result;
        }
        catch (IOException e) {
            log.error(e.toString());
            e.printStackTrace();
            return "Ошибка:" + e.toString();
        } catch (SQLException e) {
            log.error(e.toString());
            e.printStackTrace();
            return "Ошибка:" + e.toString();
        }
//        finally {
//            ContextPool.returnContext(scriptContext);
//        }
    }


}
