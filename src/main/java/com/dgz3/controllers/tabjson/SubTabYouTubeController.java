package com.dgz3.controllers.tabjson;

import java.util.List;

import com.dgz3.component.jsontab.model.Track;
import com.dgz3.model.YouTubeVideo;
import com.dgz3.service.YouTubeParseService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class SubTabYouTubeController 
{
    private final String VIDEO_VIEW_PATH
        = "/com/dgz3/views/tabjson/youtube-video-view.fxml";

    private YouTubeParseService parseService;

    @FXML 
    private ScrollPane scrollPane;

    @FXML
    private VBox vboxList;

    @FXML
    private void initialize()
    {
        parseService = new YouTubeParseService();
    }

    public void searchYoutube(Track track)
    {
        if (track != null){
            List<YouTubeVideo> fetchedVideos = parseService.parse(track);
            if (fetchedVideos.size() > 0) {
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



                    // ImageView iv = new ImageView(
                    //     new Image(video.videoThumbnailUrl())
                    // );
                    // iv.setFitWidth(192);
                    // iv.setFitHeight(108);
                    // iv.setPreserveRatio(true);

                    // vboxList.getChildren().add(
                    //     iv
                    // );

                }
            }
        }else{
            System.out.println("null track");
        }
    }
}
