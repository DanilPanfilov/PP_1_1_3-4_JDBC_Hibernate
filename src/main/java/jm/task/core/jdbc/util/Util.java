package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import javax.security.auth.login.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/schema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static SessionFactory sessionFactory;
    private static Map<String, String> settings = new HashMap<>();

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    static {
        settings.put("hibernate.connection.url", URL);
        settings.put("hibernate.connection.username", USERNAME);
        settings.put("hibernate.connection.password", PASSWORD);
          final ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(settings).build();
          try {
          sessionFactory = new MetadataSources(registry).addAnnotatedClass(User.class).buildMetadata().buildSessionFactory();
          } catch (Exception e) {
          StandardServiceRegistryBuilder.destroy(registry);
           }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

