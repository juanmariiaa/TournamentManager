package org.juanmariiaa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.juanmariiaa.model.DAO.TeamDAO;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.domain.Tournament;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowTeamsInSelectedTournamentController implements Initializable {

    @FXML
    private Label tournamentNameLabel;

    @FXML
    private ListView<String> teamsListView;

    private Tournament selectedTournament;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize
    }

    public void initData(Tournament tournament) {
        this.selectedTournament = tournament;
        tournamentNameLabel.setText(tournament.getName());
        loadTeams();
    }

    private void loadTeams() {
        List<Team> teams = TeamDAO.build().findTeamsByTournament(selectedTournament.getId());
        ObservableList<String> teamNames = FXCollections.observableArrayList();
        for (Team team : teams) {
            teamNames.add(team.getName());
        }
        teamsListView.setItems(teamNames);
    }
}
