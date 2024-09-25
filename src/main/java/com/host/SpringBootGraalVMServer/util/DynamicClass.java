

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicClass {

    public String transferDB3(String str){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        List<String> list = new ArrayList<>();

        try {
            connection =  connection = DriverManager.getConnection(str
                    , "nas"
                    , "Nas2024$");
            statement = connection.createStatement();
            String sql = "SELECT * FROM BD_NASUSR";
            resultSet = statement.executeQuery(sql);

            int columnCount = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()) {

                Map<String,String> map = new HashMap<>();

                for (int i = 0; i < columnCount; i++) {
                    String col = resultSet.getMetaData().getColumnName(i+1);
                    String val = resultSet.getString(i+1);
                    map.put(col,val);
                }
                list.add(map.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list.toString();
        }
    }

}
