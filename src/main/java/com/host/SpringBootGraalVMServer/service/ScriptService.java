package com.host.SpringBootGraalVMServer.service;

import com.host.SpringBootGraalVMServer.model.ScriptContext;
import com.host.SpringBootGraalVMServer.model.ScriptPayload;
import com.host.SpringBootGraalVMServer.pool.ContextPool;
import com.host.SpringBootGraalVMServer.scriptConfiguration.PythonConfiguration;
import org.springframework.stereotype.Service;

@Service
public class ScriptService {

    public ScriptPayload getmark(){
        ScriptContext context = ContextPool.borrowContext();
        ScriptPayload result = PythonConfiguration.getPythonService(context).getmark(new ScriptPayload("запрос", "ответ", "соединение"));
        ContextPool.returnContext(context);
        return result;
    }

}
