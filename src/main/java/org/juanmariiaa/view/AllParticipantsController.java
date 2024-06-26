package org.juanmariiaa.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.juanmariiaa.model.DAO.ParticipantDAO;
import org.juanmariiaa.model.DAO.TeamDAO;
import org.juanmariiaa.model.domain.Participant;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.domain.User;
import org.juanmariiaa.model.enums.Gender;
import org.juanmariiaa.model.enums.Role;
import org.juanmariiaa.others.SingletonUserSession;
import org.juanmariiaa.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for the All Participant view.
 * This class manages the display and editing of participation information in a TableView,
 * as well as navigation to different views and delete of selected tournaments.
 */
public class AllParticipantsController implements Initializable {

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
    private TableColumn<Participant, Role> columnRole;
    @FXML
    private TableColumn<Participant, Gender> columnGender;
    @FXML
    private TableColumn<Participant,String> columnTeam;
    private ObservableList<Participant> participants;
    ParticipantDAO participantDAO = new ParticipantDAO();
    private User currentUser;





    public void initialize(URL location, ResourceBundle resources) {
        currentUser = SingletonUserSession.getCurrentUser();
        List<Participant> participants = ParticipantDAO.build().findAll(currentUser.getId());
        this.participants = FXCollections.observableArrayList(participants);

        tableView.setItems(this.participants);
        tableView.setEditable(true);
        columnDNI.setCellValueFactory(new PropertyValueFactory<>("dni"));
        columnDNI.setCellFactory(TextFieldTableCell.forTableColumn());
        columnDNI.setOnEditCommit(p -> {
            Participant participant = p.getRowValue();
            participant.setDni(p.getNewValue());
            participantDAO.update(participant);
            tableView.refresh();
        });

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnName.setOnEditCommit(p -> {
            Participant participant = p.getRowValue();
            participant.setName(p.getNewValue());
            participantDAO.update(participant);
            tableView.refresh();
        });

        columnSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        columnSurname.setCellFactory(TextFieldTableCell.forTableColumn());
        columnSurname.setOnEditCommit(p -> {
            Participant participant = p.getRowValue();
            participant.setSurname(p.getNewValue());
            participantDAO.update(participant);
            tableView.refresh();
        });

        columnAge.setCellValueFactory(participant -> new SimpleStringProperty(Integer.toString(participant.getValue().getAge())));
        columnAge.setCellFactory(TextFieldTableCell.forTableColumn());
        columnAge.setOnEditCommit(p -> {
            Participant participant = p.getRowValue();
            participant.setAge(Integer.parseInt(p.getNewValue()));
            participantDAO.update(participant);
            tableView.refresh();
        });
        columnRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        columnRole.setCellFactory(ComboBoxTableCell.forTableColumn(Role.values()));
        columnRole.setOnEditCommit(p -> {
            Participant participant = p.getRowValue();
            participant.setRole(p.getNewValue());
            participantDAO.update(participant);
            tableView.refresh();
        });

        columnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        columnGender.setCellFactory(ComboBoxTableCell.forTableColumn(Gender.values()));
        columnGender.setOnEditCommit(p -> {
            Participant participant = p.getRowValue();
            participant.setGender(p.getNewValue());
            participantDAO.update(participant);
            tableView.refresh();
        });

        columnTeam.setCellValueFactory(participant -> {
            Team team = participant.getValue().getTeam();
            if (team != null) {
                return new SimpleStringProperty(team.getName());
            } else {
                return new SimpleStringProperty("");
            }
        });
        try {
            columnTeam.setCellFactory(ComboBoxTableCell.forTableColumn(getTeams()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        columnTeam.setOnEditCommit(p -> {
            Participant participant = p.getRowValue();
            Team team = null;
            try {
                team = (Team) TeamDAO.build().findOneByName(p.getNewValue());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            participant.setTeam(team);
            participantDAO.update(participant);
            tableView.refresh();
        });    }

    private ObservableList<String> getTeams() throws SQLException {
        List<Team> teams = TeamDAO.build().findAll(currentUser.getId());
        ObservableList<String> teamNames = FXCollections.observableArrayList();
        for (Team team : teams) {
            teamNames.add(team.getName());
        }
        return teamNames;
    }

    @FXML
    private void deleteSelected() {
        Participant selectedP = tableView.getSelectionModel().getSelectedItem();

        if (selectedP != null) {
            tableView.getItems().remove(selectedP);
            participantDAO.delete(selectedP.getDni());
            Utils.showPopUp("DELETE", "Participant deleted", "Participant has been deleted.", Alert.AlertType.INFORMATION);
        } else {
            Utils.showPopUp("ERROR", "No Participant Selected", "Please select a participant to delete.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btDelete() throws SQLException {
        deleteSelected();
    }

    @FXML
    private void switchToTeam() throws IOException {
        App.setRoot("allTeams");
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



}