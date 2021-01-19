package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Chuck", "Norris", (byte) 99);
        userService.saveUser("Bruce", "Lee", (byte) 98);
        userService.saveUser("JeanClaude", "VanDamme", (byte) 97);
        userService.saveUser("Arnold", "Schzwarznegger", (byte) 96);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
