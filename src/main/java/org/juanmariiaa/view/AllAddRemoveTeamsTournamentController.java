package org.juanmariiaa.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.juanmariiaa.model.DAO.TeamDAO;
import org.juanmariiaa.model.DAO.TournamentDAO;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.domain.Tournament;
import org.juanmariiaa.model.domain.User;
import org.juanmariiaa.others.SingletonUserSession;
import org.juanmariiaa.utils.Utils;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for the view that allows adding or removing teams from a selected tournament.
 * This class manages the display and modification of teams associated with a tournament.
 */
public class AllAddRemoveTeamsTournamentController implements Initializable {

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
    private User currentUser;

    /**
     * It sets up the ListViews for adding and removing teams and initializes the current user session.
     *
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentUser = SingletonUserSession.getCurrentUser();
        teamsToAdd = FXCollections.observableArrayList();
        teamsToRemove = FXCollections.observableArrayList();

        teamListView.setItems(teamsToAdd);
        teamListView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);

        teamDeleteListView.setItems(teamsToRemove);
        teamDeleteListView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
    }

    /**
     * Displays the teams associated with the given tournament.
     * This method is called when the view is switched to manage teams for the selected tournament.
     *
     * @param tournament The tournament whose teams are to be displayed and managed.
     */
    public void show(Tournament tournament) {
        this.tournament = tournament;
        loadTeamsInTournament();
        loadTeamsNotInTournament();
    }

    /**
     * Loads and displays the teams that are currently associated with the tournament.
     */
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
    /**
     * Loads and displays the teams that are not currently associated with the tournament.
     */
    private void loadTeamsNotInTournament() {
        if (tournament != null) {
            try {
                List<Team> allTeams = new ArrayList<>(TeamDAO.build().findAll(currentUser.getId()));
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


    /**
     * Adds the selected teams to the tournament.
     * This method is called when the add button is clicked.
     */
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
        loadTeamsNotInTournament();
    }





    /**
     * Removes the selected teams from the tournament.
     * This method is called when the delete button is clicked.
     */
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