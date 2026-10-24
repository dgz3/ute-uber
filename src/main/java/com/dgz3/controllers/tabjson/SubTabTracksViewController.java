package com.dgz3.controllers.tabjson;

import java.io.File;
import java.util.List;


import com.dgz3.component.jsontab.model.Track;
import com.dgz3.service.JsonFileParseService;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

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
    @FXML
    private TableColumn<Track,String> linkColumn;

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
        linkColumn
            .setCellValueFactory( new Callback<CellDataFeatures<Track,String>,ObservableValue<String>> () {
                public ObservableValue<String> call(CellDataFeatures<Track,String> data) 
                {
                    ObservableValue<String> link = new SimpleStringProperty(
                        data.getValue().getName() + "+" + data.getValue().getArtist()
                    );
                    return(link);
                }
            });

        tracksTable.setRowFactory( tv -> {
            TableRow<Track> row = new TableRow<>();

            row.setOnMouseClicked( (MouseEvent event) -> {

                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY){
                    Track clickedItem = row.getItem();
                    handleRowClick(clickedItem);
                }
            });

            return(row);
        });
    }

    private void handleRowClick(Track clickedItem)
    {
        System.out.println(
            "name: " + clickedItem.getName()
            + "\n" +
            "artist: " + clickedItem.getArtist()
        );
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
