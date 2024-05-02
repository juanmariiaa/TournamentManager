package org.juanmariiaa;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.juanmariiaa.App;
import org.juanmariiaa.model.DAO.UserDAO;
import org.juanmariiaa.others.SingletonUserSession;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField tfUser;
    @FXML
    private PasswordField tfPass;

    @FXML
    private void login() throws SQLException, IOException {
        String username = tfUser.getText().trim();
        String password = tfPass.getText().trim();

        if (username.equals("") || password.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("ERROR");
            alert.setContentText("Please, complete all fields to continue.");
            alert.showAndWait();
        }else{
            UserDAO uDAO = new UserDAO();
            String id;
            if((id=uDAO.validateLogin(username, password))!=null) {
                SingletonUserSession.login(id,username);
                //logged in
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("SUCCESS");
                alert.setHeaderText("You are logged in.");
                alert.setContentText("Welcome, "+username);
                alert.showAndWait();
                switchToHome();
            }else{
                SingletonUserSession.logout();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERROR");
                alert.setHeaderText("Invalid credentials.");
                alert.setContentText("Please, try again.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void switchToRegister() throws IOException {
        App.setRoot("register");
    }
    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
}
