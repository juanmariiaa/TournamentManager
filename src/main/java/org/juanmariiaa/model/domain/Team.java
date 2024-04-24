package org.juanmariiaa.model.domain;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private int id;
    private String name;
    private String city;
    private String institution;
    private List<Participant> participants = null;

    public Team() {
        this.id = 0;
        this.name = "";
        this.city = "";
        this.institution = "";
        this.participants = null;
    }

    public Team(int id, String name, String city, String institution, List<Participant> participants) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.institution = institution;
        this.participants = participants;
    }
}
