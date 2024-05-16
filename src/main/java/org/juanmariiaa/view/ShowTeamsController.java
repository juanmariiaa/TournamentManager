package org.juanmariiaa.view;

import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.juanmariiaa.model.DAO.TeamDAO;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.domain.Tournament;
import org.juanmariiaa.model.domain.User;
import org.juanmariiaa.others.SingletonUserSession;
import org.juanmariiaa.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class that display all the Teams associated with the selectedTournament.
 */
public class ShowTeamsController implements Initializable {

    @FXML
    private TableView<Team> tableView;
    @FXML
    private TableColumn<Team, String> nameColumn;
    @FXML
    private TableColumn<Team, String> cityColumn;
    @FXML
    private TableColumn<Team, String> institutionColumn;

    private User currentUser;
    private ObservableList<Team> teams;
    private final TeamDAO teamDAO = new TeamDAO();
    private Tournament selectedTournament;

    /**
     * Initializes the table view with the current user's data.
     *
     */
    public void initialize(URL location, ResourceBundle resources) {
        currentUser = SingletonUserSession.getCurrentUser();
        tableView.setEditable(true);
        nameColumn.setCellValueFactory(team -> new SimpleStringProperty(team.getValue().getName()));
        cityColumn.setCellValueFactory(team -> new SimpleStringProperty(team.getValue().getCity()));
        institutionColumn.setCellValueFactory(team -> new SimpleStringProperty(team.getValue().getInstitution()));
    }

    /**
     * Deletes the selected team from the table and the database.
     */
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


    /**
     * Loads the teams associated with the given tournament.
     *
     * @param tournament The tournament for which the teams are to be loaded.
     */
    public void loadTeams(Tournament tournament) {
        this.selectedTournament = tournament;
        List<Team> teams = teamDAO.findTeamsByTournament(tournament.getId());
        this.teams = FXCollections.observableArrayList(teams);
        tableView.setItems(this.teams);
    }


    @FXML
    public void switchToSelectTeam() {
        Team selectedTeam = tableView.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("selectedTeam.fxml"));
            Parent root = loader.load();

            SelectedTeamController controller = loader.getController();
            controller.initialize(selectedTeam);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Select Team");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void refresh() {
        if (selectedTournament != null) {
            teams.clear();
            teams.addAll(teamDAO.findTeamsByTournament(selectedTournament.getId()));
        }
    }
    public void closeWindow() {
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
    }
}