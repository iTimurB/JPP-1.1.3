package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFromDB {
    private static final String URL = "jdbc:mysql://localhost:3306/testt?useSSL=false&serverTimezone=UTC";
    private static final String NAME = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public ConnectionFromDB() {
    }

    public static Connection getConnection() {
        Connection connection;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("DB connection error");
            e.printStackTrace();
        }
        return null;
    }
}
