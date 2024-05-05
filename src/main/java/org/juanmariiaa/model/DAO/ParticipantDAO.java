package org.juanmariiaa.model.DAO;

import org.juanmariiaa.model.connection.ConnectionMariaDB;
import org.juanmariiaa.model.domain.Participant;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.enums.Gender;
import org.juanmariiaa.model.enums.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantDAO {

    private final static String FINDALL = "SELECT * FROM participant LIMIT 15";
    private final static String FINDBYID = "SELECT * FROM participant WHERE dni=?";
    private final static String FINDBYNAME = "SELECT * FROM participant WHERE first_name LIKE ? OR surname LIKE ?";
    private final static String INSERT = "INSERT INTO participant (dni, role, gender, first_name, surname, age, id_team) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE participant SET role=?, gender=?, first_name=?, surname=?, age=?, id_team=? WHERE dni=?";
    private final static String DELETE = "DELETE FROM participant WHERE dni=?";


    private Connection conn;

    public ParticipantDAO(Connection conn) {
        this.conn = conn;
    }

    public ParticipantDAO() {
        this.conn = ConnectionMariaDB.getConnection();
    }


    public List<Participant> findAll() {
        List<Participant> result = new ArrayList<>();
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(FINDALL)) {
            while (resultSet.next()) {
                Participant participant = new Participant();
                participant.setDni(resultSet.getString("dni"));
                participant.setRole(Role.valueOf(resultSet.getString("role")));
                participant.setGender(Gender.valueOf(resultSet.getString("gender")));
                participant.setName(resultSet.getString("first_name"));
                participant.setSurname(resultSet.getString("surname"));
                participant.setAge(resultSet.getInt("age"));
                Team team = new TeamDAO(conn).findById(resultSet.getInt("id_team"));
                participant.setTeam(team);
                result.add(participant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean insert(Participant participant) {
        try (PreparedStatement statement = conn.prepareStatement(INSERT)) {
            statement.setString(1, participant.getDni());
            statement.setString(2, participant.getRole().toString());
            statement.setString(3, participant.getGender().toString());
            statement.setString(4, participant.getName());
            statement.setString(5, participant.getSurname());
            statement.setInt(6, participant.getAge());
            statement.setInt(7, participant.getTeam().getId());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Participant findById(String dni) {
        try (PreparedStatement statement = conn.prepareStatement(FINDBYID)) {
            statement.setString(1, dni);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Participant participant = new Participant();
                participant.setDni(resultSet.getString("dni"));
                participant.setRole(Role.valueOf(resultSet.getString("role")));
                participant.setGender(Gender.valueOf(resultSet.getString("gender")));
                participant.setName(resultSet.getString("first_name"));
                participant.setSurname(resultSet.getString("surname"));
                participant.setAge(resultSet.getInt("age"));
                Team team = new TeamDAO(conn).findById(resultSet.getInt("id_team"));
                participant.setTeam(team);
                return participant;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Participant> findByName(String name) throws SQLException {
        List<Participant> participants = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(FINDBYNAME)) {
            statement.setString(1, "%" + name + "%"); // Wildcard search for first and last name
            statement.setString(2, "%" + name + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Participant participant = new Participant();
                    participant.setDni(resultSet.getString("dni"));
                    participant.setRole(Role.valueOf(resultSet.getString("role")));
                    participant.setGender(Gender.valueOf(resultSet.getString("gender")));
                    participant.setName(resultSet.getString("first_name"));
                    participant.setSurname(resultSet.getString("surname"));
                    participant.setAge(resultSet.getInt("age"));
                    Team team = new TeamDAO(conn).findById(resultSet.getInt("id_team"));
                    participant.setTeam(team);
                    participants.add(participant);
                }
            }
        }
        return participants;
    }


    public boolean save(Participant participant) {
        try (PreparedStatement statement = conn.prepareStatement(UPDATE)) {
            statement.setString(1, participant.getRole().toString());
            statement.setString(2, participant.getGender().toString());
            statement.setString(3, participant.getName());
            statement.setString(4, participant.getSurname());
            statement.setInt(5, participant.getAge());
            statement.setInt(6, participant.getTeam().getId());
            statement.setString(7, participant.getDni());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String dni) {
        try (PreparedStatement statement = conn.prepareStatement(DELETE)) {
            statement.setString(1, dni);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ParticipantDAO build(){
        return new ParticipantDAO();
    }
}

