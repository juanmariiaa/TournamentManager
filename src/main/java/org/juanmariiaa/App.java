package org.juanmariiaa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file for the Login view
            Parent root = FXMLLoader.load(getClass().getResource("yow.fxml"));

            // Set the scene to the stage
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("yow");

            // Show the stage
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
