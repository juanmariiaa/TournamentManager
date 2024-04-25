package org.juanmariiaa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.juanmariiaa.model.DAO.UserDAO;

import java.io.IOException;

public class App extends Application {
    private static Scene scene;
    private static Stage stage;


    @Override
    public void start(Stage stage) throws Exception {
        // Replace with your actual UserDAO implementation
        UserDAO userDAO = new UserDAO(); // Placeholder implementation

        FXMLLoader fxmlLoader = new FXMLLoader(RegisterController.class.getResource("register.fxml"));
        fxmlLoader.setControllerFactory(c -> new RegisterController(userDAO));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("User Registration");
        stage.setScene(scene);
        stage.show();
    }



    private static Scene createScene(String fxml, double width, double height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        return new Scene(root, width, height);
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
