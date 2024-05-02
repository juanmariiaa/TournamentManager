package org.juanmariiaa.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TeamParticipantDAO {

    private final static String INSERT = "INSERT INTO participation (id_team, id_tournament) VALUES (?, ?)";
    private final static String DELETE = "DELETE FROM participation WHERE id_team = ? AND id_tournament = ?";

    private Connection conn;

    public TeamParticipantDAO(Connection conn) {
        this.conn = conn;
    }

    public void addParticipantToTeam(int teamId, int participantId) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(INSERT)) {
            statement.setInt(1, teamId);
            statement.setInt(2, participantId);
            statement.executeUpdate();
        }
    }

    public void removeParticipantFromTeam(int teamId, int participantId) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(DELETE)) {
            statement.setInt(1, teamId);
            statement.setInt(2, participantId);
            statement.executeUpdate();
        }
    }
}