package org.juanmariiaa.model.DAO;

import org.juanmariiaa.model.connection.ConnectionMariaDB;
import org.juanmariiaa.model.domain.Participant;
import org.juanmariiaa.model.domain.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO {

    private final static String FINDALL = "SELECT * FROM team LIMIT 15";
    private final static String FINDBYID = "SELECT * FROM team WHERE id=?";
    private final static String FINDBYNAME = "SELECT * FROM team WHERE name LIKE ?";
    private final static String INSERT = "INSERT INTO team (id, name, city, institution) VALUES (?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE team SET name=?, city=?, institution=? WHERE id=?";
    private final static String DELETE = "DELETE FROM team WHERE id=?";
    private final static String FIND_PARTICIPANTS_BY_TEAM = "SELECT * FROM participant WHERE id_team = ?";


    private Connection conn;

    public TeamDAO(Connection conn) {
        this.conn = conn;
    }

    public TeamDAO() {
        this.conn = ConnectionMariaDB.getConnection();
    }

    public List<Team> findAll() throws SQLException {
        List<Team> result = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Team team = new Team();
                    team.setId(res.getInt("id"));
                    team.setName(res.getString("name"));
                    team.setCity(res.getString("city"));
                    team.setInstitution(res.getString("institution"));
                    result.add(team);
                }
            }
        }
        return result;
    }

    public Team findById(int id) throws SQLException {
        Team team = null;
        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    team = new Team();
                    team.setId(res.getInt("id"));
                    team.setName(res.getString("name"));
                    team.setCity(res.getString("city"));
                    team.setInstitution(res.getString("institution"));
                }
            }
        }
        return team;
    }

    public List<Team> findByName(String name) throws SQLException {
        List<Team> teams = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(FINDBYNAME)) {
            statement.setString(1, "%" + name + "%"); // Add wildcards for partial name search
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Team team = new Team();
                    team.setId(resultSet.getInt("id"));
                    team.setName(resultSet.getString("name"));
                    team.setCity(resultSet.getString("city"));
                    team.setInstitution(resultSet.getString("institution"));
                    teams.add(team);
                }
            }
        }
        return teams;
    }

    public Team findOneByName(String name) throws SQLException {
        Team team = null;
        try (PreparedStatement statement = conn.prepareStatement(FINDBYNAME)) {
            statement.setString(1, "%" + name + "%"); // Add wildcards for partial name search
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    team = new Team();
                    team.setId(resultSet.getInt("id"));
                    team.setName(resultSet.getString("name"));
                    team.setCity(resultSet.getString("city"));
                    team.setInstitution(resultSet.getString("institution"));

                    // Check if there are more results (shouldn't be with wildcards)
                    if (resultSet.next()) {
                        System.out.println("Warning: Multiple teams found with name: " + name);
                    }
                }
            }
        }
        return team;
    }


    public Team save(Team entity) throws SQLException {
        if (entity == null) {
            return null;
        }
        if (entity.getId() == 0) {
            // Insert new team
            try (PreparedStatement pst = this.conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setNull(1, Types.INTEGER); // id is auto-generated, set as null
                pst.setString(2, entity.getName());
                pst.setString(3, entity.getCity());
                pst.setString(4, entity.getInstitution());
                pst.executeUpdate();

                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        entity.setId(rs.getInt(1)); // Set the generated id back to the entity
                    }
                }
            }
        } else {
            // Update existing team
            try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                pst.setString(1, entity.getName());
                pst.setString(2, entity.getCity());
                pst.setString(3, entity.getInstitution());
                pst.setInt(4, entity.getId());
                pst.executeUpdate();
            }
        }
        return entity;
    }


    public void delete(Team entity) throws SQLException {
        if (entity != null && entity.getId() != 0) {
            try (PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
                pst.setInt(1, entity.getId());
                pst.executeUpdate();
            }
        }
    }

    public List<Participant> findParticipantsByTeam(int teamId) throws SQLException {
        List<Participant> participants = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FIND_PARTICIPANTS_BY_TEAM)) {
            pst.setInt(1, teamId);
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Participant participant = new Participant();
                    participant.setDni(res.getString("dni"));
                    participant.setName(res.getString("first_name"));
                    participant.setSurname(res.getString("surname"));
                    participant.setAge(res.getInt("age"));
                    participant.setTeam(findById(res.getInt("id_team")));
                    participants.add(participant);
                }
            }
        }
        return participants;
    }


    public static TeamDAO build(){
        return new TeamDAO();
    }


}