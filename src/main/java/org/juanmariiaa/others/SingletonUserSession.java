package org.juanmariiaa.others;

import org.juanmariiaa.model.domain.User;

public class SingletonUserSession {
    private static User user=null;
    public static void login (String dni,String username) {user=new User(dni,username);}
    public static void logout () {user=null;}
    public static boolean isLogged () {return user==null?false:true;}
    public static User getUser () {return user;}

}
