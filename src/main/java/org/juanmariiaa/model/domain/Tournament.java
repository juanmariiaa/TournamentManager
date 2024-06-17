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
    private List<Picture> pictures;

    public Tournament() {
        this.id = 0;
        this.name = "";
        this.location = "";
        this.city = "";
        this.date = null;
        this.teams = null;
        this.pictures = null;
    }

    public Tournament(String name, String location, String city, Date date, List<Team> teams, List<Picture> pictures) {
        this.id = 0;
        this.name = name;
        this.location = location;
        this.city = city;
        this.date = date;
        this.teams = teams;
        this.pictures = pictures;
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

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
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
                ", pictures=" + pictures +
                '}';
    }
}
