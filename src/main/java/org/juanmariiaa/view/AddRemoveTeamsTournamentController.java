package org.juanmariiaa.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.juanmariiaa.model.DAO.TeamDAO;
import org.juanmariiaa.model.DAO.TournamentDAO;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.domain.Tournament;
import org.juanmariiaa.utils.Utils;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddRemoveTeamsTournamentController implements Initializable {

    @FXML
    private ListView<String> teamListView;
    @FXML
    private ListView<String> teamDeleteListView;

    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;

    private ObservableList<String> teamsToAdd;
    private ObservableList<String> teamsToRemove;
    private Tournament tournament;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        teamsToAdd = FXCollections.observableArrayList();
        teamsToRemove = FXCollections.observableArrayList();

        teamListView.setItems(teamsToAdd);
        teamListView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);

        teamDeleteListView.setItems(teamsToRemove);
        teamDeleteListView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
    }

    public void initData(Tournament tournament) {
        this.tournament = tournament;
        loadTeamsInTournament();
        loadTeamsNotInTournament();
    }
    private void loadTeamsInTournament() {
        if (tournament != null) {
            List<Team> teamsInTournament = TeamDAO.build().findTeamsByTournament(tournament.getId());
            ObservableList<String> teamsInTournamentNames = FXCollections.observableArrayList();
            for (Team team : teamsInTournament) {
                teamsInTournamentNames.add(team.getName());
            }
            teamDeleteListView.setItems(teamsInTournamentNames);
        } else {
            Utils.showPopUp("Error", null, "Tournament is null!", Alert.AlertType.ERROR);
        }
    }
    private void loadTeamsNotInTournament() {
        if (tournament != null) {
            try {
                List<Team> allTeams = new ArrayList<>(TeamDAO.build().findAll());
                List<Team> teamsInTournament = TeamDAO.build().findTeamsByTournament(tournament.getId());

                allTeams.removeAll(teamsInTournament);

                ObservableList<String> teamsNotInTournamentNames = FXCollections.observableArrayList();
                for (Team team : allTeams) {
                    teamsNotInTournamentNames.add(team.getName());
                }
                teamListView.setItems(teamsNotInTournamentNames);
            } catch (SQLException e) {
                Utils.showPopUp("Error", null, "Error while fetching teams not in tournament: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            Utils.showPopUp("Error", null, "Tournament is null!", Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void addTeamsToTournament() {
        ObservableList<String> selectedItems = teamListView.getSelectionModel().getSelectedItems();
        if (selectedItems.isEmpty() || tournament == null) {
            Utils.showPopUp("Error", null, "Please select at least one team and ensure a tournament is selected!", Alert.AlertType.ERROR);
            return;
        }
        for (String selectedTeamName : selectedItems) {
            try {
                Team selectedTeam = TeamDAO.build().findOneByName(selectedTeamName);
                if (selectedTeam == null) {
                    Utils.showPopUp("Error", null, "Selected team not found!", Alert.AlertType.ERROR);
                    return;
                }
                if (TournamentDAO.build().isTeamInTournament(selectedTeam.getId(), tournament.getId())) {
                    Utils.showPopUp("Warning", null, "Selected team '" + selectedTeam.getName() + "' is already in the tournament!", Alert.AlertType.WARNING);
                    continue;
                }
                // Insert a record into the participation table
                TournamentDAO.build().addTeamToTournament(tournament.getId(),selectedTeam.getId());
                teamsToAdd.remove(selectedTeamName);
                teamsToRemove.add(selectedTeamName);
            } catch (SQLException e) {
                Utils.showPopUp("Error", null, "Error while adding team to tournament: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
                return;
            }
        }
        loadTeamsInTournament();
        loadTeamsNotInTournament(); // Reload the list of teams not in the tournament
    }





    @FXML
    private void removeTeamFromTournament() {
        ObservableList<String> selectedItems = teamDeleteListView.getSelectionModel().getSelectedItems();
        if (selectedItems.isEmpty() || tournament == null) {
            Utils.showPopUp("Error", null, "Please select at least one team and ensure a tournament is selected!", Alert.AlertType.ERROR);
            return;
        }
        for (String selectedTeamName : selectedItems) {
            try {
                Team selectedTeam = TeamDAO.build().findOneByName(selectedTeamName);
                if (selectedTeam == null) {
                    Utils.showPopUp("Error", null, "Selected team not found!", Alert.AlertType.ERROR);
                    return;
                }
                if (!TournamentDAO.build().isTeamInTournament(selectedTeam.getId(), tournament.getId())) {
                    Utils.showPopUp("Warning", null, "Selected team '" + selectedTeam.getName() + "' is not in the tournament!", Alert.AlertType.WARNING);
                    continue;
                }
                TournamentDAO.build().removeTeamFromTournament(selectedTeam.getId(), tournament.getId());
                teamsToRemove.remove(selectedTeamName);
                teamsToAdd.add(selectedTeamName);
            } catch (SQLException e) {
                Utils.showPopUp("Error", null, "Error while removing team from tournament: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
                return;
            }
        }
        loadTeamsInTournament();
        loadTeamsNotInTournament();
    }


}
