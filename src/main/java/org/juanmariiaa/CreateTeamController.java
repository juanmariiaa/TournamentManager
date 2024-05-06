package org.juanmariiaa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.juanmariiaa.model.DAO.ParticipantDAO;
import org.juanmariiaa.model.DAO.TeamDAO;
import org.juanmariiaa.model.DAO.TournamentDAO;
import org.juanmariiaa.model.domain.Participant;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.domain.Tournament;
import org.juanmariiaa.model.domain.User;
import org.juanmariiaa.others.SingletonUserSession;
import org.juanmariiaa.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreateTeamController {
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfCity;
    @FXML
    private TextField tfInstitution;
    @FXML
    private ListView<String> lvParticipants;
    @FXML
    private Hyperlink linkTournaments;
    @FXML
    private Hyperlink linkTeams;
    @FXML
    private Hyperlink linkParticipants;
    @FXML
    private Hyperlink linkHome;
    @FXML
    private Button btnCreate;

    private TeamDAO teamDAO = new TeamDAO();

    private ObservableList<Team> teams;


    @FXML
    private void initialize() throws SQLException {


    }

    @FXML
    private void createTeam() throws IOException {
        try {
            String name = tfName.getText();
            String city = tfCity.getText();
            String institution = tfInstitution.getText();

            if (name.isEmpty() || city.isEmpty() || institution.isEmpty()) {
                Utils.showPopUp("Error", null, "Please fill in all the fields.", Alert.AlertType.ERROR);
                return;
            }

            Team newTeam = new Team();
            newTeam.setName(name);
            newTeam.setCity(city);
            newTeam.setInstitution(institution);

            teamDAO.save(newTeam);
            switchToTeam();
        } catch (SQLException e) {
            Utils.showPopUp("Error", null, "An error occurred while creating the team.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void switchToTournament() throws IOException {
        App.setRoot("tournament");
    }

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void switchToTeam() throws IOException {
        App.setRoot("team");
    }

    @FXML
    private void switchToParticipant() throws IOException {
        App.setRoot("participant");
    }
}
