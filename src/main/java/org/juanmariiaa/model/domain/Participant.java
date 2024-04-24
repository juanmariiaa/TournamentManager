package org.juanmariiaa.model.domain;

import org.juanmariiaa.model.enums.Gender;
import org.juanmariiaa.model.enums.Role;

public class Participant extends Persona {
    private int age;
    private Role role;
    private Gender gender;

    public Participant() {
        super();
        this.age = 0;
        this.role = null;
        this.gender = null;
    }

    public Participant(String dni, String name, String surname, int age, Role role, Gender gender) {
        super(dni, name, surname);
        this.age = age;
        this.role = role;
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Participant{"+super.toString()+"age=" + age + ", role=" + role + ", gender=" + gender + '}';
    }
}
