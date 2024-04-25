package org.juanmariiaa.model.DAO;

import org.juanmariiaa.model.connection.ConnectionMariaDB;
import org.juanmariiaa.model.domain.Tournament;
import org.juanmariiaa.model.domain.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final static String FINDALL = "SELECT * FROM user";
    private final static String FINDBYDNI = "SELECT * FROM user WHERE dni=?";
    private final static String FINDBYUSERNAME = "SELECT * FROM user WHERE user=?";
    private final static String INSERT = "INSERT INTO user (dni, name, surname, user, password) VALUES (?, ?, ?, ?, ?)";
    private final static String FIND_USER_BY_USERNAME = "SELECT * FROM tournament WHERE user_id=?";


    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public UserDAO() {
        this.conn = ConnectionMariaDB.getConnection();
    }

    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(FINDALL)) {
            while (resultSet.next()) {
                User user = new User();
                user.setDni(resultSet.getString("dni"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setUser(resultSet.getString("user"));
                user.setPassword(resultSet.getString("password"));
                users.add(user);
            }
        }
        return users;
    }


    public User findByUsername(String username) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(FINDBYUSERNAME)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setDni(resultSet.getString("dni"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setUser(resultSet.getString("user"));
                user.setPassword(passwordHashingFunction(username)); // Assuming password hashing
                return user;
            }
        }
        return null;
    }

    public User save(User user) throws SQLException {
        if (user == null) {
            return null;
        }
        try (PreparedStatement statement = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getDni());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());
            statement.setString(4, user.getUser());
            statement.setString(5, passwordHashingFunction(user.getPassword())); // Assuming password hashing
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setDni(rs.getString(1)); // Set the generated id back to the user object (if applicable)
                }
            }
        }
        return user;
    }

    // Add a method to create a tournament for a user (assuming many-to-one relationship)
    public Tournament createTournament(User user, Tournament tournament) throws SQLException {
        // Implement logic to create a tournament associated with the provided user
        try (PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO tournament (name, location, description, user_id) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, tournament.getName());
            statement.setString(2, tournament.getLocation());
            statement.setString(3, user.getDni()); // Assuming User has a getter for its ID
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    tournament.setId(rs.getInt(1)); // Set the generated tournament ID
                }
            }
        }
        return tournament;
    }

    // Retrieve all tournaments created by a user (assuming many-to-one relationship)
    public List<Tournament> findTournamentsByUser(User user) throws SQLException {
        List<Tournament> tournaments = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(FIND_USER_BY_USERNAME)) {
            statement.setString(1, user.getUser());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Tournament tournament = new Tournament();
                tournament.setId(resultSet.getInt("id"));
                tournament.setName(resultSet.getString("name"));
                tournament.setLocation(resultSet.getString("location"));
                tournaments.add(tournament);
            }
        }
        return tournaments;
    }

    // Replace with your actual password hashing function (e.g., using a secure hashing algorithm)
    private String passwordHashingFunction(String password) {
        // **Warning:** This is a simplified example for demonstration purposes only.
        // It's not recommended for production use due to security vulnerabilities.

        // Apply a hashing algorithm (MD5 is not the most secure, consider SHA-256 or higher)
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            byte[] hashedBytes = messageDigest.digest();

            // Convert the byte array to a hexadecimal string (common representation)
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating password hash", e);
        }
    }
}
