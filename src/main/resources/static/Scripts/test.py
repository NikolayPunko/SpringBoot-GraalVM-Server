import java
ScriptPayload = java.type("com.host.SpringBootGraalVMServer.model.ScriptPayload")


def main(zap: ScriptPayload):
    zap.setResponse(zap.getRequest());
    return zap
