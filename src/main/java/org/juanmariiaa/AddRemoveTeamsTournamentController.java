package org.juanmariiaa;

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

    private ObservableList<String> teamNames;
    private Tournament tournament;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        teamNames = FXCollections.observableArrayList();
        teamListView.setItems(teamNames);
        teamListView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
    }

    public void initData(Tournament tournament) {
        this.tournament = tournament;
        loadTeamLists();
    }

    private void loadTeamLists() {
        if (tournament != null) {
            try {
                List<Team> allTeams = TeamDAO.build().findAll();
                List<Team> teamsInTournament = TournamentDAO.build().findTeamsByTournamentId(tournament.getId());

                for (Team team : allTeams) {
                    if (!teamsInTournament.contains(team)) {
                        teamNames.add(team.getName());
                    }
                }
            } catch (SQLException e) {
                Utils.showPopUp("Error", null, "Error while fetching teams: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }

            // Populate teamDeleteListView with teams that are in the selected tournament
            ObservableList<String> teamsInTournamentNames = FXCollections.observableArrayList();
            teamDeleteListView.setItems(teamsInTournamentNames);
            teamDeleteListView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);

            try {
                List<Team> teamsInTournament = TournamentDAO.build().findTeamsByTournamentId(tournament.getId());
                for (Team team : teamsInTournament) {
                    teamsInTournamentNames.add(team.getName());
                }
            } catch (SQLException e) {
                Utils.showPopUp("Error", null, "Error while fetching teams: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            Utils.showPopUp("Error", null, "Tournament is null!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void addTeamsToTournament() {
        ObservableList<String> selectedItems = teamListView.getSelectionModel().getSelectedItems();
        if (!selectedItems.isEmpty() && tournament != null) {
            for (String selectedTeamName : selectedItems) {
                try {
                    Team selectedTeam = (Team) TeamDAO.build().findOneByName(selectedTeamName);
                    if (selectedTeam != null) {
                        if (TournamentDAO.build().isTeamInTournament(selectedTeam.getId(), tournament.getId())) {
                            Utils.showPopUp("Warning", null, "Selected team '" + selectedTeam.getName() + "' is already in the tournament!", Alert.AlertType.WARNING);
                        } else {
                            TournamentDAO.build().addTeamToTournament(selectedTeam.getId(), tournament.getId());
                        }
                    } else {
                        Utils.showPopUp("Error", null, "Selected team not found!", Alert.AlertType.ERROR);
                    }
                } catch (SQLException e) {
                    Utils.showPopUp("Error", null, "Error while adding team to tournament: " + e.getMessage(), Alert.AlertType.ERROR);
                    e.printStackTrace();
                }
            }
            Utils.showPopUp("Success", null, "Teams added to tournament successfully.", Alert.AlertType.INFORMATION);
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        } else {
            Utils.showPopUp("Error", null, "Please select at least one team!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void removeTeamFromTournament() {
        ObservableList<String> selectedItems = teamDeleteListView.getSelectionModel().getSelectedItems();
        if (!selectedItems.isEmpty() && tournament != null) {
            for (String selectedTeamName : selectedItems) {
                try {
                    Team selectedTeam = (Team) TeamDAO.build().findOneByName(selectedTeamName);
                    if (selectedTeam != null) {
                        if (!TournamentDAO.build().isTeamInTournament(selectedTeam.getId(), tournament.getId())) {
                            Utils.showPopUp("Warning", null, "Selected team '" + selectedTeam.getName() + "' is not in the tournament!", Alert.AlertType.WARNING);
                        } else {
                            TournamentDAO.build().removeTeamFromTournament(selectedTeam.getId(), tournament.getId());
                        }
                    } else {
                        Utils.showPopUp("Error", null, "Selected team not found!", Alert.AlertType.ERROR);
                    }
                } catch (SQLException e) {
                    Utils.showPopUp("Error", null, "Error while removing team from tournament: " + e.getMessage(), Alert.AlertType.ERROR);
                    e.printStackTrace();
                }
            }
            Utils.showPopUp("Success", null, "Teams removed from tournament successfully.", Alert.AlertType.INFORMATION);
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        } else {
            Utils.showPopUp("Error", null, "Please select at least one team!", Alert.AlertType.ERROR);
        }
    }
}