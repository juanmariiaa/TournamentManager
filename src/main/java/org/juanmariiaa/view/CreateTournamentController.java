package org.juanmariiaa.view;

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
import org.juanmariiaa.utils.Utils;
import org.juanmariiaa.view.App;

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
    private Button btnCreate;

    private TournamentDAO tournamentDAO = new TournamentDAO();
    private TeamDAO teamDAO = new TeamDAO();
    private ObservableList<Tournament> tournaments;

    private User currentUser;

    @FXML
    private void initialize() throws SQLException {
        currentUser = SingletonUserSession.getCurrentUser();


        List<Team> teams = teamDAO.findAll(currentUser.getId());

        ObservableList<String> nameTeamList = FXCollections.observableArrayList();

        for (Team team : teams) {
            nameTeamList.add(team.getName());
        }
        lvTeams.setItems(nameTeamList);
        lvTeams.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void createTournament() throws IOException {
        try {
            String name = tfName.getText();
            String location = tfLocation.getText();
            String city = tfCity.getText();
            LocalDate localDate = dtDate.getValue();

            if (localDate == null) {
                Utils.showPopUp("Error", null, "Please select a valid date.", Alert.AlertType.ERROR);
                return;
            }

            java.sql.Date date = java.sql.Date.valueOf(localDate);

            ObservableList<String> selectedTeamNames = lvTeams.getSelectionModel().getSelectedItems();
            List<Team> selectedTeams = new ArrayList<>();
            for (String teamName : selectedTeamNames) {
                List<Team> teams = teamDAO.findByName(teamName);
                if (!teams.isEmpty()) {
                    selectedTeams.add(teams.get(0));
                }
            }

            Tournament tournament = new Tournament(name, location, city, date, selectedTeams);

            try {
                tournament = tournamentDAO.save(currentUser, tournament);

                for (Team team : selectedTeams) {
                    tournamentDAO.addTeamToTournament(tournament.getId(), team.getId());
                }

                tfName.clear();
                tfLocation.clear();
                tfCity.clear();
                dtDate.getEditor().clear();
                lvTeams.getSelectionModel().clearSelection();
                switchToMyTournament();

                Utils.showPopUp("Tournament Created", null, "Tournament has been created successfully.", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                Utils.showPopUp("Error", null, "Error while creating tournament: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        } catch (SQLException e) {
            Utils.showPopUp("Error", null, "An error occurred while creating the tournament.", Alert.AlertType.ERROR);
        }
    }




    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
    @FXML
    private void switchToMyTournament() throws IOException {
        App.setRoot("myTournaments");
    }

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }

}
