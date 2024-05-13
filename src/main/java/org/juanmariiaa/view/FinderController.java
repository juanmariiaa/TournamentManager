package org.juanmariiaa.view;

import javafx.fxml.FXML;

import java.io.IOException;

public class FinderController {

    @FXML
    private void switchToTournaments() throws IOException {
        App.setRoot("allTournaments");
    }
    @FXML
    private void switchToParticipants() throws IOException {
        App.setRoot("allParticipants");
    }
    @FXML
    private void switchToTeams() throws IOException {
        App.setRoot("allTeams");
    }
    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }
}
