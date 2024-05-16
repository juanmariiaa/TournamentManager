package org.juanmariiaa.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.juanmariiaa.model.DAO.ParticipantDAO;
import org.juanmariiaa.model.DAO.TeamDAO;
import org.juanmariiaa.model.domain.Participant;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.enums.Gender;
import org.juanmariiaa.model.enums.Role;
import org.juanmariiaa.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller class for displaying and managing details of a selected team.
 * This controller handles updating team details, displaying associated participants
 * and creating new participants for this selected tournament.
 */
public class SelectedTeamController {


    @FXML
    private TextField nameField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField institutionField;
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
    private Team selectedTeam;
    private ParticipantDAO participantDAO;
    private TeamDAO teamDAO;
    private ObservableList<Participant> participantsData;


    public void initialize(Team selectedTeam) {
        this.selectedTeam = selectedTeam;
        this.teamDAO = new TeamDAO();
        this.participantDAO = new ParticipantDAO();
        nameField.setText(selectedTeam.getName());
        cityField.setText(selectedTeam.getCity());
        institutionField.setText(selectedTeam.getInstitution());

        displayParticipants();
    }



    /**
     * Displays the details of a selected team's participants.
     * This controller also handles updating participant details.
     */
    private void displayParticipants() {
        List<Participant> participants = participantDAO.findParticipantsByTeam(selectedTeam.getId());
        participantsData = FXCollections.observableArrayList(participants);

        tableView.setItems(this.participantsData);
        tableView.setEditable(true);
        columnDNI.setCellValueFactory(new PropertyValueFactory<>("dni"));
        columnDNI.setCellFactory(TextFieldTableCell.forTableColumn());
        columnDNI.setOnEditCommit(p -> {
            Participant participant = p.getRowValue();
            participant.setDni(p.getNewValue());
            participantDAO.update(participant);
            // Update the participantsData list
            participantsData.set(tableView.getSelectionModel().getSelectedIndex(), participant);
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
            participantsData.set(tableView.getSelectionModel().getSelectedIndex(), participant);
            tableView.refresh();
        });

        columnAge.setCellValueFactory(participant -> new SimpleStringProperty(Integer.toString(participant.getValue().getAge())));
        columnAge.setCellFactory(TextFieldTableCell.forTableColumn());
        columnAge.setOnEditCommit(p -> {
            Participant participant = p.getRowValue();
            participant.setAge(Integer.parseInt(p.getNewValue()));
            participantDAO.update(participant);
            // Update the participantsData list
            participantsData.set(tableView.getSelectionModel().getSelectedIndex(), participant);
            tableView.refresh();
        });
        columnRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        columnRole.setCellFactory(ComboBoxTableCell.forTableColumn(Role.values()));
        columnRole.setOnEditCommit(p -> {
            Participant participant = p.getRowValue();
            participant.setRole(p.getNewValue());
            participantDAO.update(participant);
            // Update the participantsData list
            participantsData.set(tableView.getSelectionModel().getSelectedIndex(), participant);
            tableView.refresh();
        });

        columnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        columnGender.setCellFactory(ComboBoxTableCell.forTableColumn(Gender.values()));
        columnGender.setOnEditCommit(p -> {
            Participant participant = p.getRowValue();
            participant.setGender(p.getNewValue());
            participantDAO.update(participant);
            // Update the participantsData list
            participantsData.set(tableView.getSelectionModel().getSelectedIndex(), participant);
            tableView.refresh();
        });
    }

    /**
     * Updates the details of the selected team.
     */
    @FXML
    private void updateTeam() {
        if (nameField.getText().isEmpty() || cityField.getText().isEmpty() || institutionField.getText().isEmpty()) {
            Utils.showPopUp("ERROR", "Update Failed", "Please fill in all the fields.", Alert.AlertType.ERROR);
            return;
        }

        selectedTeam.setName(nameField.getText());
        selectedTeam.setCity(cityField.getText());
        selectedTeam.setInstitution(institutionField.getText());
        teamDAO.update(selectedTeam);
        Utils.showPopUp("UPDATE", "Team Updated", "Team details updated successfully.", Alert.AlertType.INFORMATION);
    }


    /**
     * Deletes the selected participant from the team.
     */
    @FXML
    private void deleteSelected() {
        Participant selectedP = (Participant) tableView.getSelectionModel().getSelectedItem();

        if (selectedP != null) {
            tableView.getItems().remove(selectedP);

            participantDAO.delete(String.valueOf(selectedP));
            Utils.showPopUp("DELETE", "Team deleted", "This team has deleted.", Alert.AlertType.INFORMATION);

        }
    }

    @FXML
    private void switchToCreateParticipant() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateParticipant.fxml"));
            Parent root = loader.load();

            CreateParticipantController controller = loader.getController();
            controller.setSelectedTeam(selectedTeam);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Create Participant");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            displayParticipants();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void closeWindow() {
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
    }


}
