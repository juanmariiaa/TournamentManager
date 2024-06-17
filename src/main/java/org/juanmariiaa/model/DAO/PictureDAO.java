package org.juanmariiaa.model.DAO;

import org.juanmariiaa.model.connection.ConnectionMariaDB;
import org.juanmariiaa.model.domain.Picture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PictureDAO {
    private final static String GET_PICTURES_BY_TOURNAMENT = "SELECT id, image_data FROM pictures WHERE tournament_id = ?";

    private static final String INSERT_PICTURE = "INSERT INTO pictures (image_data, tournament_id) VALUES (?, ?)";
    private static final String DELETE_PICTURE = "DELETE FROM pictures WHERE id = ?";

    private Connection conn;

    public PictureDAO(Connection conn) {
        this.conn = conn;
    }

    public PictureDAO() {
        this.conn = ConnectionMariaDB.getConnection();
    }

    public List<Picture> getPicturesByTournamentId(int tournamentId) throws SQLException {
        List<Picture> pictures = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(GET_PICTURES_BY_TOURNAMENT)) {
            stmt.setInt(1, tournamentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                byte[] imageData = rs.getBytes("image_data");
                Picture picture = new Picture(id, imageData);
                pictures.add(picture);
            }
        }
        return pictures;
    }
    public void addPicture(Picture picture, int tournamentId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_PICTURE)) {
            stmt.setBytes(1, picture.getImageData());
            stmt.setInt(2, tournamentId);
            stmt.executeUpdate();
        }
    }

    public void deletePicture(int pictureId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_PICTURE)) {
            stmt.setInt(1, pictureId);
            stmt.executeUpdate();
        }
    }

}
