package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private Util() {
    }
    private static SessionFactory sessionFactory = null;
    private static String URL = "jdbc:mysql://localhost:3306/kata";
    private static String userName = "root";
    private static String password = "Marcomru007";
    private static String DRIVER = "com.mysql.cj.jdbc.Driver";
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static SessionFactory getSessionFactory(){
            try {
                Configuration configuration = new Configuration();
                configuration.setProperty("hibernate.connection.driver_class", DRIVER);
                configuration.setProperty("hibernate.connection.url", URL);
                configuration.setProperty("hibernate.connection.username", userName);
                configuration.setProperty("hibernate.connection.password", password);
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        return sessionFactory;
    }
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}