package com.dgz3.controllers.tabjson;

import java.util.List;

import com.dgz3.component.jsontab.model.Track;
import com.dgz3.model.YouTubeVideo;
import com.dgz3.service.YouTubeParseService;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SubTabYouTubeController 
{
    private final String VIDEO_VIEW_PATH
        = "/com/dgz3/views/tabjson/youtube-video-view.fxml";

    private YouTubeParseService parseService;

    private int focusIndex; 

    @FXML 
    private ScrollPane scrollPane;

    @FXML
    private VBox ytVBox;

    private ObservableList<Node> ytVidList;

    @FXML
    private void initialize()
    {
        parseService = new YouTubeParseService();
        ytVidList = ytVBox.getChildren();
        ytVBox.setOnKeyPressed( event -> {
            if (ytVidList.size() == 0) return;

            switch(event.getCode()){
                case KeyCode.UP:
                case KeyCode.K:
                    if (focusIndex > 0) focusIndex -= 1;
                    break;
                case KeyCode.DOWN:
                case KeyCode.J:
                    if (focusIndex < ytVidList.size()-1) focusIndex += 1;
                    break;
                default:
                    break;
            }

            ytVidList.get(focusIndex).requestFocus();
            scrollPane.setVvalue((double)(focusIndex)/(ytVidList.size()-1));

            Bounds nodeBounds = scrollPane.getContent().sceneToLocal(
                ytVidList.get(focusIndex).localToScene(ytVidList.get(focusIndex).getBoundsInLocal())
            );
            double nodeTop = nodeBounds.getMinY();
            double nodeBottom = nodeBounds.getMaxY();

            System.out.printf("vp: %g , content: %g , vvalue: %g , nodeTop: %g , nodeBottom: %g\n",
                 scrollPane.getViewportBounds().getHeight()
                ,scrollPane.getContent().getLayoutBounds().getHeight()
                ,scrollPane.getVvalue()
                ,nodeTop
                ,nodeBottom
            );
        });

    }

    public void resetFocus()
    {
        System.err.println("request!! -> " + focusIndex);
        ytVidList.get(focusIndex).requestFocus();
    }

    public void searchYoutube(Track track)
    {
        if (track == null){
            /* todo: error / usage */
            System.out.println("null track");
            return;
        }

        List<YouTubeVideo> fetchedVideos = parseService.parse(track);

        if (fetchedVideos.size() == 0){
            /* todo: error / usage */
            System.out.println("null fetchedVideos");
            return;
        }

        /* todo: undetermined if this is needed (id:qc1)*/
        // int count = 0;
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

                content.setFocusTraversable(false);

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

                /* todo: undetermined if this is needed (id:qc1)*/
                //if (count == 0){
                //    content.requestFocus();
                //    focusIndex = count;
                //}

                YouTubeVideoController ytController 
                    = loader.getController();
                ytController.populateView(video);
                ytVidList.add(content);
            } catch(Exception e){
                System.out.printf(
                    "Error loading YouTube Video View : (%s) %s\n"
                    ,e.getClass()
                    ,e.getMessage()
                );
            }
            /* todo: undetermined if this is needed (id:qc1)*/
            //count += 1;
        }

        /* focus first node after finished rendering */
        Platform.runLater( () -> {
            ytVidList.get(0).requestFocus();
        });

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
