package org.juanmariiaa;

import javafx.fxml.FXML;

import java.io.IOException;

public class LoginController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("register");
    }
}
