package org.juanmariiaa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.juanmariiaa.model.DAO.UserDAO;
import org.juanmariiaa.model.domain.User;

import java.sql.SQLException;

/**
 * JavaFX App
 */
public class Login {
    @FXML
    private TextField txtDNI;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSurname;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnCreate;

    private UserDAO userDAO;

    public Login() {
        userDAO = new UserDAO();
    }

    @FXML
    void createUser(ActionEvent event) {
        String dni = txtDNI.getText();
        String name = txtName.getText();
        String surname = txtSurname.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        User newUser = new User(dni, name, surname, username, password);

        try {
            userDAO.save(newUser);
            clearFields();
            System.out.println("User created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    private void clearFields() {
        txtDNI.clear();
        txtName.clear();
        txtSurname.clear();
        txtUsername.clear();
        txtPassword.clear();
    }

}