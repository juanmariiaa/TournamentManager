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

    public static ParticipantDAO getInstance() {
        if (instance == null) {
            instance = new ParticipantDAO();
        }
        return instance;
    }

    public List<Participant> findAll() throws SQLException {
        List<Participant> result = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Participant participant = new Participant();
                    participant.setDni(res.getString("dni"));
                    participant.setRole(Role.valueOf(res.getString("role")));
                    participant.setGender(Gender.valueOf(res.getString("gender")));
                    participant.setName(res.getString("first_name"));
                    participant.setSurname(res.getString("surname"));
                    participant.setAge(res.getInt("age"));
                    result.add(participant);
                }
            }
        }
        return result;
    }

    public Participant findById(String dni) throws SQLException {
        Participant participant = null;
        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
            pst.setString(1, dni);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    participant = new Participant();
                    participant.setDni(res.getString("dni"));
                    participant.setRole(Role.valueOf(res.getString("role")));
                    participant.setGender(Gender.valueOf(res.getString("gender")));
                    participant.setName(res.getString("first_name"));
                    participant.setSurname(res.getString("surname"));
                    participant.setAge(res.getInt("age"));
                }
            }
        }
        return participant;
    }


    public Participant save(Participant entity) throws SQLException {
        if (entity == null || entity.getDni() == null) {
            return null;
        }
        if (findById(entity.getDni()) == null) {
            try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                pst.setString(1, entity.getDni());
                pst.setString(2, entity.getRole().toString());
                pst.setString(3, entity.getGender().toString());
                pst.setString(4, entity.getName());
                pst.setString(5, entity.getSurname());
                pst.setInt(6, entity.getAge());
                int teamId = TeamDAO.findTeamIdByParticipantDni(entity.getDni());
                pst.setInt(7, teamId);
                pst.executeUpdate();
            }
        } else {
            try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                pst.setString(1, entity.getRole().toString());
                pst.setString(2, entity.getGender().toString());
                pst.setString(3, entity.getName());
                pst.setString(4, entity.getSurname());
                pst.setInt(5, entity.getAge());
                int teamId = TeamDAO.findTeamIdByParticipantDni(entity.getDni());
                pst.setInt(6, teamId);
                pst.setString(7, entity.getDni());
                pst.executeUpdate();
            }
        }
        return entity;
    }

    public void delete(String dni) throws SQLException {
        if (dni != null) {
            try (PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
                pst.setString(1, dni);
                pst.executeUpdate();
            }
        }
    }
}

