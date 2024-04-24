package org.juanmariiaa.model.DAO;

import org.juanmariiaa.model.connection.ConnectionMariaDB;
import org.juanmariiaa.model.domain.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO {

    private final static String FINDALL = "SELECT * FROM team LIMIT 15";
    private final static String FINDBYID = "SELECT * FROM team WHERE id=?";
    private final static String INSERT = "INSERT INTO team (id, name, city, institution) VALUES (?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE team SET name=?, city=?, institution=? WHERE id=?";
    private final static String DELETE = "DELETE FROM team WHERE id=?";
    private final static String FINDBYTEAM = "SELECT * FROM team WHERE id = ?";


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

    public Team save(Team entity) throws SQLException {
        if (entity == null) {
            return null;
        }
        if (entity.getId() == 0) {
            try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                pst.setInt(1, entity.getId());
                pst.setString(2, entity.getName());
                pst.setString(3, entity.getCity());
                pst.setString(4, entity.getInstitution());
                pst.executeUpdate();
            }
        } else {
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

    public Team getTeam(int id) {
        Team team = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYTEAM)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    team = new Team();
                    team.setId(rs.getInt("id"));
                    team.setName(rs.getString("name"));
                    team.setCity(rs.getString("city"));
                    team.setInstitution(rs.getString("institution"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return team;
    }


}