package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        if (conn != null) {
            return conn;
        }

        conn = DriverManager.getConnection("jdbc:sqlite:my_awesome_database.db");
        return conn;
    }

    public static void closeConnection(){
        if (conn == null) return;
        try {
            conn.close();
        } catch (SQLException ignored) {
        }
    }
}
