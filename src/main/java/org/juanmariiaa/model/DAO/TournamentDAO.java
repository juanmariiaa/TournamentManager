package org.juanmariiaa.model.DAO;

import org.juanmariiaa.model.connection.ConnectionMariaDB;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.domain.Tournament;
import org.juanmariiaa.model.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TournamentDAO {
    private final static String FIND_ALL = "SELECT * FROM tournament";
    private final static String INSERT = "INSERT INTO tournament (name, location, city, date, id_user) VALUES (?, ?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE tournament SET name=?, location=?, city=? WHERE id=?";
    private final static String DELETE = "DELETE FROM tournament WHERE id=?";
    private final static String ADD_TEAM_TO_TOURNAMENT = "INSERT INTO participation (id_tournament,id_team ) VALUES (?, ?)";
    private final static String DELETE_TEAM_FROM_TOURNAMENT = "DELETE FROM participation WHERE id_tournament = ? AND id_team = ?";
    private final static String IS_TEAM_IN_TOURNAMENT = "SELECT COUNT(*) FROM participation WHERE id_team = ? AND id_tournament = ?";
    private final static String FIND_TOURNAMENTS_BY_TEAM = "SELECT t.id, t.name, t.location, t.city, t.date FROM tournament t, participation p WHERE t.id = p.id_tournament AND p.id_team = ?";



    private Connection conn;
    private TeamTournamentDAO teamTournamentDAO; // Inject TeamTournamentDAO instance

    public TournamentDAO(Connection conn, TeamTournamentDAO teamTournamentDAO) {
        this.conn = conn;
        this.teamTournamentDAO = teamTournamentDAO; // Inject in constructor
    }

    public TournamentDAO() {
        this.conn = ConnectionMariaDB.getConnection();
        this.teamTournamentDAO = new TeamTournamentDAO(conn); // Create instance if not injected
    }

    public  List<Tournament> findAll() throws SQLException {
        List<Tournament> tournaments = new ArrayList<>();
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
            while (resultSet.next()) {
                Tournament tournament = new Tournament();
                tournament.setId(resultSet.getInt("id"));
                tournament.setName(resultSet.getString("name"));
                tournament.setLocation(resultSet.getString("location"));
                tournament.setCity(resultSet.getString("city"));
                tournament.setDate(resultSet.getDate("date"));
                // Assuming teams are loaded separately using the new method
                tournament.setTeams(findTeamsByTournamentId(tournament.getId())); // Load teams using findTeamsByTournamentId
                tournaments.add(tournament);
            }
        }
        return tournaments;
    }


    public Tournament save(User user, Tournament tournament) throws SQLException {
        // Implement logic to create a tournament associated with the provided user
        try (PreparedStatement statement = conn.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, tournament.getName());
            statement.setString(2, tournament.getLocation());
            statement.setString(3, tournament.getCity());
            statement.setString(4, String.valueOf(tournament.getDate())); // Assuming getDate() returns a String
            statement.setInt(5, user.getId()); // Assuming User has a getter for its ID
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    tournament.setId(rs.getInt(1)); // Set the generated tournament ID
                }
            }
        }
        return tournament;
    }

    public Tournament update(Tournament tournament) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(UPDATE)) {
            statement.setString(1, tournament.getName());
            statement.setString(2, tournament.getLocation());
            statement.setString(3, tournament.getCity());
            statement.setString(4, String.valueOf(tournament.getDate())); // Assuming getDate() returns a String
            statement.setInt(5, tournament.getId());
            statement.executeUpdate();
        }
        return tournament;
    }



    public void delete(int tournamentId) throws SQLException {
        // 1. Delete tournament itself (assuming no foreign key constraints)
        try (PreparedStatement statement = conn.prepareStatement(DELETE)) {
            statement.setInt(1, tournamentId);
            statement.executeUpdate();
        }
        // 2. (Optional) Remove team associations (if needed)
        teamTournamentDAO.removeTeamFromAllTournaments(tournamentId); // Assuming this method exists in TeamTournamentDAO
    }

    public List<Team> findTeamsByTournamentId(int tournamentId) throws SQLException {
        return teamTournamentDAO.findTeamsByTournamentId(tournamentId);
    }

    public void addTeamToTournament(int tournamentId, int teamId) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(ADD_TEAM_TO_TOURNAMENT)) {
            statement.setInt(1, tournamentId);
            statement.setInt(2, teamId);
            statement.executeUpdate();
        }
    }

    public void removeTeamFromTournament(int teamId, int tournamentId) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(DELETE_TEAM_FROM_TOURNAMENT)) {
            statement.setInt(1, tournamentId);
            statement.setInt(2, teamId);
            statement.executeUpdate();
        }
    }



    public boolean isTeamInTournament(int teamId, int tournamentId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(IS_TEAM_IN_TOURNAMENT)) {
            stmt.setInt(1, teamId);
            stmt.setInt(2, tournamentId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    public List<Tournament> findTournamentsByTeam(int teamId) throws SQLException {
        List<Tournament> tournaments = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(FIND_TOURNAMENTS_BY_TEAM)) {
            statement.setInt(1, teamId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Tournament tournament = new Tournament();
                    tournament.setId(resultSet.getInt("id"));
                    tournament.setName(resultSet.getString("name"));
                    tournament.setLocation(resultSet.getString("location"));
                    tournament.setCity(resultSet.getString("city"));
                    tournament.setDate(resultSet.getDate("date"));
                    tournaments.add(tournament);
                }
            }
        }
        return tournaments;
    }


    public static TournamentDAO build(){
        return new TournamentDAO();
    }

}
