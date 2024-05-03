package org.juanmariiaa;

import javafx.fxml.FXML;

import java.io.IOException;

public class HomeController {
    @FXML
    private void switchToTournament() throws IOException {
        App.setRoot("tournament");
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



}
