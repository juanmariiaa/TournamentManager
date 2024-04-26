package org.juanmariiaa;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.juanmariiaa.model.DAO.UserDAO;
import org.juanmariiaa.model.domain.User;

import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField tfDni,tfName, tfSurname, tfUsername;

    @FXML
    private PasswordField tfPassword;

    UserDAO userDAO = new UserDAO();

    @FXML
    public void btSignUp(){addUser();}

    @FXML
    private void addUser(){
        App a = new App();
        String dni = tfDni.getText();
        String name = tfName.getText();
        String surname = tfSurname.getText();
        String username = tfUsername.getText();
        String password = tfPassword.getText();

        //hash password
    }

    if (dni.isEmpty() || name.isEmpty() || surname.isEmpty() username.isEmpty() || password.isEmpty()){

    }



    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("login");
    }
}