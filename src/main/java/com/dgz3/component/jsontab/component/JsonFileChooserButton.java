package com.dgz3.component.jsontab.component;


import javafx.event.EventHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.dgz3.pattern.Observer;
import com.dgz3.pattern.Subject;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class JsonFileChooserButton extends Button implements Subject
{
    List<Observer> observers = new ArrayList<Observer>();

    private final String BUTTON_LABEL = "Choose...";

    public JsonFileChooserButton(Stage stage) 
    {
        this.setText(BUTTON_LABEL);
        this.setOnAction(new JsonFileChooserEventHandler(stage));
    }

    public File getSelectedFile()
    {
        return(
            ((JsonFileChooserEventHandler)this.getOnAction()).getSelectedFile()
        );
    }

    @Override 
    public void addObserver(Observer observer)
    {
        observers.add(observer);
    }

    @Override 
    public void removeObserver(Observer observer)
    {
        observers.remove(observer);
    }

    @Override 
    public void notifyObserver()
    {
        for (var observer : observers){
            observer.update( 
                ((JsonFileChooserEventHandler)this.getOnAction()).getSelectedFile() 
            );
        }
    }

    private class
    JsonFileChooserEventHandler
    implements EventHandler<ActionEvent>
    {
        private Stage stage;
        private File selectedFile;

        JsonFileChooserEventHandler(Stage stage) { this.stage = stage; }

        @Override
        public void handle(ActionEvent actionEvent) 
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open JSON File");
            fileChooser.getExtensionFilters().add(
                new ExtensionFilter("JSON Files", "*.json")
            );
            selectedFile = fileChooser.showOpenDialog(stage);
            notifyObserver();
        }

        public File getSelectedFile() { return(selectedFile); }
    }
}
