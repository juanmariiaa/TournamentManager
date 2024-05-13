package org.juanmariiaa.model.DAO;

import org.juanmariiaa.model.connection.ConnectionMariaDB;
import org.juanmariiaa.model.domain.Participant;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.domain.Tournament;
import org.juanmariiaa.model.domain.User;
import org.juanmariiaa.model.enums.Gender;
import org.juanmariiaa.model.enums.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantDAO {
    private final static String FINDALL = "SELECT * FROM participant WHERE id_user=?";
    private final static String INSERT = "INSERT INTO participant (dni, role, gender, first_name, surname, age, id_team, id_user) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE participant SET role=?, gender=?, first_name=?, surname=?, age=?, id_team=? WHERE dni=?";
    private final static String DELETE = "DELETE FROM participant WHERE dni=?";
    private final static String FIND_PARTICIPANT_BY_TEAM = "SELECT * FROM participant WHERE id_team = ?";

    private Connection conn;

    public ParticipantDAO(Connection conn) {
        this.conn = conn;
    }

    public ParticipantDAO() {
        this.conn = ConnectionMariaDB.getConnection();
    }


    public List<Participant> findAll(int userId) {
        List<Participant> result = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(FINDALL)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Participant participant = new Participant();
                    participant.setDni(resultSet.getString("dni"));
                    participant.setRole(Role.valueOf(resultSet.getString("role")));
                    participant.setGender(Gender.valueOf(resultSet.getString("gender")));
                    participant.setName(resultSet.getString("first_name"));
                    participant.setSurname(resultSet.getString("surname"));
                    participant.setAge(resultSet.getInt("age"));
                    result.add(participant);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public boolean save(User user, Participant participant) {
        try (PreparedStatement statement = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, participant.getDni());
            statement.setString(2, participant.getRole().toString());
            statement.setString(3, participant.getGender().toString());
            statement.setString(4, participant.getName());
            statement.setString(5, participant.getSurname());
            statement.setInt(6, participant.getAge());
            statement.setInt(7, participant.getTeam().getId());
            statement.setInt(8, user.getId());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public boolean update(Participant participant) {
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


    public List<Participant> findParticipantsByTeam(int teamId) {
        List<Participant> participants = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(FIND_PARTICIPANT_BY_TEAM)) {
            statement.setInt(1, teamId);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participants;
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

