package org.juanmariiaa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.juanmariiaa.model.DAO.TeamDAO;
import org.juanmariiaa.model.DAO.TournamentDAO;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.domain.Tournament;

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
    TournamentDAO tournamentDAO = new TournamentDAO();
    TeamDAO teamDAO = new TeamDAO();
    private ObservableList<Tournament> tournaments;


    @FXML
    private void initialize() throws SQLException {
        List<Team> teams = teamDAO.findAll();

        ObservableList<String> nameTeamList = FXCollections.observableArrayList();

        for (Team team : teams) {
            nameTeamList.add(team.getName());
        }
        lvTeams.setItems(nameTeamList);
        lvTeams.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void createTournament() throws SQLException {
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
            Team team = null;
            List<Team> teams = teamDAO.findByName(teamName);
            if (!teams.isEmpty()) {
                team = teams.get(0);
            }
            if (team != null) {
                selectedTeams.add(team);
            }
        }


        // Assuming the id is auto-incremental and doesn't need to be set manually
        Tournament tournament = new Tournament(0, name, location, city, date, selectedTeams);

        // Save Tournament to Database
        tournamentDAO.save(tournament);

        // Clear Fields
        tfName.clear();
        tfLocation.clear();
        tfCity.clear();
        dtDate.getEditor().clear();
        lvTeams.getSelectionModel().clearSelection();
    }



}
