package com.Edi.bc;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DispAppmain {

    public DispAppmain() {
    }

    public List<String> getJsonFormat(ResultSet resultSet) throws SQLException {
        List<String> list = new ArrayList<>();
        List<String> columns = new ArrayList<>();

        int columnCount = resultSet.getMetaData().getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            columns.add(resultSet.getMetaData().getColumnName(i).trim());
        }

        while (resultSet.next()) {

            Map<String, String> map = new HashMap<>();

            for (int i = 0; i < columnCount; i++) {
                String col = columns.get(i);
                String val = resultSet.getObject(i + 1) != null ? resultSet.getString(i + 1).trim() : "";
                map.put(col, val);
            }
            list.add(map.toString());
        }

        return list;
    }

}

