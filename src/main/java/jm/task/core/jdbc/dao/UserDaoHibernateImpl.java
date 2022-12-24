package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sf = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sf.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("  CREATE TABLE if NOT EXISTS `schema`.`users` (\n" +
                            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                            "  `name` VARCHAR(45) NOT NULL,\n" +
                            "  `lastName` VARCHAR(45) NOT NULL,\n" +
                            "  `age` INT NOT NULL,\n" +
                            "                PRIMARY KEY (`id`))\n" +
                            "        ENGINE = InnoDB\n" +
                            "        DEFAULT CHARACTER SET = utf8;")
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sf.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE if EXISTS users;").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sf.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            transaction.commit();
            System.out.println("User с именем –" + name + " добавлен в базу данных");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sf.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        TypedQuery<User> query = sf.createEntityManager().createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sf.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(" TRUNCATE TABLE users ;").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

