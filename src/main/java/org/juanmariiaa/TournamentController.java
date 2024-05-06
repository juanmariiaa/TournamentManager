package org.juanmariiaa;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;
import org.juanmariiaa.model.DAO.TeamDAO;
import org.juanmariiaa.model.DAO.TournamentDAO;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.domain.Tournament;
import org.juanmariiaa.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class TournamentController extends Controller implements Initializable {

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


    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<Tournament> tournaments = TournamentDAO.build().findAll();
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
                e.printStackTrace();
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
                e.printStackTrace();
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
                e.printStackTrace();
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
        Tournament selectedT = (Tournament) tableView.getSelectionModel().getSelectedItem();

        if (selectedT != null) {
            tableView.getItems().remove(selectedT);

            try {
                tournamentDAO.delete(selectedT.getId());
                Utils.showPopUp("DELETE", "Team deleted", "This team has deleted.", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
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
    private void switchToCreateTournament() throws IOException {
        App.setRoot("createTournament");
    }
    @FXML
    private void switchToShowTeamsInSelectedTournament() throws IOException {
        Tournament selectedTournament = tableView.getSelectionModel().getSelectedItem();
        if (selectedTournament != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("showTeamsInSelectedTournament.fxml"));
            Parent root = loader.load();
            ShowTeamsInSelectedTournamentController controller = loader.getController();
            controller.initData(selectedTournament);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } else {
            Utils.showPopUp("Error", null, "Please select a tournament first!", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }
}
