package com.mycompany.assignment02;

//Github repo: https://github.com/BoomEAE/Car-Race-Simulation.git

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.*;

/**
 * JavaFX App
 * @author Eric Andre Evsei
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(loadFXML("SlideshowFXML"), 1000, 500);
        stage.setScene(scene);
        stage.setTitle("Traffic Simulation");
        stage.getIcons().add(new Image(getClass().getResource("/images/carIcon.png").toExternalForm()));
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}