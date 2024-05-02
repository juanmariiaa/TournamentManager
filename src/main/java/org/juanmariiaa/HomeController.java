package org.juanmariiaa;

import javafx.fxml.FXML;
import org.juanmariiaa.App;

import java.io.IOException;

public class HomeController {
    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("Login");
    }
    @FXML
    private void switchToTournament() throws IOException {
        App.setRoot("Tournament");
    }
    @FXML
    private void switchToRegister() throws IOException {
        App.setRoot("Register");
    }



}
