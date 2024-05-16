package org.juanmariiaa.model.DAO;

import org.juanmariiaa.model.connection.ConnectionMariaDB;
import org.juanmariiaa.model.domain.Participant;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.domain.Tournament;
import org.juanmariiaa.model.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO {

    private final static String FINDALL = "SELECT * FROM team WHERE id_user=?";
    private final static String FINDBYID = "SELECT * FROM team WHERE id=?";
    private final static String FINDBYNAME = "SELECT * FROM team WHERE name LIKE ?";
    private final static String INSERT = "INSERT INTO team (id, name, city, institution, id_user) VALUES (?, ?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE team SET name=?, city=?, institution=? WHERE id=?";
    private final static String DELETE = "DELETE FROM team WHERE id=?";
    private final static String FIND_PARTICIPANTS_BY_TEAM = "SELECT * FROM participant WHERE id_team = ?";

    private final static String FIND_TEAMS_BY_TOURNAMENT = "SELECT t.id, t.name, t.city, t.institution " +
            "FROM team t " +
            "JOIN participation p ON t.id = p.id_team " +
            "WHERE p.id_tournament = ?";




    TournamentDAO tournamentDAO = new TournamentDAO();

    private Connection conn;

    public TeamDAO(Connection conn) {
        this.conn = conn;
    }

    public TeamDAO() {
        this.conn = ConnectionMariaDB.getConnection();
    }
    /**
     * Finds all teams associated with a given user.
     *
     * @param userId The ID of the user
     * @return A list of Team objects associated with the user
     * @throws SQLException if a database access error occurs
     */
    public List<Team> findAll(int userId) throws SQLException {
        List<Team> result = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            pst.setInt(1, userId);
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
    /**
     * Finds a team by its ID.
     *
     * @param id The ID of the team to find
     * @return The Team object with the given ID, or null if not found
     * @throws SQLException if a database access error occurs
     */
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
    /**
     * Finds teams by name using partial match.
     *
     * @param name The name (or partial name) of the team to find
     * @return A list of Team objects matching the given name
     * @throws SQLException if a database access error occurs
     */
    public List<Team> findByName(String name) throws SQLException {
        List<Team> teams = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(FINDBYNAME)) {
            statement.setString(1, "%" + name + "%");
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


    /**
     * Finds a single team by name using partial match.
     * If multiple teams match the given name, a warning is printed.
     *
     * @param name The name (or partial name) of the team to find
     * @return The first Team object matching the given name, or null if not found
     * @throws SQLException if a database access error occurs
     */
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




    /**
     * Saves a team to the database.
     *
     * @param user The user associated with the team
     * @param team The team to be saved
     * @param tournamentId The ID of the tournament the team is associated with
     * @return The saved Team object with its ID set
     * @throws SQLException if a database access error occurs
     */
    public Team save(User user, Team team, int tournamentId) throws SQLException {
        if (team.getId() == 0) {
            try (PreparedStatement pst = this.conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setNull(1, Types.INTEGER);
                pst.setString(2, team.getName());
                pst.setString(3, team.getCity());
                pst.setString(4, team.getInstitution());
                pst.setInt(5, user.getId());
                pst.executeUpdate();

                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        team.setId(rs.getInt(1));
                    }
                }
            }
        }
        return team;
    }

    /**
     * Updates a team in the database.
     *
     * @param team The team to be updated
     * @return The updated Team object
     */
    public Team update(Team team) {
        try (PreparedStatement statement = conn.prepareStatement(UPDATE)) {
            statement.setString(1, team.getName());
            statement.setString(2, team.getCity());
            statement.setString(3, team.getInstitution());
            statement.setInt(4, team.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return team;
    }




    /**
     * Deletes a team from the database.
     *
     * @param entity The team to be deleted
     * @throws SQLException if a database access error occurs
     */
    public void delete(Team entity) throws SQLException {
        if (entity != null && entity.getId() != 0) {
            try (PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
                pst.setInt(1, entity.getId());
                pst.executeUpdate();
            }
        }
    }

    /**
     * Finds participants belonging to a specific team.
     *
     * @param teamId The ID of the team
     * @return A list of Participant objects belonging to the team
     * @throws SQLException if a database access error occurs
     */
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



    /**
     * Finds teams associated with a specific tournament.
     *
     * @param tournamentId The ID of the tournament
     * @return A list of Team objects associated with the tournament
     */
    public List<Team> findTeamsByTournament(int tournamentId) {
        List<Team> teams = new ArrayList<>();
        try (PreparedStatement statement = this.conn.prepareStatement(FIND_TEAMS_BY_TOURNAMENT)) {
            statement.setInt(1, tournamentId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Team team = new Team();
                    team.setId(rs.getInt("id"));
                    team.setName(rs.getString("name"));
                    team.setCity(rs.getString("city"));
                    team.setInstitution(rs.getString("institution"));
                    teams.add(team);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }


    /**
     * Builds and returns a new instance of TeamDAO.
     *
     * @return A new instance of TeamDAO
     */
    public static TeamDAO build(){
        return new TeamDAO();
    }


}