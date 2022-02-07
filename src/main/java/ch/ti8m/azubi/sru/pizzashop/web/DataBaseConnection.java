package ch.ti8m.azubi.sru.pizzashop.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    protected String host = "localhost";
    protected int port = 3306;
    protected String dbName = "pizzashop";
    protected String user = "root";
    protected String password = "SsRrUu_4682";

    public String connectionURL = String.format("jdbc:mysql://%s:%d/%s", host, port, dbName);
    Connection connection = DriverManager.getConnection(connectionURL, user, password);

    public DataBaseConnection() throws SQLException {

    }

    public Connection getConnection() {
        return connection;
    }
}
