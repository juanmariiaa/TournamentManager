package org.juanmariiaa.model.DAO;

import org.juanmariiaa.model.connection.ConnectionMariaDB;
import org.juanmariiaa.model.domain.Participant;
import org.juanmariiaa.model.domain.Team;
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
    /**
     * Retrieves a list of all participants associated with a specific user.
     *
     * @param userId The ID of the user whose participants are to be retrieved.
     * @return A list of Participant objects associated with the given user.
     */
    public List<Participant> findAll(int userId) {
        List<Participant> result = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(FINDALL)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Participant participant = participantEager(resultSet);
                    result.add(participant);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Saves a new participant to the database.
     *
     * @param user        The user associated with the participant, the one who has created it.
     * @param participant The Participant object to be saved.
     * @return The saved Participant object.
     * @throws RuntimeException If a database access error occurs.
     */
    public Participant save(User user, Participant participant) {
        try (PreparedStatement statement = conn.prepareStatement(INSERT)) {
            statement.setString(1, participant.getDni());
            statement.setString(2, participant.getRole().toString());
            statement.setString(3, participant.getGender().toString());
            statement.setString(4, participant.getName());
            statement.setString(5, participant.getSurname());
            statement.setInt(6, participant.getAge());
            statement.setInt(7, participant.getTeam().getId());
            statement.setInt(8, user.getId());
            statement.executeUpdate();

            return participant;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Updates an existing participant in the database.
     *
     * @param participant The Participant object to be updated.
     * @return The updated Participant object.
     */
    public Participant update(Participant participant) {
        try (PreparedStatement statement = conn.prepareStatement(UPDATE)) {
            statement.setString(1, participant.getRole().toString());
            statement.setString(2, participant.getGender().toString());
            statement.setString(3, participant.getName());
            statement.setString(4, participant.getSurname());
            statement.setInt(5, participant.getAge());
            statement.setInt(6, participant.getTeam().getId());
            statement.setString(7, participant.getDni());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participant;
    }

    /**
     * Retrieves a list of participants belonging to a specific team.
     *
     * @param teamId The ID of the team whose participants are to be retrieved.
     * @return A list of Participant objects belonging to the given team.
     */
    public List<Participant> findParticipantsByTeam(int teamId) {
        List<Participant> participants = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(FIND_PARTICIPANT_BY_TEAM)) {
            statement.setInt(1, teamId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Participant participant = participantEager(resultSet);
                    participants.add(participant);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participants;
    }

    /**
     * Deletes a participant from the database.
     *
     * @param dni The DNI of the participant to be deleted.
     */
    public void delete(String dni) {
        try (PreparedStatement statement = conn.prepareStatement(DELETE)) {
            statement.setString(1, dni);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructs a Participant object with eagerly fetched attributes from the ResultSet.
     *
     * @param resultSet The ResultSet containing participant data.
     * @return A Participant object with eagerly fetched attributes.
     * @throws SQLException If a database access error occurs.
     */
    private Participant participantEager(ResultSet resultSet) throws SQLException {
        Participant participant = new Participant();
        participant.setDni(resultSet.getString("dni"));
        participant.setRole(Role.valueOf(resultSet.getString("role")));
        participant.setGender(Gender.valueOf(resultSet.getString("gender")));
        participant.setName(resultSet.getString("first_name"));
        participant.setSurname(resultSet.getString("surname"));
        participant.setAge(resultSet.getInt("age"));
        participant.setTeam(TeamDAO.build().findById(resultSet.getInt("id_team")));
        return participant;
    }

    /**
     * Helper method to construct a ParticipantDAO object.
     *
     * @return A new instance of ParticipantDAO.
     */
    public static ParticipantDAO build() {
        return new ParticipantDAO();
    }
}
