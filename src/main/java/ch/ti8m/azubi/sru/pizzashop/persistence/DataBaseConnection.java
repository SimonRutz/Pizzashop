package ch.ti8m.azubi.sru.pizzashop.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    public Connection connection() throws SQLException, ClassNotFoundException {

        String dbDriver = "com.mysql.jdbc.Driver";
        String dbURL = "jdbc:mysql://localhost:3306/";
        String dbName = "pizzashop";
        String user = "root";
        String password = "SsRrUu_4682";

        Class.forName(dbDriver);

        return DriverManager.getConnection(dbURL + dbName, user, password);
    }
}


