package org.juanmariiaa.view;

import javafx.fxml.FXML;
import org.juanmariiaa.App;

import java.io.IOException;

public class HomeController {
    @FXML
    private void switchToCreateTeam() throws IOException {
        App.setRoot("TournamentMenu");
    }


}
