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
        try (Statement st = Util.getConnection().createStatement()) {
            st.execute("  CREATE TABLE if NOT EXISTS `schema`.`users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastName` VARCHAR(45) NOT NULL,\n" +
                    "  `age` INT NOT NULL,\n" +
                    "                PRIMARY KEY (`id`))\n" +
                    "        ENGINE = InnoDB\n" +
                    "        DEFAULT CHARACTER SET = utf8;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement st = Util.getConnection().createStatement()) {
            st.execute("DROP TABLE if EXISTS users;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Statement st = Util.getConnection().createStatement()) {
            st.execute("insert into users (name , lastName, age) values ('" + name + "', '" + lastName + "'," + age + ");");
            System.out.println("User с именем –" + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Statement st = Util.getConnection().createStatement()) {
            st.execute(" DELETE FROM users WHERE id = " + id + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String query = "select * from users";
        List<User> list = new ArrayList<>();
        try (Statement st = Util.getConnection().createStatement()) {
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Statement st = Util.getConnection().createStatement()) {
            st.execute(" TRUNCATE TABLE users ; ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
