package com.host.SpringBootGraalVMServer.service;

import com.host.SpringBootGraalVMServer.model.Person;
import com.host.SpringBootGraalVMServer.model.ScriptContext;
import com.host.SpringBootGraalVMServer.pool.ContextPool;
import com.host.SpringBootGraalVMServer.scriptConfiguration.PythonConfiguration;
import org.springframework.stereotype.Service;

@Service
public class ScriptService {

    public int getMethod1() {
        ScriptContext context = ContextPool.borrowContext();
        int result = PythonConfiguration.getPythonService(context).testMethod_1(5, 10);
        ContextPool.returnContext(context);
        return result;
    }

    public String getMethod2() {
        ScriptContext context = ContextPool.borrowContext();
        String result = PythonConfiguration.getPythonService(context).testMethod_2();
        ContextPool.returnContext(context);
        return result;
    }

    public String getMethod3() {
        ScriptContext context = ContextPool.borrowContext();
        String result = PythonConfiguration.getPythonService(context).testMethod_3(new Person("Ivan", 35));
        ContextPool.returnContext(context);
        return result;
    }

    public Person getMethod4() {
        ScriptContext context = ContextPool.borrowContext();
        Person result = PythonConfiguration.getPythonService(context).testMethod_4(new Person("Ivan", 35));
        ContextPool.returnContext(context);
        return result;
    }

}
