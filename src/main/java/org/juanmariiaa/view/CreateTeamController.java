package org.juanmariiaa.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import org.juanmariiaa.model.DAO.TeamDAO;
import org.juanmariiaa.model.DAO.TournamentDAO;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.domain.Tournament;
import org.juanmariiaa.model.domain.User;
import org.juanmariiaa.others.SingletonUserSession;
import org.juanmariiaa.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is responsible for creating a new team.
 * It retrieves the input fields' values, validates them, and then creates a new Team object
 * associated with the selectedTournament.
 *
 * @throws SQLException if an error occurs while creating the team
 */
public class CreateTeamController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField institutionField;

    private Tournament selectedTournament;
    private TournamentDAO tournamentDAO;
    private TeamDAO teamDAO;
    private User currentUser;

    public CreateTeamController() {
        this.tournamentDAO = new TournamentDAO();
    }

    public void setSelectedTournament(Tournament selectedTournament) {
        this.selectedTournament = selectedTournament;
    }

    // Add this setter method for teamDAO
    public void setTeamDAO(TeamDAO teamDAO) {
        this.teamDAO = teamDAO;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = SingletonUserSession.getCurrentUser();
    }


    @FXML
    private void createTeam() {
        try {
            String name = nameField.getText();
            String city = cityField.getText();
            String institution = institutionField.getText();

            if (name.isEmpty() || city.isEmpty() || institution.isEmpty() || selectedTournament == null) {
                Utils.showPopUp("Error", null, "Please fill in all the fields and select a tournament.", Alert.AlertType.ERROR);
                return;
            }

            Team newTeam = new Team();
            newTeam.setName(name);
            newTeam.setCity(city);
            newTeam.setInstitution(institution);

            if (selectedTournament == null) {
                Utils.showPopUp("Error", null, "Please select a tournament.", Alert.AlertType.ERROR);
                return;
            }

            if (teamDAO == null) {
                Utils.showPopUp("Error", null, "TeamDAO is not initialized.", Alert.AlertType.ERROR);
                return;
            }

            teamDAO.save(currentUser, newTeam, selectedTournament.getId());

            tournamentDAO.addTeamToTournament(selectedTournament.getId(), newTeam.getId());


            nameField.clear();
            cityField.clear();
            institutionField.clear();
            closeWindow();

            Utils.showPopUp("Success", null, "Team created successfully!", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            Utils.showPopUp("Error", null, "An error occurred while creating the team.", Alert.AlertType.ERROR);
        }
    }



    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }


}
