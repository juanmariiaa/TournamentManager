package org.juanmariiaa.model.domain;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Tournament {
    private int id;
    private String name;
    private String location;
    private String city;
    private Date date;
    private List<Team> teams;

    public Tournament() {
        this.id = 0;
        this.name = "";
        this.location = "";
        this.city = "";
        this.date = null;
        this.teams = null;
    }

    public Tournament(String name, String location, String city, Date date, List<Team> teams) {
        this.id = 0;
        this.name = name;
        this.location = location;
        this.city = city;
        this.date = date;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", city='" + city + '\'' +
                ", date=" + date +
                ", teams=" + teams +
                '}';
    }
}
