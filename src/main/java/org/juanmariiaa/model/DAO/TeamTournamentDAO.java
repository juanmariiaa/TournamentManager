package org.juanmariiaa.model.DAO;

import org.juanmariiaa.model.domain.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamTournamentDAO {
    private final static String FIND_BY_TOURNAMENT_ID = "SELECT team.id, team.name, team.city, team.institution\n" +
            "FROM team\n" +
            "INNER JOIN participation ON team.id = participation.id_team\n" +
            "WHERE participation.id_tournament = ?;";


    private Connection conn;

    public TeamTournamentDAO(Connection conn) {
        this.conn = conn;
    }



    public List<Team> findTeamsByTournamentId(int tournamentId) throws SQLException {
        List<Team> result = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(FIND_BY_TOURNAMENT_ID)) {
            statement.setInt(1, tournamentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Team team = new Team();
                    team.setId(resultSet.getInt("id"));
                    team.setName(resultSet.getString("name"));
                    team.setCity(resultSet.getString("city"));
                    team.setInstitution(resultSet.getString("institution"));
                    result.add(team);
                }
            }
        }
        return result;
    }
}