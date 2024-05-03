package org.juanmariiaa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.juanmariiaa.model.DAO.TeamDAO;
import org.juanmariiaa.model.DAO.TournamentDAO;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.domain.Tournament;
import org.juanmariiaa.model.domain.User;
import org.juanmariiaa.others.SingletonUserSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreateTournamentController {
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfLocation;
    @FXML
    private TextField tfCity;
    @FXML
    private DatePicker dtDate;
    @FXML
    private ListView<String> lvTeams;
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

    private TournamentDAO tournamentDAO = new TournamentDAO();
    private TeamDAO teamDAO = new TeamDAO();
    private ObservableList<Tournament> tournaments;

    private User currentUser;

    @FXML
    private void initialize() throws SQLException {
        List<Team> teams = teamDAO.findAll();

        ObservableList<String> nameTeamList = FXCollections.observableArrayList();

        for (Team team : teams) {
            nameTeamList.add(team.getName());
        }
        lvTeams.setItems(nameTeamList);
        lvTeams.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Set the current user
        currentUser = SingletonUserSession.getCurrentUser();
        if (currentUser == null) {
            // If currentUser is null, redirect to login or handle the situation accordingly
            System.err.println("No user logged in. Redirecting to login page...");
            // Example: Redirect to the login page
            // You might want to use JavaFX's Scene class to change the scene
            return;
        }
    }

    @FXML
    private void createTournament() throws SQLException, IOException {
        String name = tfName.getText();
        String location = tfLocation.getText();
        String city = tfCity.getText();
        LocalDate localDate = dtDate.getValue();
        // Convert LocalDate to java.sql.Date or java.util.Date as required
        java.sql.Date date = java.sql.Date.valueOf(localDate);

        ObservableList<String> selectedTeamNames = lvTeams.getSelectionModel().getSelectedItems();
        // Convert List<String> to List<Team>
        List<Team> selectedTeams = new ArrayList<>();
        for (String teamName : selectedTeamNames) {
            List<Team> teams = teamDAO.findByName(teamName);
            if (!teams.isEmpty()) {
                selectedTeams.add(teams.get(0));
            }
        }

        // Check if currentUser is not null before creating the tournament
        if (currentUser != null) {
            // Create the tournament
            Tournament tournament = new Tournament(name, location, city, date, selectedTeams);

            // Associate tournament with the current user
            tournament = tournamentDAO.createTournament(currentUser, tournament);

            // Clear Fields
            tfName.clear();
            tfLocation.clear();
            tfCity.clear();
            dtDate.getEditor().clear();
            lvTeams.getSelectionModel().clearSelection();
        } else {
            // Handle the case when currentUser is null
            // For example, show an error message
            System.err.println("Current user is null. Cannot create tournament.");
        }
        switchToTournament();
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
