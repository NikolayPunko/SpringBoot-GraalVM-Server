import pyodbc
import json

import java
ScriptPayload = java.type("com.host.SpringBootGraalVMServer.model.ScriptPayload")


def main(zap: ScriptPayload):
    request = zap.getRequest()
    conn = pyodbc.connect('Driver={SQL Server};'
                          'Server=10.30.0.157;'
                          'Database=websrv;'
                          'Trusted_Connection=yes;')
    cursor = conn.cursor()
    cursor.execute('SELECT top 10 * from NS_SRVFORM')
    rows = cursor.fetchall()
    columns = [column[0] for column in cursor.description]
    result = [dict(zip(columns, row)) for row in rows]
    json_result = json.dumps(result, default=str)
    cursor.close()
    conn.close()
    zap.setResponse(json_result);
    return zap