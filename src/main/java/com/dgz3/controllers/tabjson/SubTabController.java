package com.dgz3.controllers.tabjson;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class SubTabController 
{
    private enum ContentViewFlag { CHOOSER,TRACKS,YOUTUBE }

    private final String CHOOSER_VIEW_PATH
        = "/com/dgz3/views/tabjson/sub-tab-chooser-view.fxml";
    private final String TRACKS_VIEW_PATH
        = "/com/dgz3/views/tabjson/sub-tab-tracks-view.fxml";
    private final String YOUTUBE_VIEW_PATH
        = "/com/dgz3/views/tabjson/sub-tab-youtube-view.fxml";

    @FXML 
    private Tab tab;

    private SubTabChooserController chooserController;
    private SubTabTracksViewController tracksController;
    private SubTabYouTubeController youtubeController;

    @FXML
    private void initialize()
    {
        GridPane grid = (GridPane) loadFXMLContent(ContentViewFlag.CHOOSER);
        if (grid != null){
            tab.setContent(grid);
        }
    }

    public void setOnLoadCreateNewTab(Runnable newTabCallback)
    {
        chooserController.setOnLoadCreateNewTab(() -> {
            VBox vbox = (VBox) loadFXMLContent(ContentViewFlag.TRACKS);
            if (vbox != null) {
                switchContentToTracksView(vbox);
                newTabCallback.run();
            }
        });
    }

    private void switchContentToTracksView(VBox vbox)
    {
        tab.setClosable(true);
        tab.setText(chooserController.getFileName());
        tracksController.setOnRowClickLoadYouTubeView( () -> {
            switchContentToYouTubeView();
        });
        tracksController.parseFile(chooserController.getFile());
        tab.setContent(vbox);
    }

    private void switchContentToYouTubeView()
    {
        ScrollPane scrollPane = (ScrollPane) loadFXMLContent(ContentViewFlag.YOUTUBE);
        if (scrollPane == null) return;

        youtubeController.searchYoutube(tracksController.getTrack());
        tab.setOnSelectionChanged(event -> {
            if (!tab.isSelected()) return;
            /* let ui finish rending the tab content first */
            Platform.runLater( () -> youtubeController.resetFocus() );
        });
        tab.setContent(scrollPane);
    }

    private Object loadFXMLContent(ContentViewFlag flag)
    {
        String fxmlPath = null;
        switch(flag){
            case CHOOSER -> fxmlPath = CHOOSER_VIEW_PATH;
            case TRACKS  -> fxmlPath = TRACKS_VIEW_PATH;
            case YOUTUBE -> fxmlPath = YOUTUBE_VIEW_PATH;
        }

        Object contentObject = null;
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource(fxmlPath)
            );
            contentObject = loader.load();
            switch (flag) {
                case CHOOSER -> chooserController = loader.getController();
                case TRACKS  -> tracksController  = loader.getController();
                case YOUTUBE -> youtubeController = loader.getController();
            }
        } catch(Exception e) {
            System.out.printf(
                "SubTabController Error: (%s) %s\n"+
                "Error occurred loading the following fxml content: %s\n"
                ,e.getClass()
                ,e.getMessage()
                ,fxmlPath
            );
        }
        return(contentObject);
    }
}
