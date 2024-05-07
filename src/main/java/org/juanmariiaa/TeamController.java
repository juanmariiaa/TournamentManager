package org.juanmariiaa;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import org.juanmariiaa.model.DAO.TeamDAO;
import org.juanmariiaa.model.domain.Participant;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

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
    TeamDAO teamDAO = new TeamDAO();


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
        columnID.setCellValueFactory(team -> new SimpleIntegerProperty(team.getValue().getId()).asString());
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnName.setOnEditCommit(event -> {
            Team team = event.getRowValue();
            team.setName(event.getNewValue());
            try {
                teamDAO.save(team);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            tableView.refresh();
        });

        // City column
        columnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        columnCity.setCellFactory(TextFieldTableCell.forTableColumn());
        columnCity.setOnEditCommit(event -> {
            Team team = event.getRowValue();
            team.setCity(event.getNewValue());
            try {
                teamDAO.save(team);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            tableView.refresh();
        });

        // Institution column
        columnInstitution.setCellValueFactory(new PropertyValueFactory<>("institution"));
        columnInstitution.setCellFactory(TextFieldTableCell.forTableColumn());
        columnInstitution.setOnEditCommit(event -> {
            Team team = event.getRowValue();
            team.setInstitution(event.getNewValue());
            try {
                teamDAO.save(team);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            tableView.refresh();
        });


    }

    @FXML
    private void deleteSelected() {
        Team selectedT = (Team) tableView.getSelectionModel().getSelectedItem();

        if (selectedT != null) {
            tableView.getItems().remove(selectedT);

            try {
                teamDAO.delete(selectedT);
                Utils.showPopUp("DELETE", "Team deleted", "This team has deleted.", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                Utils.showPopUp("Error", null, "Error while deleting team: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void btDelete() throws SQLException {
        deleteSelected();
    }

    @FXML
    private void switchToTournament() throws IOException {
        App.setRoot("tournament");
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

    @FXML
    private void switchToShowParticipantsInSelectedTeam() throws IOException {
        Team selectedTeam = tableView.getSelectionModel().getSelectedItem();
        if (selectedTeam != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("showParticipantsInSelectedTeam.fxml"));
            Parent root = loader.load();
            ShowParticipantsInSelectedTeam controller = loader.getController();
            controller.initData(selectedTeam);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } else {
            Utils.showPopUp("Error", null, "Please select a team first!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void switchToShowTournamentsInSelectedTeam() throws IOException {
        Team selectedTeam = tableView.getSelectionModel().getSelectedItem();
        if (selectedTeam != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("showTournamentsInSelectedTeam.fxml"));
            Parent root = loader.load();
            ShowTournamentsInSelectedTeamController controller = loader.getController();
            controller.initData(selectedTeam);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } else {
            Utils.showPopUp("Error", null, "Please select a team first!", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }
}
