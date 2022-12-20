package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private static UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        userService.createUsersTable();
        userService.saveUser("QQQQ", "qqqq", (byte) 12);
        userService.saveUser("wwww", "WWWW", (byte) 14);
        userService.saveUser("eeee", "EEEE", (byte) 15);
        userService.saveUser("rrrr", "RRRR", (byte) 16);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
