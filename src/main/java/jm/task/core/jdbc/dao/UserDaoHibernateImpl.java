package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
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
        Session session = sf.openSession();
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
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("DROP TABLE if EXISTS users;").executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        session.save(user);
        transaction.commit();
        System.out.println("User с именем –" + name + " добавлен в базу данных");
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        User user = new User();
        user.setId(id);
        session.delete(user);
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sf.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root);

        Query query = session.createQuery(cq);
        List<User> list = query.getResultList();
        session.close();
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery(" TRUNCATE TABLE users ;").executeUpdate();
        transaction.commit();
        session.close();
    }
}

