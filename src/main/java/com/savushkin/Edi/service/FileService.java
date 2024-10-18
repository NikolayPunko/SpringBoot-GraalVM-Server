package com.savushkin.Edi.service;

import com.savushkin.Edi.dto.NewScriptDTO;
import com.savushkin.Edi.model.RequestObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
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
        String classpath = "/projects/graalvm_srv/SpringBoot-GraalVM-Server/target/classes:/root/.m2/repository/org/aspectj/aspectjweaver/1.9.22.1/aspectjweaver-1.9.22.1.jar:/root/.m2/repository/com/zaxxer/HikariCP/5.1.0/HikariCP-5.1.0.jar:/root/.m2/repository/org/springframework/spring-jdbc/6.1.12/spring-jdbc-6.1.12.jar:/root/.m2/repository/org/hibernate/orm/hibernate-core/6.5.2.Final/hibernate-core-6.5.2.Final.jar:/root/.m2/repository/jakarta/persistence/jakarta.persistence-api/3.1.0/jakarta.persistence-api-3.1.0.jar:/root/.m2/repository/jakarta/transaction/jakarta.transaction-api/2.0.1/jakarta.transaction-api-2.0.1.jar:/root/.m2/repository/org/jboss/logging/jboss-logging/3.5.3.Final/jboss-logging-3.5.3.Final.jar:/root/.m2/repository/org/hibernate/common/hibernate-commons-annotations/6.0.6.Final/hibernate-commons-annotations-6.0.6.Final.jar:/root/.m2/repository/io/smallrye/jandex/3.1.2/jandex-3.1.2.jar:/root/.m2/repository/com/fasterxml/classmate/1.7.0/classmate-1.7.0.jar:/root/.m2/repository/net/bytebuddy/byte-buddy/1.14.19/byte-buddy-1.14.19.jar:/root/.m2/repository/org/glassfish/jaxb/jaxb-runtime/4.0.5/jaxb-runtime-4.0.5.jar:/root/.m2/repository/org/glassfish/jaxb/jaxb-core/4.0.5/jaxb-core-4.0.5.jar:/root/.m2/repository/org/eclipse/angus/angus-activation/2.0.2/angus-activation-2.0.2.jar:/root/.m2/repository/org/glassfish/jaxb/txw2/4.0.5/txw2-4.0.5.jar:/root/.m2/repository/com/sun/istack/istack-commons-runtime/4.1.2/istack-commons-runtime-4.1.2.jar:/root/.m2/repository/jakarta/inject/jakarta.inject-api/2.0.1/jakarta.inject-api-2.0.1.jar:/root/.m2/repository/org/antlr/antlr4-runtime/4.13.0/antlr4-runtime-4.13.0.jar:/root/.m2/repository/org/springframework/data/spring-data-jpa/3.3.3/spring-data-jpa-3.3.3.jar:/root/.m2/repository/org/springframework/data/spring-data-commons/3.3.3/spring-data-commons-3.3.3.jar:/root/.m2/repository/org/springframework/spring-orm/6.1.12/spring-orm-6.1.12.jar:/root/.m2/repository/org/springframework/spring-beans/6.1.12/spring-beans-6.1.12.jar:/root/.m2/repository/jakarta/annotation/jakarta.annotation-api/2.1.1/jakarta.annotation-api-2.1.1.jar:/root/.m2/repository/org/slf4j/slf4j-api/2.0.16/slf4j-api-2.0.16.jar:/root/.m2/repository/org/springframework/spring-aspects/6.1.12/spring-aspects-6.1.12.jar:/root/.m2/repository/org/springframework/boot/spring-boot/3.3.3/spring-boot-3.3.3.jar:/root/.m2/repository/org/springframework/boot/spring-boot-autoconfigure/3.3.3/spring-boot-autoconfigure-3.3.3.jar:/root/.m2/repository/ch/qos/logback/logback-classic/1.5.7/logback-classic-1.5.7.jar:/root/.m2/repository/org/apache/logging/log4j/log4j-to-slf4j/2.23.1/log4j-to-slf4j-2.23.1.jar:/root/.m2/repository/org/apache/logging/log4j/log4j-api/2.23.1/log4j-api-2.23.1.jar:/root/.m2/repository/org/slf4j/jul-to-slf4j/2.0.16/jul-to-slf4j-2.0.16.jar:/root/.m2/repository/org/yaml/snakeyaml/2.2/snakeyaml-2.2.jar:/root/.m2/repository/org/springframework/spring-aop/6.1.12/spring-aop-6.1.12.jar:/root/.m2/repository/org/springframework/security/spring-security-config/6.3.3/spring-security-config-6.3.3.jar:/root/.m2/repository/org/springframework/security/spring-security-core/6.3.3/spring-security-core-6.3.3.jar:/root/.m2/repository/org/springframework/security/spring-security-crypto/6.3.3/spring-security-crypto-6.3.3.jar:/root/.m2/repository/org/springframework/security/spring-security-web/6.3.3/spring-security-web-6.3.3.jar:/root/.m2/repository/org/springframework/spring-expression/6.1.12/spring-expression-6.1.12.jar:/root/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jdk8/2.17.2/jackson-datatype-jdk8-2.17.2.jar:/root/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.17.2/jackson-datatype-jsr310-2.17.2.jar:/root/.m2/repository/com/fasterxml/jackson/module/jackson-module-parameter-names/2.17.2/jackson-module-parameter-names-2.17.2.jar:/root/.m2/repository/org/apache/tomcat/embed/tomcat-embed-core/10.1.28/tomcat-embed-core-10.1.28.jar:/root/.m2/repository/org/apache/tomcat/embed/tomcat-embed-websocket/10.1.28/tomcat-embed-websocket-10.1.28.jar:/root/.m2/repository/org/springframework/spring-web/6.1.12/spring-web-6.1.12.jar:/root/.m2/repository/org/springframework/spring-webmvc/6.1.12/spring-webmvc-6.1.12.jar:/root/.m2/repository/org/apache/tomcat/embed/tomcat-embed-el/10.1.28/tomcat-embed-el-10.1.28.jar:/root/.m2/repository/org/hibernate/validator/hibernate-validator/8.0.1.Final/hibernate-validator-8.0.1.Final.jar:/root/.m2/repository/jakarta/validation/jakarta.validation-api/3.0.2/jakarta.validation-api-3.0.2.jar:/root/.m2/repository/io/lettuce/lettuce-core/6.3.2.RELEASE/lettuce-core-6.3.2.RELEASE.jar:/root/.m2/repository/io/netty/netty-common/4.1.112.Final/netty-common-4.1.112.Final.jar:/root/.m2/repository/io/netty/netty-handler/4.1.112.Final/netty-handler-4.1.112.Final.jar:/root/.m2/repository/io/netty/netty-resolver/4.1.112.Final/netty-resolver-4.1.112.Final.jar:/root/.m2/repository/io/netty/netty-buffer/4.1.112.Final/netty-buffer-4.1.112.Final.jar:/root/.m2/repository/io/netty/netty-transport-native-unix-common/4.1.112.Final/netty-transport-native-unix-common-4.1.112.Final.jar:/root/.m2/repository/io/netty/netty-codec/4.1.112.Final/netty-codec-4.1.112.Final.jar:/root/.m2/repository/io/netty/netty-transport/4.1.112.Final/netty-transport-4.1.112.Final.jar:/root/.m2/repository/io/projectreactor/reactor-core/3.6.9/reactor-core-3.6.9.jar:/root/.m2/repository/org/reactivestreams/reactive-streams/1.0.4/reactive-streams-1.0.4.jar:/root/.m2/repository/org/springframework/data/spring-data-redis/3.3.3/spring-data-redis-3.3.3.jar:/root/.m2/repository/org/springframework/data/spring-data-keyvalue/3.3.3/spring-data-keyvalue-3.3.3.jar:/root/.m2/repository/org/springframework/spring-oxm/6.1.12/spring-oxm-6.1.12.jar:/root/.m2/repository/org/springframework/spring-context-support/6.1.12/spring-context-support-6.1.12.jar:/root/.m2/repository/org/springframework/kafka/spring-kafka/3.2.3/spring-kafka-3.2.3.jar:/root/.m2/repository/org/springframework/spring-context/6.1.12/spring-context-6.1.12.jar:/root/.m2/repository/org/springframework/spring-messaging/6.1.12/spring-messaging-6.1.12.jar:/root/.m2/repository/org/springframework/spring-tx/6.1.12/spring-tx-6.1.12.jar:/root/.m2/repository/org/springframework/retry/spring-retry/2.0.8/spring-retry-2.0.8.jar:/root/.m2/repository/org/apache/kafka/kafka-clients/3.7.1/kafka-clients-3.7.1.jar:/root/.m2/repository/com/github/luben/zstd-jni/1.5.6-3/zstd-jni-1.5.6-3.jar:/root/.m2/repository/org/lz4/lz4-java/1.8.0/lz4-java-1.8.0.jar:/root/.m2/repository/org/xerial/snappy/snappy-java/1.1.10.5/snappy-java-1.1.10.5.jar:/root/.m2/repository/io/micrometer/micrometer-observation/1.13.3/micrometer-observation-1.13.3.jar:/root/.m2/repository/io/micrometer/micrometer-commons/1.13.3/micrometer-commons-1.13.3.jar:/root/.m2/repository/com/auth0/java-jwt/4.4.0/java-jwt-4.4.0.jar:/root/.m2/repository/org/projectlombok/lombok/1.18.34/lombok-1.18.34.jar:/root/.m2/repository/org/postgresql/postgresql/42.7.3/postgresql-42.7.3.jar:/root/.m2/repository/org/checkerframework/checker-qual/3.42.0/checker-qual-3.42.0.jar:/root/.m2/repository/com/microsoft/sqlserver/mssql-jdbc/12.6.1.jre11/mssql-jdbc-12.6.1.jre11.jar:/root/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.16.2/jackson-databind-2.16.2.jar:/root/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.17.2/jackson-annotations-2.17.2.jar:/root/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.17.2/jackson-core-2.17.2.jar:/root/.m2/repository/org/modelmapper/modelmapper/3.2.0/modelmapper-3.2.0.jar:/root/.m2/repository/jakarta/xml/bind/jakarta.xml.bind-api/4.0.2/jakarta.xml.bind-api-4.0.2.jar:/root/.m2/repository/jakarta/activation/jakarta.activation-api/2.1.3/jakarta.activation-api-2.1.3.jar:/root/.m2/repository/org/springframework/spring-core/6.1.12/spring-core-6.1.12.jar:/root/.m2/repository/org/springframework/spring-jcl/6.1.12/spring-jcl-6.1.12.jar:/root/.m2/repository/com/h2database/h2/2.2.224/h2-2.2.224.jar:/root/.m2/repository/org/graalvm/polyglot/polyglot/23.1.0/polyglot-23.1.0.jar:/root/.m2/repository/org/graalvm/sdk/collections/23.1.0/collections-23.1.0.jar:/root/.m2/repository/org/graalvm/sdk/nativeimage/23.1.0/nativeimage-23.1.0.jar:/root/.m2/repository/org/graalvm/sdk/word/23.1.0/word-23.1.0.jar:/root/.m2/repository/dev/akkinoc/spring/boot/logback-access-spring-boot-starter/4.1.1/logback-access-spring-boot-starter-4.1.1.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.9.25/kotlin-stdlib-jdk8-1.9.25.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib/1.9.25/kotlin-stdlib-1.9.25.jar:/root/.m2/repository/org/jetbrains/annotations/13.0/annotations-13.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.9.25/kotlin-stdlib-jdk7-1.9.25.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-reflect/1.9.25/kotlin-reflect-1.9.25.jar:/root/.m2/repository/ch/qos/logback/logback-access/1.4.11/logback-access-1.4.11.jar:/root/.m2/repository/ch/qos/logback/logback-core/1.5.7/logback-core-1.5.7.jar:/root/.m2/repository/org/json/json/20240303/json-20240303.jar";

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("/projects/graalvm_srv/scripts/logCompile.log");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(null, outputStream, outputStream, "-classpath", classpath, filePath);
        if (result != 0) {
            throw new RuntimeException("Ошибка компиляции " + filePath);
        }

    }

    public String executeClass(String className, String methodName, RequestObj payload) {

        String classpath = "/projects/graalvm_srv/SpringBoot-GraalVM-Server/target/classes:/root/.m2/repository/org/aspectj/aspectjweaver/1.9.22.1/aspectjweaver-1.9.22.1.jar:/root/.m2/repository/com/zaxxer/HikariCP/5.1.0/HikariCP-5.1.0.jar:/root/.m2/repository/org/springframework/spring-jdbc/6.1.12/spring-jdbc-6.1.12.jar:/root/.m2/repository/org/hibernate/orm/hibernate-core/6.5.2.Final/hibernate-core-6.5.2.Final.jar:/root/.m2/repository/jakarta/persistence/jakarta.persistence-api/3.1.0/jakarta.persistence-api-3.1.0.jar:/root/.m2/repository/jakarta/transaction/jakarta.transaction-api/2.0.1/jakarta.transaction-api-2.0.1.jar:/root/.m2/repository/org/jboss/logging/jboss-logging/3.5.3.Final/jboss-logging-3.5.3.Final.jar:/root/.m2/repository/org/hibernate/common/hibernate-commons-annotations/6.0.6.Final/hibernate-commons-annotations-6.0.6.Final.jar:/root/.m2/repository/io/smallrye/jandex/3.1.2/jandex-3.1.2.jar:/root/.m2/repository/com/fasterxml/classmate/1.7.0/classmate-1.7.0.jar:/root/.m2/repository/net/bytebuddy/byte-buddy/1.14.19/byte-buddy-1.14.19.jar:/root/.m2/repository/org/glassfish/jaxb/jaxb-runtime/4.0.5/jaxb-runtime-4.0.5.jar:/root/.m2/repository/org/glassfish/jaxb/jaxb-core/4.0.5/jaxb-core-4.0.5.jar:/root/.m2/repository/org/eclipse/angus/angus-activation/2.0.2/angus-activation-2.0.2.jar:/root/.m2/repository/org/glassfish/jaxb/txw2/4.0.5/txw2-4.0.5.jar:/root/.m2/repository/com/sun/istack/istack-commons-runtime/4.1.2/istack-commons-runtime-4.1.2.jar:/root/.m2/repository/jakarta/inject/jakarta.inject-api/2.0.1/jakarta.inject-api-2.0.1.jar:/root/.m2/repository/org/antlr/antlr4-runtime/4.13.0/antlr4-runtime-4.13.0.jar:/root/.m2/repository/org/springframework/data/spring-data-jpa/3.3.3/spring-data-jpa-3.3.3.jar:/root/.m2/repository/org/springframework/data/spring-data-commons/3.3.3/spring-data-commons-3.3.3.jar:/root/.m2/repository/org/springframework/spring-orm/6.1.12/spring-orm-6.1.12.jar:/root/.m2/repository/org/springframework/spring-beans/6.1.12/spring-beans-6.1.12.jar:/root/.m2/repository/jakarta/annotation/jakarta.annotation-api/2.1.1/jakarta.annotation-api-2.1.1.jar:/root/.m2/repository/org/slf4j/slf4j-api/2.0.16/slf4j-api-2.0.16.jar:/root/.m2/repository/org/springframework/spring-aspects/6.1.12/spring-aspects-6.1.12.jar:/root/.m2/repository/org/springframework/boot/spring-boot/3.3.3/spring-boot-3.3.3.jar:/root/.m2/repository/org/springframework/boot/spring-boot-autoconfigure/3.3.3/spring-boot-autoconfigure-3.3.3.jar:/root/.m2/repository/ch/qos/logback/logback-classic/1.5.7/logback-classic-1.5.7.jar:/root/.m2/repository/org/apache/logging/log4j/log4j-to-slf4j/2.23.1/log4j-to-slf4j-2.23.1.jar:/root/.m2/repository/org/apache/logging/log4j/log4j-api/2.23.1/log4j-api-2.23.1.jar:/root/.m2/repository/org/slf4j/jul-to-slf4j/2.0.16/jul-to-slf4j-2.0.16.jar:/root/.m2/repository/org/yaml/snakeyaml/2.2/snakeyaml-2.2.jar:/root/.m2/repository/org/springframework/spring-aop/6.1.12/spring-aop-6.1.12.jar:/root/.m2/repository/org/springframework/security/spring-security-config/6.3.3/spring-security-config-6.3.3.jar:/root/.m2/repository/org/springframework/security/spring-security-core/6.3.3/spring-security-core-6.3.3.jar:/root/.m2/repository/org/springframework/security/spring-security-crypto/6.3.3/spring-security-crypto-6.3.3.jar:/root/.m2/repository/org/springframework/security/spring-security-web/6.3.3/spring-security-web-6.3.3.jar:/root/.m2/repository/org/springframework/spring-expression/6.1.12/spring-expression-6.1.12.jar:/root/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jdk8/2.17.2/jackson-datatype-jdk8-2.17.2.jar:/root/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.17.2/jackson-datatype-jsr310-2.17.2.jar:/root/.m2/repository/com/fasterxml/jackson/module/jackson-module-parameter-names/2.17.2/jackson-module-parameter-names-2.17.2.jar:/root/.m2/repository/org/apache/tomcat/embed/tomcat-embed-core/10.1.28/tomcat-embed-core-10.1.28.jar:/root/.m2/repository/org/apache/tomcat/embed/tomcat-embed-websocket/10.1.28/tomcat-embed-websocket-10.1.28.jar:/root/.m2/repository/org/springframework/spring-web/6.1.12/spring-web-6.1.12.jar:/root/.m2/repository/org/springframework/spring-webmvc/6.1.12/spring-webmvc-6.1.12.jar:/root/.m2/repository/org/apache/tomcat/embed/tomcat-embed-el/10.1.28/tomcat-embed-el-10.1.28.jar:/root/.m2/repository/org/hibernate/validator/hibernate-validator/8.0.1.Final/hibernate-validator-8.0.1.Final.jar:/root/.m2/repository/jakarta/validation/jakarta.validation-api/3.0.2/jakarta.validation-api-3.0.2.jar:/root/.m2/repository/io/lettuce/lettuce-core/6.3.2.RELEASE/lettuce-core-6.3.2.RELEASE.jar:/root/.m2/repository/io/netty/netty-common/4.1.112.Final/netty-common-4.1.112.Final.jar:/root/.m2/repository/io/netty/netty-handler/4.1.112.Final/netty-handler-4.1.112.Final.jar:/root/.m2/repository/io/netty/netty-resolver/4.1.112.Final/netty-resolver-4.1.112.Final.jar:/root/.m2/repository/io/netty/netty-buffer/4.1.112.Final/netty-buffer-4.1.112.Final.jar:/root/.m2/repository/io/netty/netty-transport-native-unix-common/4.1.112.Final/netty-transport-native-unix-common-4.1.112.Final.jar:/root/.m2/repository/io/netty/netty-codec/4.1.112.Final/netty-codec-4.1.112.Final.jar:/root/.m2/repository/io/netty/netty-transport/4.1.112.Final/netty-transport-4.1.112.Final.jar:/root/.m2/repository/io/projectreactor/reactor-core/3.6.9/reactor-core-3.6.9.jar:/root/.m2/repository/org/reactivestreams/reactive-streams/1.0.4/reactive-streams-1.0.4.jar:/root/.m2/repository/org/springframework/data/spring-data-redis/3.3.3/spring-data-redis-3.3.3.jar:/root/.m2/repository/org/springframework/data/spring-data-keyvalue/3.3.3/spring-data-keyvalue-3.3.3.jar:/root/.m2/repository/org/springframework/spring-oxm/6.1.12/spring-oxm-6.1.12.jar:/root/.m2/repository/org/springframework/spring-context-support/6.1.12/spring-context-support-6.1.12.jar:/root/.m2/repository/org/springframework/kafka/spring-kafka/3.2.3/spring-kafka-3.2.3.jar:/root/.m2/repository/org/springframework/spring-context/6.1.12/spring-context-6.1.12.jar:/root/.m2/repository/org/springframework/spring-messaging/6.1.12/spring-messaging-6.1.12.jar:/root/.m2/repository/org/springframework/spring-tx/6.1.12/spring-tx-6.1.12.jar:/root/.m2/repository/org/springframework/retry/spring-retry/2.0.8/spring-retry-2.0.8.jar:/root/.m2/repository/org/apache/kafka/kafka-clients/3.7.1/kafka-clients-3.7.1.jar:/root/.m2/repository/com/github/luben/zstd-jni/1.5.6-3/zstd-jni-1.5.6-3.jar:/root/.m2/repository/org/lz4/lz4-java/1.8.0/lz4-java-1.8.0.jar:/root/.m2/repository/org/xerial/snappy/snappy-java/1.1.10.5/snappy-java-1.1.10.5.jar:/root/.m2/repository/io/micrometer/micrometer-observation/1.13.3/micrometer-observation-1.13.3.jar:/root/.m2/repository/io/micrometer/micrometer-commons/1.13.3/micrometer-commons-1.13.3.jar:/root/.m2/repository/com/auth0/java-jwt/4.4.0/java-jwt-4.4.0.jar:/root/.m2/repository/org/projectlombok/lombok/1.18.34/lombok-1.18.34.jar:/root/.m2/repository/org/postgresql/postgresql/42.7.3/postgresql-42.7.3.jar:/root/.m2/repository/org/checkerframework/checker-qual/3.42.0/checker-qual-3.42.0.jar:/root/.m2/repository/com/microsoft/sqlserver/mssql-jdbc/12.6.1.jre11/mssql-jdbc-12.6.1.jre11.jar:/root/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.16.2/jackson-databind-2.16.2.jar:/root/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.17.2/jackson-annotations-2.17.2.jar:/root/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.17.2/jackson-core-2.17.2.jar:/root/.m2/repository/org/modelmapper/modelmapper/3.2.0/modelmapper-3.2.0.jar:/root/.m2/repository/jakarta/xml/bind/jakarta.xml.bind-api/4.0.2/jakarta.xml.bind-api-4.0.2.jar:/root/.m2/repository/jakarta/activation/jakarta.activation-api/2.1.3/jakarta.activation-api-2.1.3.jar:/root/.m2/repository/org/springframework/spring-core/6.1.12/spring-core-6.1.12.jar:/root/.m2/repository/org/springframework/spring-jcl/6.1.12/spring-jcl-6.1.12.jar:/root/.m2/repository/com/h2database/h2/2.2.224/h2-2.2.224.jar:/root/.m2/repository/org/graalvm/polyglot/polyglot/23.1.0/polyglot-23.1.0.jar:/root/.m2/repository/org/graalvm/sdk/collections/23.1.0/collections-23.1.0.jar:/root/.m2/repository/org/graalvm/sdk/nativeimage/23.1.0/nativeimage-23.1.0.jar:/root/.m2/repository/org/graalvm/sdk/word/23.1.0/word-23.1.0.jar:/root/.m2/repository/dev/akkinoc/spring/boot/logback-access-spring-boot-starter/4.1.1/logback-access-spring-boot-starter-4.1.1.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.9.25/kotlin-stdlib-jdk8-1.9.25.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib/1.9.25/kotlin-stdlib-1.9.25.jar:/root/.m2/repository/org/jetbrains/annotations/13.0/annotations-13.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.9.25/kotlin-stdlib-jdk7-1.9.25.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-reflect/1.9.25/kotlin-reflect-1.9.25.jar:/root/.m2/repository/ch/qos/logback/logback-access/1.4.11/logback-access-1.4.11.jar:/root/.m2/repository/ch/qos/logback/logback-core/1.5.7/logback-core-1.5.7.jar:/root/.m2/repository/org/json/json/20240303/json-20240303.jar";
        String[] paths = classpath.split(":");

        URL[] urls = new URL[paths.length + 1];
        try {
            for (int i = 0; i < paths.length; i++) {
                urls[i] = new File(paths[i]).toURI().toURL();
            }
            urls[urls.length - 1] = new File("/projects/graalvm_srv/scripts").toURI().toURL(); //добавляем к classpath где лежат скрипты

        } catch (MalformedURLException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }

        try(URLClassLoader classLoader = new URLClassLoader(urls)) {
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
