package com.dgz3.component.jsontab.component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.dgz3.component.jsontab.model.Track;
import com.dgz3.component.jsontab.service.FileJsonParseService;
import com.dgz3.pattern.Observer;
import com.dgz3.pattern.Subject;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class JsonFileTab extends Tab implements Subject
{
    private Label fileLabel;
    private TextField fileTextField;
    private List<Observer> observers = new ArrayList<Observer>();
    private File selectedFile;

    private JsonFileChooserButton jsonFileSelector;
    private ChooserObserver chooserObserver;
    private JsonFileLoadButton jsonFileLoadButton;
    private LoadObserver loadObserver;

    public JsonFileTab(Stage stage)
    {
        super("New Tab");

        Text gridTitle = new Text("Select a file");
        gridTitle.setFont(Font.font("Ariel", FontWeight.NORMAL, 20));

        fileLabel = new Label("File selected");

        fileTextField = new TextField();
        fileTextField.setDisable(true);

        jsonFileSelector = new JsonFileChooserButton(stage);
        chooserObserver = new ChooserObserver();
        jsonFileSelector.addObserver(chooserObserver);

        jsonFileLoadButton = new JsonFileLoadButton();
        loadObserver = new LoadObserver();
        jsonFileLoadButton.addObserver(loadObserver);

        HBox buttonBox = new HBox(
            jsonFileSelector,jsonFileLoadButton
        );
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setSpacing(10);

        GridPane selectorGridPane = new GridPane();
        selectorGridPane.setAlignment(Pos.CENTER);
        selectorGridPane.setHgap(10);
        selectorGridPane.setVgap(10);
        selectorGridPane.setPadding(new Insets(5,5,5,5));
        // selectorGridPane.setGridLinesVisible(true);                             /* debug */ 

        selectorGridPane.add(gridTitle, 0, 0, 2, 1);
        selectorGridPane.add(fileLabel, 0, 1);
        selectorGridPane.add(fileTextField, 1, 1);
        selectorGridPane.add(
            buttonBox,1,2
        );

        this.setClosable(false);
        this.setContent(selectorGridPane);
    }

    // @Override 
    // public void update(Object o)
    // {
    //     if (o != null){
    //         fileTextField.setText(((File)o).getName());
    //         loadButton.setDisable(false);
    //     }
    // }

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

    private void parseFile()
    {
        if (selectedFile != null) {
            FileJsonParseService service = new FileJsonParseService();
            List<Track> tracks = service.parse(selectedFile);
            for (var track : tracks){
                System.out.printf(
                    "name: %s\nartist: %s\nalbum: %s\nduration: %s\n"
                    // ,track.getName(),track.getArtist(),track.getAlbum(),track.getDuration()
                    ,track.name(),track.artist(),track.album(),track.duration()
                );
            }
        }
        this.setText(selectedFile.getName());
        this.setClosable(true);
    }

    public class ChooserObserver implements Observer
    {
        @Override 
        public void update(Object o)
        {
            if (o != null){
                selectedFile = (File)o;
                fileTextField.setText(((File)o).getName());
                jsonFileLoadButton.setDisable(false);
            }
        }
    }
    public class LoadObserver implements Observer
    {
        @Override 
        public void update(Object o)
        {
            System.out.println(selectedFile.getName());
            parseFile();
            notifyObserver();
        }
    }


}
