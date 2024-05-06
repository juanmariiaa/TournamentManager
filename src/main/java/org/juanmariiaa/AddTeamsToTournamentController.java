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

public class AddTeamsToTournamentController implements Initializable {

    @FXML
    private ListView<String> teamListView;

    @FXML
    private Button addButton;

    private ObservableList<String> teamNames;
    private Tournament tournament;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        teamNames = FXCollections.observableArrayList();
        teamListView.setItems(teamNames);
        teamListView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);

        try {
            List<Team> allTeams = TeamDAO.build().findAll();
            for (Team team : allTeams) {
                teamNames.add(team.getName());
            }
        } catch (SQLException e) {
            Utils.showPopUp("Error", null, "Error while fetching teams: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void initData(Tournament tournament) {
        this.tournament = tournament;
    }

    @FXML
    private void addTeamsToTournament() {
        ObservableList<String> selectedItems = teamListView.getSelectionModel().getSelectedItems();
        if (!selectedItems.isEmpty() && tournament != null) {
            for (String selectedTeamName : selectedItems) {
                try {
                    Team selectedTeam = (Team) TeamDAO.build().findOneByName(selectedTeamName);
                    if (selectedTeam != null) {
                        TournamentDAO.build().addTeamToTournament(selectedTeam.getId(), tournament.getId());
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
}