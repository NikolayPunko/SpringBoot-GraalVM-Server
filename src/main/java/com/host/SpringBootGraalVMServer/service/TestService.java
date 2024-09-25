package com.host.SpringBootGraalVMServer.service;

import com.host.SpringBootGraalVMServer.model.ScriptPayload;
import com.host.SpringBootGraalVMServer.model.TestObj;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

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

        Connection connection = null;

        Context context = Context.newBuilder("python")
                .allowHostAccess(HostAccess.ALL)
                .allowAllAccess(true)
                .option("python.ForceImportSite", "true")
                .engine(Engine.create())
                .build();;

        try {

            Source sourceFile1 = Source
//                    .newBuilder("python", new File("/projects/graalvm_srv/SpringBoot-GraalVM-Server/src/main/resources/static/Scripts/db.py"))
                    .newBuilder("python", new File("/projects/graalvm_srv/SpringBoot-GraalVM-Server/src/main/resources/static/Scripts/script5.py"))
                    .build();

            connection = DriverManager.getConnection("jdbc:sqlserver://10.35.0.5;databaseName=naswms;encrypt=true;trustServerCertificate=true"
                    , "nas"
                    , "Nas2024$");


            context.eval(sourceFile1);

            Value function = context.getBindings("python").getMember("main"); //выбираем метод который нужен
            ScriptPayload scriptPayload = function.execute(new ScriptPayload("req", "resp", connection)).as(ScriptPayload.class); //выполняем

            return "Результат: " + scriptPayload.getResponse();

        }
        catch (IOException e) {
            log.error(e.toString());
            e.printStackTrace();
            return "Ошибка:" + e.toString();
        }
        catch (SQLException e) {
            log.error(e.toString());
            e.printStackTrace();
            return "Ошибка:" + e.toString();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public String transferDB2(){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        Map<Integer,TestObj> map = new HashMap<>();

        try {
            connection =  connection = DriverManager.getConnection("jdbc:sqlserver://10.35.0.5;databaseName=naswms;encrypt=true;trustServerCertificate=true"
                    , "nas"
                    , "Nas2024$");
            statement = connection.createStatement();
            String sql = "SELECT * FROM BD_NASUSR";
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("F_ID");
                String name = resultSet.getString("USERNAME");
                map.put(id, new TestObj(id,name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return map.toString();
        }
    }


}
