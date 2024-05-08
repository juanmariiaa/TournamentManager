package org.juanmariiaa;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.juanmariiaa.model.DAO.TeamDAO;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.utils.Utils;
import java.io.IOException;
import java.sql.SQLException;

public class CreateTeamController {
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfCity;
    @FXML
    private TextField tfInstitution;
    @FXML
    private ListView<String> lvParticipants;
    @FXML
    private Button btnCreate;

    private TeamDAO teamDAO = new TeamDAO();

    private ObservableList<Team> teams;


    @FXML
    private void initialize() throws SQLException {
        clearFields();
    }

    private void clearFields() {
        tfName.clear();
        tfCity.clear();
        tfInstitution.clear();
    }

    @FXML
    private void createTeam() throws IOException {
        try {
            String name = tfName.getText();
            String city = tfCity.getText();
            String institution = tfInstitution.getText();

            if (name.isEmpty() || city.isEmpty() || institution.isEmpty()) {
                Utils.showPopUp("Error", null, "Please fill in all the fields.", Alert.AlertType.ERROR);
                return;
            }

            Team newTeam = new Team();
            newTeam.setName(name);
            newTeam.setCity(city);
            newTeam.setInstitution(institution);

            teamDAO.save(newTeam);

            Utils.showPopUp("Success", null, "Team created successfully.", Alert.AlertType.INFORMATION);

            clearFields();

            switchToTeam();
        } catch (SQLException e) {
            Utils.showPopUp("Error", null, "An error occurred while creating the team.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void switchToTeam() throws IOException {
        App.setRoot("team");
    }

}
