package org.juanmariiaa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.juanmariiaa.model.DAO.ParticipantDAO;
import org.juanmariiaa.model.DAO.TeamDAO;
import org.juanmariiaa.model.domain.Participant;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.enums.Gender;
import org.juanmariiaa.model.enums.Role;
import java.io.IOException;
import java.sql.SQLException;


public class CreateParticipantController {
    @FXML
    private TextField tfDNI;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfSurname;
    @FXML
    private TextField tfAge;
    @FXML
    ComboBox<Role> cbRole = new ComboBox<>();
    @FXML
    ComboBox<Gender> cbGender = new ComboBox<>();
    @FXML
    private ComboBox<String> cbTeam = new ComboBox<>();
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

    private ParticipantDAO participantDAO = new ParticipantDAO();

    private TeamDAO teamDAO = new TeamDAO();


    @FXML
    private void initialize() throws SQLException {
        ObservableList<Team> teams = FXCollections.observableArrayList(teamDAO.findAll());

        ObservableList<String> teamNames = FXCollections.observableArrayList();

        // Populate cbRole with enum values
        cbRole.setItems(FXCollections.observableArrayList(Role.values()));

        // Populate cbGender with enum values
        cbGender.setItems(FXCollections.observableArrayList(Gender.values()));

        for (Team team : teams) {
            teamNames.add(team.getName());
        }
        cbTeam.setItems(teamNames);
    }

    @FXML
    private void createParticipant() throws SQLException, IOException {
        String DNI = tfDNI.getText();
        String name = tfName.getText();
        String surname = tfSurname.getText();
        int age = Integer.parseInt(tfAge.getText());
        Role role = cbRole.getValue();
        Gender gender = cbGender.getValue();
        String teamName = cbTeam.getValue();

        // Find the team based on the selected team name
        Team team = teamDAO.findOneByName(teamName);


        Participant newParticipant = new Participant();
        newParticipant.setDni(DNI);
        newParticipant.setName(name);
        newParticipant.setSurname(surname);
        newParticipant.setAge(age);
        newParticipant.setRole(role);
        newParticipant.setGender(gender);

        // Set the actual Team object to the Participant
        newParticipant.setTeam(team);

        participantDAO.insert(newParticipant);

        switchToParticipant();
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
