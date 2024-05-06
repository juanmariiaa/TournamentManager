package org.juanmariiaa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        scene = new Scene(loadFXML("login"), 600, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private static Scene createScene(String fxml, double width, double height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, width, height);
        return scene;
    }

    public static void setRoot(String fxml) throws IOException {

        Parent p = loadFXML(fxml);
        Scene newScene;

        if (fxml.equals("login")) {
            newScene = createScene(fxml, 308, 411);
            primaryStage.setResizable(false);

        }else if(fxml.equals("team")){
            newScene = createScene(fxml, 774, 489);
            primaryStage.setResizable(false);


        }else if(fxml.equals("tournament")){
            newScene = createScene(fxml, 774, 489);
            primaryStage.setResizable(false);

        }else if(fxml.equals("participant")){
            newScene = createScene(fxml, 774, 489);
            primaryStage.setResizable(false);

        }else if(fxml.equals("home")){
            newScene = createScene(fxml, 774, 489);
            primaryStage.setResizable(false);

        }else if(fxml.equals("createTournament")){
            newScene = createScene(fxml, 362, 592);
            primaryStage.setResizable(false);

        }else if(fxml.equals("createTeam")){
            newScene = createScene(fxml, 362, 592);
            primaryStage.setResizable(false);

        }else if(fxml.equals("createParticipant")){
            newScene = createScene(fxml, 362, 761);
            primaryStage.setResizable(false);

        }else if(fxml.equals("register")){
            newScene = createScene(fxml, 329, 533);
            primaryStage.setResizable(false);

        }else {
            newScene = createScene(fxml, 308, 411);
            primaryStage.setResizable(true);
        }
        primaryStage.setScene(newScene);
        App.scene.setRoot(p);


    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }

}