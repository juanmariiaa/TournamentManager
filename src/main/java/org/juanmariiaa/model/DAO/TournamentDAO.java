package org.juanmariiaa.model.DAO;

import org.juanmariiaa.model.connection.ConnectionMariaDB;
import org.juanmariiaa.model.domain.Tournament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TournamentDAO {

    private final static String FINDALL = "SELECT * FROM tournament";
    private final static String FINDBYID = "SELECT * FROM tournament WHERE id=?";
    private final static String INSERT = "INSERT INTO tournament (name, location, city, id_user) VALUES (?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE tournament SET name=?, location=?, city=? WHERE id=?";
    private final static String DELETE = "DELETE FROM tournament WHERE id=?";

    private Connection conn;

    public TournamentDAO(Connection conn) {
        this.conn = conn;
    }

    public TournamentDAO() {
        this.conn = ConnectionMariaDB.getConnection();
    }

    


}