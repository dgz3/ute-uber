package com.dgz3.model;

public record YouTubeVideo(
    String videoName,
    String videoId,
    String videoLength,
    String videoPublished,
    String videoViewCount,
    String videoChannelName,
    String videoChannelUrl,
    String videoThumbnailUrl
) {

}
