package com.dgz3.controllers.tabjson;

import java.io.File;
import java.util.List;

import com.dgz3.component.jsontab.model.Track;
import com.dgz3.service.JsonFileParseService;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class SubTabChooserController 
{
    private Tab tab;

    private JsonFileParseService parseService;

    private Runnable onLoadCreateNewTab;

    @FXML 
    private GridPane subGridPane;
    @FXML
    private TextField fileTextField;
    @FXML 
    private Button fileChooserButton;
    @FXML 
    private Button fileLoaderButton;
    private File selectedFile;

    List<Track> tracks;

    @FXML
    private void initialize()
    {
        tab = getTabContainer();
        parseService = new JsonFileParseService();
    }

    public void setOnLoadCreateNewTab(Runnable callback)
    {
        onLoadCreateNewTab = callback;
    }

    public String getFileName()
    {
        return(selectedFile.getName());
    }

    public File getFile()
    {
        return(selectedFile);
    }

    @FXML 
    private void loadFile()
    {
        onLoadCreateNewTab.run();
    }

    private Tab getTabContainer()
    {
        Tab currentTab = null;
        Parent parent = subGridPane.getParent();
        while( (parent != null) && !(parent instanceof TabPane) ){
            parent = parent.getParent();
        }
        if(parent instanceof TabPane){
            TabPane tabPane = (TabPane) parent;
            currentTab = tabPane.getSelectionModel().getSelectedItem();
        }
        return(currentTab);
    }

    @FXML
    private void chooseFile(ActionEvent actionEvent)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open JSON File");
        fileChooser.getExtensionFilters().add(
            new ExtensionFilter("JSON Files", "*.json")
        );
        selectedFile = fileChooser.showOpenDialog(
            ((Node) actionEvent.getTarget()).getScene().getWindow()
        );
        if (selectedFile != null){
            fileLoaderButton.setDisable(false);
            fileTextField.setText(selectedFile.getName());
        }
    }
}
