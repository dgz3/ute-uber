package com.dgz3.component.jsontab.component;

import java.util.ArrayList;
import java.util.List;

import com.dgz3.pattern.Observer;
import com.dgz3.pattern.Subject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class JsonFileLoadButton extends Button implements Subject
{
    List<Observer> observers = new ArrayList<Observer>();
    private final String BUTTON_LABEL = "Load";
    public JsonFileLoadButton()
    {
        this.setText(BUTTON_LABEL);
        this.setDisable(true);
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent actionEvent){
                notifyObserver();
            }
        });
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
            observer.update( null );
        }
    }
}
