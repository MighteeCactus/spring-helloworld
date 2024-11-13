package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.dropUsersTable();
        userService.createUsersTable();

        userService.saveUser("Вася", "Хакер", (byte)12);
        userService.saveUser("Петя", "Хакер", (byte)13);
        userService.saveUser("Зинаида", "Муравьёва", (byte)120);
        userService.saveUser("Егорыч", "Админ", (byte)42);

        Stream.of(userService.getAllUsers()).forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();

        Util.closeConnection();
    }
}
