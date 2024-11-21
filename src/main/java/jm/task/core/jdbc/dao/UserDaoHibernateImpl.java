package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {
        String q = "create table USERS" +
                " (id integer primary key autoincrement," +
                " name varchar(255) not null," +
                " last_name varchar(255) not null," +
                " age integer not NULL" +
                ");";
        executeNativeSql(q);
    }

    @Override
    public void dropUsersTable() {
        String q = "drop table if exists USERS;";
        executeNativeSql(q);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session s = Util.getSession();
        Transaction tx = null;
        User user = new User(name, lastName, age);

        try {
            tx = s.beginTransaction();
            s.save(user);
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            s.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session s = Util.getSession();
        Transaction tx = null;

        try {
            User user = s.get(User.class, id);
            if (user == null) return;

            tx = s.beginTransaction();
            s.remove(user);
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            s.close();
        }
    }

    @Override
    public List<User> getAllUsers() {

        Session s = Util.getSession();

        try {
            return s.createQuery("from User", User.class).list();
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            s.close();
        }

        return new ArrayList<>();
    }

    @Override
    public void cleanUsersTable() {
        Session s = Util.getSession();
        Transaction tx = null;

        try {
            tx = s.beginTransaction();
            s.createQuery("delete from User").executeUpdate();
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            s.close();
        }
    }

    /**
     * Reducing copypaste :)
     * @param query string
     * @return count of affected rows
     */
    private int executeNativeSql(String query)
    {
        Session s = Util.getSession();
        Transaction tx = null;

        int affected = 0;

        try {
            tx = s.beginTransaction();
            affected = s.createSQLQuery(query).executeUpdate();
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            s.close();
        }

        return affected;
    }
}
