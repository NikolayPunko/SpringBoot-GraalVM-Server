package com.host.SpringBootGraalVMServer.service;

import com.host.SpringBootGraalVMServer.model.ScriptPayload;

public interface PythonService {

    ScriptPayload getmark(ScriptPayload payload);


    void pythonConstructor();
}
