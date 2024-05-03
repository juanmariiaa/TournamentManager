package org.juanmariiaa;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.juanmariiaa.App;
import org.juanmariiaa.model.DAO.UserDAO;
import org.juanmariiaa.model.domain.User;
import org.juanmariiaa.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterController {
    @FXML
    private TextField tfDni;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfSurname;
    @FXML
    private TextField tfUsername;

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


        if (dni.isEmpty() || name.isEmpty() || surname.isEmpty() || username.isEmpty() || password.isEmpty()){
            Utils.showPopUp("Error", "Empty fields", "Please, complete all fields to continue.", Alert.AlertType.ERROR);

        }else{
            try{
                if(userDAO.findByDni(dni)!= null){
                    Utils.showPopUp("Error", "DNI already exists", "Please, choose another DNI.", Alert.AlertType.ERROR);
                }else{
                    User user = new User(dni, name, surname, username, password);
                    userDAO.save(user);

                    Utils.showPopUp("Success", "User created", "User created successfully.", Alert.AlertType.INFORMATION);
                }
            }catch (SQLException e){
                Utils.showPopUp("Error", "Database error", "Please, try again later.", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void switchToLogin() {
        try {
            App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
