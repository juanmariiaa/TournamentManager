package org.juanmariiaa.model.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Team {
    private int id;
    private String name;
    private String city;
    private String institution;
    private List<Participant> participants; // Remove "= null"

    public Team() {
        this.id = 0;
        this.name = "";
        this.city = "";
        this.institution = "";
        this.participants = new ArrayList<>(); // Initialize participants list
    }

    public Team(int id, String name, String city, String institution, List<Participant> participants) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.institution = institution;
        this.participants = participants; // Initialize participants list
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public List<Participant> getParticipants() {
        return participants;
    }
    public List<String> getParticipantNames() {
        List<String> participantNames = new ArrayList<>();
        for (Participant participant : participants) {
            participantNames.add(participant.getName());
        }
        return participantNames;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Team team = (Team) obj;
        return Objects.equals(id, team.id);
    }


    @Override
    public String toString() {
        return name;
    }
}
