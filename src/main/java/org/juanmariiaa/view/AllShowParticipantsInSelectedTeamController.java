package org.juanmariiaa.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.juanmariiaa.model.DAO.ParticipantDAO;
import org.juanmariiaa.model.domain.Participant;
import org.juanmariiaa.model.domain.Team;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for the view that shows participants in the selected team.
 * This class manages the display of team name.
 */
public class AllShowParticipantsInSelectedTeamController implements Initializable {

    @FXML
    private Label teamNameLabel;

    @FXML
    private ListView<String> participantsListView;

    private Team selectedTeam;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void show(Team team) {
        this.selectedTeam = team;
        teamNameLabel.setText(team.getName());
        List<Participant> participants = ParticipantDAO.build().findParticipantsByTeam(selectedTeam.getId());
        ObservableList<String> participantNames = FXCollections.observableArrayList();
        for (Participant participant : participants) {
            participantNames.add(participant.getName());
        }
        participantsListView.setItems(participantNames);
    }


}