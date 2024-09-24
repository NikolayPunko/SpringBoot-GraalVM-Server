import java
import json

ScriptPayload = java.type("com.host.SpringBootGraalVMServer.model.ScriptPayload")


def main(zap: ScriptPayload):
    request = zap.getRequest()
    connection = zap.getConnection()
    statement = connection.createStatement()
    resultSet = statement.executeQuery('SELECT * FROM BD_NASUSR')


    data = {}

    while resultSet.next():
        key = resultSet.getInt("F_ID")
        value = {
            "F_ID":  resultSet.getInt("F_ID"),
            "USERNAME": resultSet.getString("USERNAME").strip()

        }
        data[key]=value

    json_result = json.dumps(data, default=str)
    zap.setResponse(json_result)
    return zap




