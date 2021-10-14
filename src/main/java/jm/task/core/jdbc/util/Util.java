package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.sql.*;
import java.util.Properties;

public class Util {
    private static Util instance = new Util();
    private SessionFactory sessionFactory;
    private Connection connection = null;


    private final String URL = "jdbc:mysql://127.0.0.1:3306/mydb";
    private final String LOGIN = "root";
    private final String PASSWORD = "root";
    private final String DRIVER = "com.mysql.jdbc.Driver";

    private Util() {}

    public static Util getUtil() {
        if(instance == null) {
            instance = new Util();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.URL, "jdbc:mysql://127.0.0.1:3306/mydb?useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "root");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
