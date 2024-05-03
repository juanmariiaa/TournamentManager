package org.juanmariiaa.others;

import org.juanmariiaa.model.domain.User;

public class SingletonUserSession {
    private static User user = null;

    public static void login(int id, String username) {
        user = new User(id, "", username);
    }

    public static void logout() {
        user = null;
    }

    public static boolean isLogged() {
        return user == null ? false : true;
    }

    public static User getUser() {
        return user;
    }
}
