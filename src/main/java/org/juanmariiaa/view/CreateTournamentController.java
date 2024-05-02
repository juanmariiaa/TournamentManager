package org.juanmariiaa.view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import org.juanmariiaa.model.DAO.TournamentDAO;
import org.juanmariiaa.model.domain.Tournament;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CreateTournamentController extends Controller implements Initializable {

    @FXML
    private TableView<Tournament> tableView;

    @FXML
    private TableColumn<Tournament,String> columnID;
    @FXML
    private TableColumn<Tournament,String> columnName;
    @FXML
    private TableColumn<Tournament,String> columnLocation;
    @FXML
    private TableColumn<Tournament,String> columnCity;
    private ObservableList<Tournament> tournaments;



    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<Tournament> tournaments = TournamentDAO.build().findAll(); // Fixed variable name here
            this.tournaments = FXCollections.observableArrayList(tournaments);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tableView.setItems(this.tournaments);
        tableView.setEditable(true);
        columnName.setCellValueFactory(tournament -> new SimpleStringProperty(tournament.getValue().getName()));
        columnLocation.setCellValueFactory(tournament -> new SimpleStringProperty(tournament.getValue().getLocation()));
        columnCity.setCellValueFactory(tournament -> new SimpleStringProperty(tournament.getValue().getCity()));
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }
}
