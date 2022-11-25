package com.mycompany.requestconverter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 * JavaFX App
 */
public class App extends Application {
    


    private static Scene scene;

    public static Scene getScene() {
        return scene;
    }
    

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("fxml/primary"), 600, 325);
        stage.getIcons().add(new Image(getClass().getResource("/com/mycompany/requestconverter/icons/icon.png").toExternalForm()));
        stage.setTitle("Конвертер запросов ПФР");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
         FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(1000), scene.getRoot());
                fadeOutTransition.setFromValue(0.0);
                fadeOutTransition.setToValue(1.0);
                fadeOutTransition.play();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws ClassNotFoundException {
       
    
        launch();
    }

}