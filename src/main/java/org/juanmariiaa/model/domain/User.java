package org.juanmariiaa.model.domain;

public class User extends Persona{
    private String user;
    private String password;

    public User() {
        super();
        this.user="";
        this.password="";
    }

    public User(String dni, String user){super(dni, user, "");}

    public User(String dni, String name, String surname, String user, String password) {
        super(dni, name, surname);
        this.user=user;
        this.password=password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User ["+super.toString()+ "user="+ user;
    }



}
