package org.juanmariiaa;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.juanmariiaa.model.DAO.ParticipantDAO;
import org.juanmariiaa.model.DAO.TeamDAO;
import org.juanmariiaa.model.DAO.TournamentDAO;
import org.juanmariiaa.model.domain.Participant;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.domain.Tournament;
import org.juanmariiaa.model.enums.Gender;
import org.juanmariiaa.model.enums.Role;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ParticipantController extends Controller implements Initializable {

    @FXML
    private TableView<Participant> tableView;

    @FXML
    private TableColumn<Participant,String> columnDNI;
    @FXML
    private TableColumn<Participant,String> columnName;
    @FXML
    private TableColumn<Participant,String> columnSurname;
    @FXML
    private TableColumn<Participant,String> columnAge;
    @FXML
    private TableColumn<Participant,Role> columnRole;
    @FXML
    private TableColumn<Participant, Gender> columnGender;
    @FXML
    private TableColumn<Participant,String> columnTeam;
    private ObservableList<Participant> participants;



    public void initialize(URL location, ResourceBundle resources) {
        List<Participant> teamsList = ParticipantDAO.build().findAll();
        this.participants = FXCollections.observableArrayList(teamsList);

        tableView.setItems(this.participants);
        tableView.setEditable(true);
        columnDNI.setCellValueFactory(participant -> new SimpleStringProperty(participant.getValue().getDni()));
        columnName.setCellValueFactory(participant -> new SimpleStringProperty(participant.getValue().getName()));
        columnSurname.setCellValueFactory(participant -> new SimpleStringProperty(participant.getValue().getSurname()));
        columnAge.setCellValueFactory(tournament -> new SimpleIntegerProperty(tournament.getValue().getAge()).asString());
        columnRole.setCellValueFactory(participant -> new SimpleObjectProperty<>(participant.getValue().getRole()));
        columnGender.setCellValueFactory(participant -> new SimpleObjectProperty<>(participant.getValue().getGender()));
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
    private void switchToCreateParticipant() throws IOException {
        App.setRoot("createParticipant");
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }
}
