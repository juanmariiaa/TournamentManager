package org.juanmariiaa.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import org.juanmariiaa.model.DAO.TournamentDAO;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.domain.Tournament;
import org.juanmariiaa.utils.Utils;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ShowTournamentsInSelectedTeamController implements Initializable {

    @FXML
    private ListView<String> tournamentListView;

    private Team selectedTeam;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize your controller
    }

    public void initData(Team selectedTeam) {
        this.selectedTeam = selectedTeam;
        loadTournamentsInSelectedTeam();
    }

    private void loadTournamentsInSelectedTeam() {
        if (selectedTeam != null) {
            try {
                List<Tournament> tournaments = TournamentDAO.build().findTournamentsByTeam(selectedTeam.getId());
                if (!tournaments.isEmpty()) {
                    for (Tournament tournament : tournaments) {
                        tournamentListView.getItems().add(tournament.getName());
                    }
                } else {
                    Utils.showPopUp("Information", null, "No tournaments found for the selected team.", Alert.AlertType.INFORMATION);
                }
            } catch (SQLException e) {
                Utils.showPopUp("Error", null, "Error while fetching tournaments for the selected team: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            Utils.showPopUp("Error", null, "Selected team is null!", Alert.AlertType.ERROR);
        }
    }
}
