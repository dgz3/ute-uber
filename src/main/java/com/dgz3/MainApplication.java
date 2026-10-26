package com.dgz3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application 
{
    @Override
    public void 
    start(Stage stage) 
    throws Exception 
    {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource( "views/main-view.fxml" )
        );
        Parent root = loader.load();

        Scene scene = new Scene(root);

        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.setTitle("TITLE");
        stage.setScene(scene);
        stage.show();
    }


    public static void 
    main(String[] args)
    {
        launch(args);
    }
}
