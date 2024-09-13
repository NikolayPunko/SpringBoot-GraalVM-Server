
import java
ScriptPayload = java.type("com.host.SpringBootGraalVMServer.model.ScriptPayload")

class ConnectorImpl:

    @staticmethod
    def getmark(payload: ScriptPayload) :
        payload.setResponse(payload.getRequest());
        return payload