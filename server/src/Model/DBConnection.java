package Model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getMSSQLConnection(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String host = "";
            String databaseName = "";
            String user = "";
            String password = "";
            String url = String.format("jdbc:sqlserver://%s;databaseName=%s;user=%s;password=%s",host,databaseName,user,password);
            Connection conn = DriverManager.getConnection(url);
            return conn;
        }
        catch(Exception e){

        }
        return null;
    }
}

