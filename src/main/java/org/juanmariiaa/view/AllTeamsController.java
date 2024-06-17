package org.juanmariiaa.view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import org.juanmariiaa.model.DAO.ParticipantDAO;
import org.juanmariiaa.model.DAO.TeamDAO;
import org.juanmariiaa.model.domain.Participant;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.domain.User;
import org.juanmariiaa.others.SingletonUserSession;
import org.juanmariiaa.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for the All Teams view.
 * This class manages the display and editing of team information in a TableView,
 * as well as navigation to different views and delete of selected tournaments.
 */
public class AllTeamsController implements Initializable {

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
    TeamDAO teamDAO = new TeamDAO();
    private User currentUser;
    private ObservableList<Team> teams;



    public void initialize(URL location, ResourceBundle resources) {
        currentUser = SingletonUserSession.getCurrentUser();
        try {
            List<Team> teamsList = TeamDAO.build().findAll(currentUser.getId());
            for (Team team : teamsList) {
                List<Participant> participants = ParticipantDAO.build().findParticipantsByTeam(team.getId());
                team.setParticipants(participants);
            }
            this.teams = FXCollections.observableArrayList(teamsList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tableView.setItems(this.teams);
        tableView.setEditable(true);
        columnID.setCellValueFactory(team -> new SimpleIntegerProperty(team.getValue().getId()).asString());
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnName.setOnEditCommit(event -> {
            Team team = event.getRowValue();
            team.setName(event.getNewValue());
            teamDAO.update(team);
            tableView.refresh();
        });

        // City column
        columnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        columnCity.setCellFactory(TextFieldTableCell.forTableColumn());
        columnCity.setOnEditCommit(event -> {
            Team team = event.getRowValue();
            team.setCity(event.getNewValue());
            teamDAO.update(team);
            tableView.refresh();
        });

        // Institution column
        columnInstitution.setCellValueFactory(new PropertyValueFactory<>("institution"));
        columnInstitution.setCellFactory(TextFieldTableCell.forTableColumn());
        columnInstitution.setOnEditCommit(event -> {
            Team team = event.getRowValue();
            team.setInstitution(event.getNewValue());
            teamDAO.update(team);
            tableView.refresh();
        });


    }

    @FXML
    private void deleteSelected() {
        Team selectedT = tableView.getSelectionModel().getSelectedItem();

        if (selectedT != null) {
            try {
                tableView.getItems().remove(selectedT);
                teamDAO.delete(selectedT);
                Utils.showPopUp("DELETE", "Team deleted", "This team has been deleted.", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                Utils.showPopUp("Error", null, "Error while deleting team: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            Utils.showPopUp("Error", null, "Please select a team to delete.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btDelete() throws SQLException {
        deleteSelected();
    }
    @FXML
    private void switchToParticipant() throws IOException {
        App.setRoot("allParticipants");
    }
    @FXML
    private void switchToTournaments() throws IOException {
        App.setRoot("allTournaments");
    }
    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
    @FXML
    private void switchToFinder() throws IOException {
        App.setRoot("finder");
    }
    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }


    @FXML
    private void switchToShowParticipantsInSelectedTeam() throws IOException {
        Team selectedTeam = tableView.getSelectionModel().getSelectedItem();
        if (selectedTeam != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("allShowParticipantsInSelectedTeam.fxml"));
            Parent root = loader.load();
            AllShowParticipantsInSelectedTeamController controller = loader.getController();
            controller.show(selectedTeam);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } else {
            Utils.showPopUp("Error", null, "Please select a team first!", Alert.AlertType.ERROR);
        }
    }
}