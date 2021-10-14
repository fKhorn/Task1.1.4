package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Util util = Util.getUtil();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users ("
                    + "id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,"
                    + "name VARCHAR(45) NOT NULL ,"
                    + "lastName VARCHAR(45) NOT NULL ,"
                    + "age TINYINT NOT NULL )");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, lastName, age) values (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?")){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection connection = util.getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("firstName"));
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
        try (Connection connection = util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
