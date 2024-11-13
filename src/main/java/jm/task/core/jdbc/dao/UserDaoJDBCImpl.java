package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            String q = "create table USERS " +
                    "(id INTEGER not NULL PRIMARY KEY," +
                    " name VARCHAR(255) not NULL," +
                    " lastName VARCHAR(255) not NULL," +
                    " age SMALLINT not NULL" +
                    ")";

            Connection conn = Util.getConnection();

            Statement st = conn.createStatement();
            st.executeUpdate(q);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            String q = "drop table if exists USERS;";

            Connection conn = Util.getConnection();

            Statement st = conn.createStatement();
            st.executeUpdate(q);
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            String q = "insert into USERS values(null, ?, ?, ?);";

            Connection conn = Util.getConnection();

            PreparedStatement st = conn.prepareStatement(q);
            st.setString(1, name);
            st.setString(2, lastName);
            st.setByte(3, age);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            String q = "delete from USERS where id = ?;";

            Connection conn = Util.getConnection();

            PreparedStatement st = conn.prepareStatement(q);
            st.setLong(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            String q = "select * from USERS";

            Connection conn = Util.getConnection();
            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery(q);

            while (result.next()) {
                User u = new User(
                        result.getString(2),
                        result.getString(3),
                        result.getByte(4));
                u.setId(result.getLong(1));
                users.add(u);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public void cleanUsersTable() {
        try {
            String q = "delete from USERS;";

            Connection conn = Util.getConnection();

            Statement st = conn.createStatement();
            st.executeUpdate(q);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
