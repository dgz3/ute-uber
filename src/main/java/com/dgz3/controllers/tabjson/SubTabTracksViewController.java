package com.dgz3.controllers.tabjson;

import java.io.File;
import java.util.List;

import com.dgz3.component.jsontab.model.Track;
import com.dgz3.service.JsonFileParseService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SubTabTracksViewController 
{
    private JsonFileParseService parseService;

    @FXML
    private TableView<Track> tracksTable;
    @FXML
    private TableColumn<Track,String> nameColumn;
    @FXML
    private TableColumn<Track,String> artistColumn;
    @FXML
    private TableColumn<Track,String> albumColumn;
    @FXML
    private TableColumn<Track,String> durationColumn;

    List<Track> tracks;

    @FXML
    private void initialize()
    {
        parseService = new JsonFileParseService();
        nameColumn
            .setCellValueFactory(new PropertyValueFactory<>("name"));
        artistColumn
            .setCellValueFactory(new PropertyValueFactory<>("artist"));
        albumColumn
            .setCellValueFactory(new PropertyValueFactory<>("album"));
        durationColumn
            .setCellValueFactory(new PropertyValueFactory<>("duration"));
    }

    public void parseFile(File file)
    {
        tracks = parseService.parse(file);
        if (tracks != null){
            tracksTable.setItems(
                FXCollections.observableList(tracks)
            );
        }
    }
}
