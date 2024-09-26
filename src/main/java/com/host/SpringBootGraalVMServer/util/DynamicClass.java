
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestImportClass {

    public String main(){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        List<String> list = new ArrayList<>();

        try {
            connection = DriverManager.getConnection("jdbc:sqlserver://10.35.0.10;databaseName=disp;encrypt=true;trustServerCertificate=true"
                    , "web"
                    , "Web2024$");
            statement = connection.createStatement();
            String sql = "SELECT * FROM NS_MC";
            resultSet = statement.executeQuery(sql);

            int columnCount = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()) {

                Map<String,String> map = new HashMap<>();

                for (int i = 0; i < columnCount; i++) {
                    String col = resultSet.getMetaData().getColumnName(i+1).trim();
                    String val = resultSet.getObject(i+1)!=null?  resultSet.getString(i+1).trim() : "";
                    map.put(col,val);
                }
                list.add(map.toString());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
