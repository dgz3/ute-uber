package com.dgz3.controllers.tabjson;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SubTabYouTubeController {
    @FXML 
    private ScrollPane scrollPane;

    @FXML
    private VBox vboxList;

    @FXML
    private void initialize()
    {
        int counter = 0;
        while(counter++ < 100){
            vboxList.getChildren().add(new Text("node#" + counter));
        }

    }
}
