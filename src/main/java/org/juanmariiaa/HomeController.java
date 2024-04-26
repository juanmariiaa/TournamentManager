package org.juanmariiaa;

import javafx.fxml.FXML;

import java.io.IOException;

public class HomeController {
    @FXML
    private void switchToCreateTeam() throws IOException {
        App.setRoot("createTeam");
    }


}
