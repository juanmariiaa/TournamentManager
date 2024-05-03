package org.juanmariiaa.model.DAO;

import org.juanmariiaa.model.connection.ConnectionMariaDB;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.domain.Tournament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TournamentDAO {
    private final static String FIND_ALL = "SELECT * FROM tournament LIMIT 15";
    private final static String FIND_BY_ID = "SELECT * FROM tournament WHERE id=?";
    private final static String FIND_BY_NAME = "SELECT * FROM tournament WHERE name=?";
    private final static String INSERT = "INSERT INTO tournament (id, name, location, city) VALUES (?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE tournament SET name=?, location=?, city=? WHERE id=?";
    private final static String DELETE = "DELETE FROM tournament WHERE id=?";

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

    public Tournament findById(int id) throws SQLException {
        Tournament tournament = null;
        try (PreparedStatement statement = conn.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    tournament = new Tournament();
                    tournament.setId(resultSet.getInt("id"));
                    tournament.setName(resultSet.getString("name"));
                    tournament.setLocation(resultSet.getString("location"));
                    tournament.setCity(resultSet.getString("city"));
                    tournament.setDate(resultSet.getDate("date"));
                    tournament.setTeams(findTeamsByTournamentId(tournament.getId())); // Load teams using findTeamsByTournamentId
                }
            }
        }
        return tournament;
    }

    public List<Tournament> findByName(String name) throws SQLException {
        List<Tournament> tournaments = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(FIND_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Tournament tournament = new Tournament();
                    tournament.setId(resultSet.getInt("id"));
                    tournament.setName(resultSet.getString("name"));
                    tournament.setLocation(resultSet.getString("location"));
                    tournament.setCity(resultSet.getString("city"));
                    tournament.setDate(resultSet.getDate("date"));
                    tournament.setTeams(findTeamsByTournamentId(tournament.getId())); // Load teams using findTeamsByTournamentId
                    tournaments.add(tournament);
                }
            }
        }
        return tournaments;
    }

    public Tournament save(Tournament tournament) throws SQLException {
        if (tournament == null) {
            return null;
        }
        if (tournament.getId() == 0) {
            // Insert new tournament
            try (PreparedStatement statement = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, tournament.getName());
                statement.setString(2, tournament.getLocation());
                statement.setString(3, tournament.getCity());
                statement.setString(4, String.valueOf(tournament.getDate()));
                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        tournament.setId(generatedKeys.getInt(1)); // Set the generated id back to the entity
                    }
                }
            }
        } else {
            // Update existing tournament
            try (PreparedStatement statement = conn.prepareStatement(UPDATE)) {
                statement.setString(1, tournament.getName());
                statement.setString(2, tournament.getLocation());
                statement.setString(3, tournament.getCity());
                statement.setString(4, String.valueOf(tournament.getDate()));
                statement.setInt(5, tournament.getId());
                statement.executeUpdate();
            }
        }

        // Update team associations after saving the tournament (optional)
        if (tournament.getTeams() != null) {
            for (Team team : tournament.getTeams()) {
                teamTournamentDAO.addTeamToTournament(tournament.getId(), team.getId());
            }
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

    public void addTeams(Tournament tournament, List<Team> teams) throws SQLException {
        if (tournament != null && teams != null) {
            for (Team team : teams) {
                teamTournamentDAO.addTeamToTournament(tournament.getId(), team.getId());
            }
        }
    }
    public static TournamentDAO build(){
        return new TournamentDAO();
    }

}
