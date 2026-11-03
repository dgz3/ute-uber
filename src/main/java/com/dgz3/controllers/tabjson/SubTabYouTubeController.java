package com.dgz3.controllers.tabjson;

import java.util.List;

import com.dgz3.component.jsontab.model.Track;
import com.dgz3.model.YouTubeVideo;
import com.dgz3.service.YouTubeParseService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class SubTabYouTubeController 
{
    private final String VIDEO_VIEW_PATH
        = "/com/dgz3/views/tabjson/youtube-video-view.fxml";

    private YouTubeParseService parseService;

    private int focusIndex; 

    @FXML 
    private ScrollPane scrollPane;

    @FXML
    private VBox vboxList;

    @FXML
    private void initialize()
    {
        parseService = new YouTubeParseService();
        vboxList.setOnKeyPressed( event -> {
            switch(event.getCode()){
                case KeyCode.K:
                    if (vboxList.getChildren().size() > 0){
                        if (focusIndex > 0) {
                            vboxList.getChildren().get(--focusIndex).requestFocus();
                            scrollPane.setVvalue((double)(focusIndex)/(vboxList.getChildren().size()-1));
                        }
                    }
                    break;
                case KeyCode.J:
                    if (vboxList.getChildren().size() > 0){
                        if (focusIndex < vboxList.getChildren().size()-1) {
                            vboxList.getChildren().get(++focusIndex).requestFocus();
                            scrollPane.setVvalue((double)(focusIndex)/(vboxList.getChildren().size()-1));
                        }
                    }
                    break;
                default:
                    break;
            }
        });
    }

    public void searchYoutube(Track track)
    {
        if (track != null){
            List<YouTubeVideo> fetchedVideos = parseService.parse(track);
            if (fetchedVideos.size() > 0) {


                int count = 0;
                for (var video : fetchedVideos) {
                    System.out.printf(
                        "-------------------\n"+
                        "name: %s\nid: %s\nlength: %s\npublished: %s\n"+
                        "view count: %s\nchannel: %s\nchannel url: %s\nthumnail: %s\n"+
                        "-------------------\n",
                        video.videoName(),video.videoId(),video.videoLength(),video.videoPublished(),
                        video.videoViewCount(),video.videoChannelName(),video.videoChannelUrl(),video.videoThumbnailUrl()
                    );



                    try {
                        FXMLLoader loader = new FXMLLoader(
                            getClass().getResource(VIDEO_VIEW_PATH)
                        );
                        GridPane content = loader.load();

                        content.setFocusTraversable(true);

                        content.focusedProperty().addListener(
                            (observable,oldValue,newValue) -> {
                                content.setBackground(
                                    new Background(
                                        new BackgroundFill(
                                            (newValue?Color.KHAKI:Color.TRANSPARENT),
                                            null,
                                            null)
                                    )
                                );
                                setFocusTextColor(content,newValue);
                            }
                        );

                        if (count == 0){
                            content.requestFocus();
                            focusIndex = count;
                        }

                        YouTubeVideoController ytController 
                            = loader.getController();
                        ytController.populateView(video);
                        vboxList.getChildren().add(content);
                    } catch(Exception e){
                        System.out.printf(
                            "Error loading YouTube Video View : (%s) %s\n"
                            ,e.getClass()
                            ,e.getMessage()
                        );
                    }
                    ++count;
                }
            }
        }else{
            System.out.println("null track");
        }
    }

    public void setFocusTextColor(Pane pane,Boolean newValue)
    {
        for (var child : pane.getChildren()) {
            if (child instanceof Label){
                child.setStyle(
                    (newValue)?
                    "-fx-text-fill: #ff0000;":""
                );
            }
            if (child instanceof Pane) {
                setFocusTextColor((Pane)child,newValue);
            }
        }
    }
}
