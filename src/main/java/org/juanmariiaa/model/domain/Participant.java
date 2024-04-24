package org.juanmariiaa.model.domain;

import org.juanmariiaa.model.enums.Gender;
import org.juanmariiaa.model.enums.Role;

import java.util.Objects;

public class Participant extends Person {
    private int age;
    private Role role;
    private Gender gender;
    private Team team;

    public Participant() {
        super();
        this.age = 0;
        this.role = null;
        this.gender = null;
        this.team = null;
    }

    public Participant(String dni, String name, String surname, int age, Role role, Gender gender) {
        super(dni, name, surname);
        this.age = age;
        this.role = role;
        this.gender = gender;
        this.team = team;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), age, role, gender, team);
    }

    @Override
    public String toString() {
        return "Participant{" +
                "age=" + age +
                ", role=" + role +
                ", gender=" + gender +
                ", team=" + team +
                '}';
    }
}
