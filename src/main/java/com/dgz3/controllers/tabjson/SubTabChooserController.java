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
//        FXMLLoader tracksPageLoader = new FXMLLoader(
//            getClass().getResource(FXML_TRACKS_VIEW_PATH)
//        );
//        VBox content = null;
//        try {
//            content = tracksPageLoader.load();
//        } catch(Exception e){
//            System.err.printf(
//                "[Error <%s>] Could not load fxml file %s.\n\tSee message: %s\n"
//                ,e.getClass()
//                ,FXML_TRACKS_VIEW_PATH
//                ,e.getMessage()
//            );
//        }
//
//        if (selectedFile == null) return;
//        tracks = parseService.parse(selectedFile);
//            for (var track : tracks){
//                System.out.printf(
//                    "name: %s\nartist: %s\nalbum: %s\nduration: %s\n"
//                    ,track.getName(),track.getArtist(),track.getAlbum(),track.getDuration()
//                    // ,track.name(),track.artist(),track.album(),track.duration()
//                );
//            }
//
//        if (tracks == null) return;
//        tracksTable.setItems(
//            FXCollections.observableList(tracks)
//        );
//
//        tab.setContent(content);
//        if (tab != null) tab.setClosable(true);
//        onLoadCreateNewTab.run();
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

    // private void parseFile()
    // {
    //     if (selectedFile != null) {
    //         FileJsonParseService service = new FileJsonParseService();
    //         tracks = service.parse(selectedFile);
    //         for (var track : tracks){
    //             System.out.printf(
    //                 "name: %s\nartist: %s\nalbum: %s\nduration: %s\n"
    //                 ,track.getName(),track.getArtist(),track.getAlbum(),track.getDuration()
    //                 // ,track.name(),track.artist(),track.album(),track.duration()
    //             );
    //         }
    //     }

    //     TableView<Track> table = new TableView<>();
    //     TableColumn<Track,String> nameCol = new TableColumn<>("Name");
    //     nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    //     TableColumn<Track,String> artistCol = new TableColumn<>("Artist");
    //     artistCol.setCellValueFactory(new PropertyValueFactory<>("artist"));
    //     TableColumn<Track,String> albumCol = new TableColumn<>("Album");
    //     albumCol.setCellValueFactory(new PropertyValueFactory<>("album"));
    //     TableColumn<Track,String> durationCol = new TableColumn<>("Duration");
    //     durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));

    //     TableColumn<Track,String> linkCol = new TableColumn<>("Link");
    //     linkCol.setCellValueFactory( rowdata -> {
    //         Track track = rowdata.getValue();

    //         SimpleStringProperty link = new SimpleStringProperty(
    //             "https://www.youtube.com/results?search_query=" +
    //             URLEncoder.encode(track.getName(),StandardCharsets.UTF_8) +
    //             "+" +
    //             URLEncoder.encode(track.getArtist(),StandardCharsets.UTF_8)
    //         );

    //         return( link );
    //     }); 

    //     // linkCol.setCellFactory(

    //     // );
    //     // linkCol.setCellValueFactory(cellData -> {
    //     //     Track track = cellData.getValue();
    //     //     Hyperlink link = new Hyperlink(
    //     //         "https://www.youtube.com/results?search_query=" +
    //     //         URLEncoder.encode(track.getName(),StandardCharsets.UTF_8) +
    //     //         "+" +
    //     //         URLEncoder.encode(track.getArtist(),StandardCharsets.UTF_8)
    //     //     );
    //     //     link.setOnAction(new EventHandler<ActionEvent>() {
    //     //         @Override 
    //     //         public void handle(ActionEvent actionEvent){
    //     //             System.out.println(
    //     //                 ((Hyperlink)actionEvent.getSource()).getText()
    //     //             );
    //     //         }
    //     //     });
    //     // });

    //     table.getColumns().add(nameCol);
    //     table.getColumns().add(artistCol);
    //     table.getColumns().add(albumCol);
    //     table.getColumns().add(durationCol);
    //     table.getColumns().add(linkCol);
    //     ObservableList<Track> observableTracks = FXCollections.observableList(tracks);
    //     table.setItems(observableTracks);

    //     this.setContent(
    //         new StackPane(
    //             table,
    //             new Hyperlink("hi")
    //         )
    //     );

    //     this.setText(selectedFile.getName());
    //     this.setClosable(true);
    // }

/* This was originally for debugging new tabs; delete later
    @FXML 
    private void createNewTab()
    {
        onLoadCreateNewTab.run();
    } */

}
