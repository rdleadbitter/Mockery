package com.mockeryfx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class for the NFL Mock Draft Simulator.
 * Handles initialization and scene switching.
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 640, 600);
        stage.setTitle("MOCKERY");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Changes the root of the current scene to the given FXML layout.
     * @param fxml The name of the FXML file (without extension)
     * @throws IOException If loading the FXML fails
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Loads an FXML file from the resources folder.
     * @param fxml The name of the FXML file (without ".fxml")
     * @return The loaded Parent node
     * @throws IOException If the file cannot be read
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Returns the currently active scene.
     * @return The active Scene instance
     */
    public static Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
        launch();
    }
} 
