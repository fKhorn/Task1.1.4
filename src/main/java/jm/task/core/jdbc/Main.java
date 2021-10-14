package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.dropUsersTable();

        userService.createUsersTable();

        userService.saveUser("Sam", "Sam", (byte) 18);
        userService.saveUser("Richard", "Richard", (byte) 8);
        userService.saveUser("John", "John", (byte) 38);
        userService.saveUser("Pit", "Pit", (byte) 20);

        for(User user: userService.getAllUsers()) {
            System.out.println(user);
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();

    }
}
