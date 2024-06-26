package org.juanmariiaa.model.DAO;

import org.juanmariiaa.model.connection.ConnectionMariaDB;
import org.juanmariiaa.model.domain.Tournament;
import org.juanmariiaa.model.domain.User;
import org.juanmariiaa.utils.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final static String FINDBYDNI = "SELECT * FROM user WHERE dni=?";
    private final static String FINDBYUSERNAME = "SELECT * FROM user WHERE username=?";
    private final static String INSERT = "INSERT INTO user (dni, name, surname, username, password) VALUES (?, ?, ?, ?, ?)";
    private final static String LOGIN_VALIDATION = "SELECT id FROM user WHERE username = ? AND password = ?";



    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public UserDAO() {
        this.conn = ConnectionMariaDB.getConnection();
    }


    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to find
     * @return The User object with the given username, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public User findByUsername(String username) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(FINDBYUSERNAME)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setDni(resultSet.getString("dni"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setUser(resultSet.getString("username"));
                return user;
            }
        }
        return null;
    }

    /**
     * Finds a user by their DNI.
     *
     * @param dni The DNI of the user to find
     * @return The User object with the given DNI, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public User findByDni(String dni) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(FINDBYDNI)) {
            statement.setString(1, dni);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setDni(resultSet.getString("dni"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setUser(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        }
        return null;
    }

    /**
     * Saves a user to the database.
     *
     * @param user The user to be saved
     * @return The saved User object with its DNI set
     * @throws SQLException if a database access error occurs
     */
    public User save(User user) throws SQLException {
        if (user == null) {
            return null;
        }
        try (PreparedStatement statement = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getDni());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());
            statement.setString(4, user.getUser());
            statement.setString(5, user.getPassword());
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setDni(rs.getString(1));
                }
            }
        }
        return user;
    }

    /**
     * Validates user login.
     *
     * @param username The username of the user
     * @param password The password of the user
     * @return The ID of the user if the login is valid, null otherwise
     * @throws SQLException if a database access error occurs
     */
    public String validateLogin(String username, String password) throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement(LOGIN_VALIDATION)) {
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                return rs.getString("id");
            }
        }
        return null;
    }

}
