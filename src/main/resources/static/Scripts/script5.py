import json

import java
ScriptPayload = java.type("com.host.SpringBootGraalVMServer.model.ScriptPayload")


def main(zap: ScriptPayload):
    request = zap.getRequest()
    conn = zap.getConnection()
    statement = conn.createStatement()
    rs = statement.executeQuery(SELECT * FROM BD_NASUSR)
    columnCount = rs.getMetaData().getColumnCount()
    result = []
    while rs.next():
        row = {}
        for i in range(columnCount+1):
            name = rs.getMetaDate().getColumnName(i+1)
            val = rs.getString(i+1)
            row[name] = val
        result.append(row)
    json_result = json.dumps(result, default=str)
    zap.setResponse(json_result)
    return zap