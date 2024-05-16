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
import javafx.util.StringConverter;
import org.juanmariiaa.model.DAO.TournamentDAO;
import org.juanmariiaa.model.domain.Tournament;
import org.juanmariiaa.model.domain.User;
import org.juanmariiaa.others.SingletonUserSession;
import org.juanmariiaa.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for the All Tournaments view.
 * This class manages the display and editing of tournament information in a TableView,
 * as well as navigation to different views and delete of selected tournaments.
 */
public class AllTournamentsController implements Initializable {

    @FXML
    private TableView<Tournament> tableView;

    @FXML
    private TableColumn<Tournament,String> columnID;
    @FXML
    private TableColumn<Tournament,String> columnName;
    @FXML
    private TableColumn<Tournament,String> columnLocation;
    @FXML
    private TableColumn<Tournament,String> columnCity;
    @FXML
    private TableColumn<Tournament, Date> columnDate;
    private ObservableList<Tournament> tournaments;
    TournamentDAO tournamentDAO = new TournamentDAO();
    private User currentUser;



    /**
     * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
     * Also display all the tournaments related to the currentUser. This method also allows the user to update the fields
     * associated with the tournament he wants to update.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    public void initialize(URL location, ResourceBundle resources) {
        currentUser = SingletonUserSession.getCurrentUser();
        try {
            List<Tournament> tournaments = TournamentDAO.build().findAll(currentUser.getId());
            this.tournaments = FXCollections.observableArrayList(tournaments);
        } catch (SQLException e) {
            Utils.showPopUp("Error", null, "Error while fetching tournaments: " + e.getMessage(), Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }

        tableView.setItems(this.tournaments);
        tableView.setEditable(true);
        columnID.setCellValueFactory(tournament -> new SimpleIntegerProperty(tournament.getValue().getId()).asString());

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnName.setOnEditCommit(event -> {
            Tournament tournament = event.getRowValue();
            tournament.setName(event.getNewValue());
            try {
                tournamentDAO.update(tournament);
            } catch (SQLException e) {
                Utils.showPopUp("Error", null, "Error while updating tournament: " + e.getMessage(), Alert.AlertType.ERROR);
            }
            tableView.refresh();
        });

        columnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        columnLocation.setCellFactory(TextFieldTableCell.forTableColumn());
        columnLocation.setOnEditCommit(event -> {
            Tournament tournament = event.getRowValue();
            tournament.setLocation(event.getNewValue());
            try {
                tournamentDAO.update(tournament);
            } catch (SQLException e) {
                Utils.showPopUp("Error", null, "Error while updating tournament: " + e.getMessage(), Alert.AlertType.ERROR);
            }
            tableView.refresh();
        });


        columnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        columnCity.setCellFactory(TextFieldTableCell.forTableColumn());
        columnCity.setOnEditCommit(event -> {
            Tournament tournament = event.getRowValue();
            tournament.setCity(event.getNewValue());
            try {
                tournamentDAO.update(tournament);
            } catch (SQLException e) {
                Utils.showPopUp("Error", null, "Error while updating tournament: " + e.getMessage(), Alert.AlertType.ERROR);
            }
            tableView.refresh();
        });


        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnDate.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Date>() {
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            @Override
            public String toString(Date object) {
                if (object != null) {
                    return dateFormatter.format(object.toLocalDate());
                } else {
                    return "";
                }
            }

            @Override
            public Date fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return Date.valueOf(LocalDate.parse(string, dateFormatter));
                } else {
                    return null;
                }
            }
        }));
        columnDate.setOnEditCommit(event -> {
            Tournament tournament = event.getRowValue();
            tournament.setDate(Date.valueOf(event.getNewValue().toLocalDate()));
            try {
                tournamentDAO.update(tournament);
            } catch (SQLException e) {
                Utils.showPopUp("Error", null, "Write a correct format of date (dd-MM-yyyy): " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
            tableView.refresh();
        });
    }

    @FXML
    private void deleteSelected() {
        Tournament selectedT = tableView.getSelectionModel().getSelectedItem();

        if (selectedT != null) {
            try {
                tableView.getItems().remove(selectedT);
                tournamentDAO.delete(selectedT.getId());
                Utils.showPopUp("DELETE", "Tournament deleted", "This tournament has been deleted.", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                Utils.showPopUp("Error", null, "Error while deleting tournament: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            Utils.showPopUp("Error", null, "Please select a tournament to delete.", Alert.AlertType.ERROR);
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
    private void switchToTeam() throws IOException {
        App.setRoot("allTeams");
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
    /**
     * Switches the scene to the view that shows teams in the selected tournament.
     *
     * @throws IOException if there is an error loading the view.
     */
    @FXML
    private void switchToShowTeamsInSelectedTournament() throws IOException {
        Tournament selectedTournament = tableView.getSelectionModel().getSelectedItem();
        if (selectedTournament != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("allShowTeamsInSelectedTournament.fxml"));
            Parent root = loader.load();
            AllShowTeamsInSelectedTournamentController controller = loader.getController();
            controller.show(selectedTournament);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } else {
            Utils.showPopUp("Error", null, "Please select a tournament first!", Alert.AlertType.ERROR);
        }
    }
    /**
     * Switches the scene to the view that allows adding or removing teams from the selected tournament.
     *
     * @throws IOException if there is an error loading the view.
     */
    @FXML
    private void switchToAddRemoveTeamToTournament() throws IOException {
        Tournament selectedTournament = tableView.getSelectionModel().getSelectedItem();
        if (selectedTournament != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("allAddRemoveTeamFromTournament.fxml"));
            Parent root = loader.load();
            AllAddRemoveTeamsTournamentController controller = loader.getController();
            controller.show(selectedTournament);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } else {
            Utils.showPopUp("Error", null, "Please select a tournament first!", Alert.AlertType.ERROR);
        }
    }
}