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

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
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

    private ParticipantDAO participantDAO = new ParticipantDAO();
    private ObservableList<Team> teams;


    @FXML
    private void initialize() throws SQLException {
        ObservableList<Participant> participants = FXCollections.observableArrayList(participantDAO.findAll());

        ObservableList<String> participantNames = FXCollections.observableArrayList();

        for (Participant participant : participants) {
            participantNames.add(participant.getName());
        }
        lvParticipants.setItems(participantNames);
        lvParticipants.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }

    @FXML
    private void createTeam() throws SQLException, IOException {
        String name = tfName.getText();
        String city = tfCity.getText();
        String institution = tfInstitution.getText();

        ObservableList<String> selectedParticipantNames = lvParticipants.getSelectionModel().getSelectedItems();
        // Convert List<String> to List<Participant>
        List<Participant> selectedTeams = new ArrayList<>();
        for (String participantName : selectedParticipantNames) {
            List<Participant> participants = participantDAO.findByName(participantName);
            if (!participants.isEmpty()) {
                selectedTeams.add(participants.get(0));
            }
        }

        Team newTeam = new Team();
        newTeam.setName(name);
        newTeam.setCity(city);
        newTeam.setInstitution(institution);
        newTeam.setParticipants(selectedTeams);

        teamDAO.save(newTeam);
        switchToTeam();


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
