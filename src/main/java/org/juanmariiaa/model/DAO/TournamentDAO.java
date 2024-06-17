package org.juanmariiaa.model.DAO;

import org.juanmariiaa.model.connection.ConnectionMariaDB;
import org.juanmariiaa.model.domain.*;
import org.juanmariiaa.model.enums.Gender;
import org.juanmariiaa.model.enums.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TournamentDAO {
    private final static String FIND_ALL = "SELECT * FROM tournament WHERE id_user=?";
    private final static String INSERT = "INSERT INTO tournament (name, location, city, date, id_user) VALUES (?, ?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE tournament SET name=?, location=?, city=?, date=? WHERE id=?";
    private final static String DELETE = "DELETE FROM tournament WHERE id=?";
    private final static String ADD_TEAM_TO_TOURNAMENT = "INSERT INTO participation (id_tournament ,id_team) VALUES (?, ?)";
    private final static String DELETE_TEAM_FROM_TOURNAMENT = "DELETE FROM participation WHERE id_tournament = ? AND id_team = ?";
    private final static String IS_TEAM_IN_TOURNAMENT = "SELECT COUNT(*) FROM participation WHERE id_team = ? AND id_tournament = ?";

    private Connection conn;
    private TeamTournamentDAO teamTournamentDAO;

    public TournamentDAO(Connection conn, TeamTournamentDAO teamTournamentDAO) {
        this.conn = conn;
        this.teamTournamentDAO = teamTournamentDAO;
    }

    public TournamentDAO() {
        this.conn = ConnectionMariaDB.getConnection();
        this.teamTournamentDAO = new TeamTournamentDAO(conn);
    }

    /**
     * Finds all tournaments associated with a given user.
     *
     * @param userId The ID of the user
     * @return A list of Tournament objects associated with the user
     * @throws SQLException if a database access error occurs
     */
    public List<Tournament> findAll(int userId) throws SQLException {
        List<Tournament> tournaments = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(FIND_ALL)) {
            preparedStatement.setInt(1, userId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    Tournament tournament = tournamentEager(resultSet);
                    tournaments.add(tournament);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tournaments;
    }

    /**
     * Saves a tournament to the database.
     *
     * @param user      The user associated with the tournament
     * @param tournament The tournament to be saved
     * @return The saved Tournament object with its ID set
     * @throws SQLException if a database access error occurs
     */
    public Tournament save(User user, Tournament tournament) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, tournament.getName());
            statement.setString(2, tournament.getLocation());
            statement.setString(3, tournament.getCity());
            statement.setString(4, String.valueOf(tournament.getDate()));
            statement.setInt(5, user.getId());
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    tournament.setId(rs.getInt(1));
                }
            }
        }
        return tournament;
    }

    /**
     * Updates a tournament in the database.
     *
     * @param tournament The tournament to be updated
     * @return The updated Tournament object
     * @throws SQLException if a database access error occurs
     */
    public Tournament update(Tournament tournament) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(UPDATE)) {
            statement.setString(1, tournament.getName());
            statement.setString(2, tournament.getLocation());
            statement.setString(3, tournament.getCity());
            statement.setDate(4, (Date) tournament.getDate());
            statement.setInt(5, tournament.getId());
            statement.executeUpdate();
        }
        return tournament;
    }

    /**
     * Deletes a tournament from the database.
     *
     * @param tournamentId The ID of the tournament to be deleted
     * @throws SQLException if a database access error occurs
     */
    public void delete(int tournamentId) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(DELETE)) {
            statement.setInt(1, tournamentId);
            statement.executeUpdate();
        }
    }

    /**
     * Finds teams associated with a specific tournament.
     *
     * @param tournamentId The ID of the tournament
     * @return A list of Team objects associated with the tournament
     * @throws SQLException if a database access error occurs
     */
    public List<Team> findTeamsByTournamentId(int tournamentId) throws SQLException {
        return teamTournamentDAO.findTeamsByTournamentId(tournamentId);
    }

    /**
     * Adds a team to a tournament.
     *
     * @param tournamentId The ID of the tournament
     * @param teamId       The ID of the team
     * @throws SQLException if a database access error occurs
     */
    public void addTeamToTournament(int tournamentId, int teamId) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(ADD_TEAM_TO_TOURNAMENT)) {
            statement.setInt(1, tournamentId);
            statement.setInt(2, teamId);
            statement.executeUpdate();
        }
    }

    /**
     * Removes a team from a tournament.
     *
     * @param teamId       The ID of the team
     * @param tournamentId The ID of the tournament
     * @throws SQLException if a database access error occurs
     */
    public void removeTeamFromTournament(int teamId, int tournamentId) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(DELETE_TEAM_FROM_TOURNAMENT)) {
            statement.setInt(1, tournamentId);
            statement.setInt(2, teamId);
            statement.executeUpdate();
        }
    }

    /**
     * Checks if a team is in a specific tournament.
     *
     * @param teamId       The ID of the team
     * @param tournamentId The ID of the tournament
     * @return true if the team is in the tournament, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean isTeamInTournament(int teamId, int tournamentId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(IS_TEAM_IN_TOURNAMENT)) {
            stmt.setInt(1, teamId);
            stmt.setInt(2, tournamentId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }


    private Tournament tournamentEager(ResultSet resultSet) throws SQLException {
        Tournament tournament = new Tournament();
        tournament.setId(resultSet.getInt("id"));
        tournament.setName(resultSet.getString("name"));
        tournament.setLocation(resultSet.getString("location"));
        tournament.setCity(resultSet.getString("city"));
        tournament.setDate(resultSet.getDate("date"));
        tournament.setTeams(findTeamsByTournamentId(tournament.getId()));
        return tournament;
    }



    /**
     * Builds and returns a new instance of TournamentDAO.
     *
     * @return A new instance of TournamentDAO
     */
    public static TournamentDAO build(){
        return new TournamentDAO();
    }

}
