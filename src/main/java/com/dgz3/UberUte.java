package com.dgz3;

import java.io.InputStream;
import java.util.Properties;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class UberUte extends Application {

    @Override
    public void start(Stage stage) {
        Properties buildProps = new Properties();
        try(InputStream input = getClass().getResourceAsStream("/build.properties"))
        {
            buildProps.load(input);

            System.out.println(
                buildProps.getProperty("app.name")
            );
        }catch(Exception exception){
            System.out.println("exception!!!!");
            System.err.println(exception.getMessage());
        }

        // var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        // var scene = new Scene(new StackPane(label), 640, 480);
        // stage.setScene(scene);
        // stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
