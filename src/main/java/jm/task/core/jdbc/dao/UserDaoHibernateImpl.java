package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS USER (ID INT NOT NULL AUTO_INCREMENT, NAME VARCHAR(30), LASTNAME VARCHAR(30), AGE INT, PRIMARY KEY (ID));").executeUpdate();
            session.beginTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null)
                session.getTransaction().rollback();
        } finally {
            session.close();
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS USER").executeUpdate();
            session.beginTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null)
                session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        User user = new User(name, lastName, age);
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    @Override
    public void removeUserById(long id) {
        Session session = getSessionFactory().openSession();
        User user = (User)session.load(User.class, id);
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(user);
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = getSessionFactory().openSession();
        List<User> users = (List<User>) session.createQuery("from User").list();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from User").executeUpdate();
        transaction.commit();
    }
}
