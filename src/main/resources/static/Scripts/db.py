import java
ScriptPayload = java.type("com.host.SpringBootGraalVMServer.model.ScriptPayload")


def main(zap: ScriptPayload):
    request = zap.getRequest()
    connection = zap.getConnection()
    statement = connection.createStatement()
    resultSet = statement.executeQuery('SELECT * FROM BD_NASUSR')
    return resultSet