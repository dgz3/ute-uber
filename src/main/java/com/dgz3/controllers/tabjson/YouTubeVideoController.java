package com.dgz3.controllers.tabjson;


import com.dgz3.model.YouTubeVideo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class YouTubeVideoController 
{
    @FXML private GridPane gridPane;
    @FXML private Label title;
    @FXML private Label channel;
    @FXML private Label length;
    @FXML private Label views;
    @FXML private Label published;
    @FXML private ImageView thumbnail;

    private YouTubeVideo video;

    @FXML
    private void initialize()
    {
        thumbnail.setFitHeight(108);
        thumbnail.setFitWidth(192);
        thumbnail.setPreserveRatio(true);
    }

    public void populateView(YouTubeVideo _video)
    {
        video = _video;

        title.setText(video.videoName());
        channel.setText(video.videoChannelName());
        length.setText(video.videoLength());
        views.setText(video.videoViewCount());
        published.setText(video.videoPublished());
        thumbnail.setImage(
            new Image(video.videoThumbnailUrl())
        );
        if (video.isOfficialCard()){
            System.err.println("OFFICIAL CARD VIEW -> "+ 
                video.videoName()+ 
                " , channel -> "+ 
                video.videoChannelName());
        }
    }

    public YouTubeVideo getVideo()
    {
        return(video);
    }
}
