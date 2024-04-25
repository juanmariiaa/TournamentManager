package org.juanmariiaa;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.juanmariiaa.model.DAO.UserDAO;
import org.juanmariiaa.model.domain.User;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    private TextField txtDNI;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSurname;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnCreate;

    private UserDAO userDAO;

    public RegisterController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @FXML
    public void onBtnCreateClick() {
        String dni = txtDNI.getText();
        String name = txtName.getText();
        String surname = txtSurname.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (dni.isEmpty() || name.isEmpty() || surname.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert("Please fill in all fields");
            return;
        }

        // Consider implementing password hashing before saving the user (e.g., using BCrypt)
        User user = new User(dni, name, surname, username, password);
        try {
            userDAO.save(user);
            clearFields();
            showAlert("User created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error creating user: " + e.getMessage());
        }
    }

    @FXML
    private void goBack() throws IOException {
        App.setRoot("login.fxml");
    }

    private void clearFields() {
        txtDNI.setText("");
        txtName.setText("");
        txtSurname.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("User Registration");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
