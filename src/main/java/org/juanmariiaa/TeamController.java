package org.juanmariiaa;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.juanmariiaa.model.DAO.TeamDAO;
import org.juanmariiaa.model.DAO.TournamentDAO;
import org.juanmariiaa.model.domain.Participant;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.domain.Tournament;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TeamController extends Controller implements Initializable {

    @FXML
    private TableView<Team> tableView;

    @FXML
    private TableColumn<Team,String> columnID;
    @FXML
    private TableColumn<Team,String> columnName;
    @FXML
    private TableColumn<Team,String> columnCity;
    @FXML
    private TableColumn<Team,String> columnInstitution;
    @FXML
    private TableColumn<Team,String> columnParticipants;
    private ObservableList<Team> teams;



    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<Team> teamsList = TeamDAO.build().findAll();
            for (Team team : teamsList) {
                List<Participant> participants = TeamDAO.build().findParticipantsByTeam(team.getId());
                team.setParticipants(participants);
            }
            this.teams = FXCollections.observableArrayList(teamsList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tableView.setItems(this.teams);
        tableView.setEditable(true);
        columnID.setCellValueFactory(tournament -> new SimpleIntegerProperty(tournament.getValue().getId()).asString());
        columnName.setCellValueFactory(tournament -> new SimpleStringProperty(tournament.getValue().getName()));
        columnCity.setCellValueFactory(tournament -> new SimpleStringProperty(tournament.getValue().getCity()));
        columnInstitution.setCellValueFactory(tournament -> new SimpleStringProperty(tournament.getValue().getInstitution()));
        columnParticipants.setCellValueFactory(team -> {
            List<String> participantNames = team.getValue().getParticipants().stream()
                    .map(Participant::getName)
                    .collect(Collectors.toList());
            return new SimpleStringProperty(String.join(", ", participantNames));
        });
    }

    @FXML
    private void switchToTournament() throws IOException {
        App.setRoot("Tournament");
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
    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }
    @FXML
    private void switchToCreateTeam() throws IOException {
        App.setRoot("createTeam");
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }
}
