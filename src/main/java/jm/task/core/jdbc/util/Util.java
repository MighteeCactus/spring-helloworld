package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Connection conn;
    private static SessionFactory sessionFactory;

    public static Connection getConnection() throws SQLException {
        if (conn != null) {
            return conn;
        }

        conn = DriverManager.getConnection("jdbc:sqlite:my_awesome_database.db");
        return conn;
    }

    public static void closeConnection() {
        if (conn == null) return;
        try {
            conn.close();
        } catch (SQLException ignored) {
        }
    }

    public static Session getSession() {
        if (sessionFactory == null) {
            sessionFactory = createSessionFactory();
        }

        return sessionFactory.openSession();
    }

    private static SessionFactory createSessionFactory() {
        Configuration config = new Configuration();

        config.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC")
                .setProperty("hibernate.connection.url", "jdbc:sqlite:my_awesome_database.db")
                .setProperty("hibernate.dialect", "org.sqlite.hibernate.dialect.SQLiteDialect")
                .setProperty("hibernate.hbm2ddl", "create-drop")
                .addAnnotatedClass(User.class);

        return config.buildSessionFactory();
    }
}
