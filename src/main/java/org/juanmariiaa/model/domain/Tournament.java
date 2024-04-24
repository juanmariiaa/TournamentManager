package org.juanmariiaa.model.domain;

import java.util.List;
import java.util.Objects;

public class Tournament {
    private int id;
    private String name;
    private String location;
    private List<Team> teams = null;

    public Tournament() {
        this.id = 0;
        this.name = "";
        this.location = "";
        this.teams = null;
    }

    public Tournament(int id, String name, String location, List<Team> teams) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.teams = teams;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tournament that = (Tournament) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "id=" + id +
                ", name='" + name +
                ", location='" + location +
                ", teams=" + teams + '}';
    }

}
