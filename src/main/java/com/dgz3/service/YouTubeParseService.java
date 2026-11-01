package com.dgz3.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.dgz3.component.jsontab.model.Track;
import com.dgz3.model.YouTubeVideo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class YouTubeParseService 
{
    private HttpService httpService;
    private final String FIRST_LEFT_BRACE = "{";

    public YouTubeParseService()
    {
        httpService = new HttpService();
    }

    public List<YouTubeVideo> parse(Track track)
    {
        List<YouTubeVideo> fetchedVideos = new ArrayList<YouTubeVideo>();

        String body = httpService.fetchYouTubeSearch(
            (track.getName()+" "+track.getArtist()).replace(" ", "+")
        );

        if (body == null) {
            System.out.println("error: null body");
            return(null);
        }

        Document doc = Jsoup.parse(body);
        Element elem = doc.selectFirst("script[nonce]:containsData(ytInitialData)");

        String nodeBody = null;
        try {

            if (elem != null) {
                // System.out.println(elem.data().substring(index,50));
                ObjectMapper mapper = new ObjectMapper();
                int index = elem.data().indexOf(FIRST_LEFT_BRACE);               // strip js 'var x = ' 
                String json = elem.data().substring(index, elem.data().length()-1);
                JsonNode rootNode = mapper.readTree(json);

                // String contents = rootNode.at("/contents/twoColumnSearchResultsRenderer/primaryContents/sectionListRenderer/contents/0/itemSectionRenderer/contents").toString();
                // if (contents != null) System.out.println(contents);
                // JsonNode contents = mapper.readTree(rootNode.at("/contents/twoColumnSearchResultsRenderer/primaryContents/sectionListRenderer/contents/0/itemSectionRenderer/contents").toString());

                rootNode = mapper.readTree(
                    rootNode
                    .path("contents")
                    .path("twoColumnSearchResultsRenderer")
                    .path("primaryContents")
                    .path("sectionListRenderer")
                    .path("contents")
                    .get(0)
                    .path("itemSectionRenderer")
                    .toString()
                );

                JsonNode contents = rootNode.path("contents");
                if (contents.isArray()){
                    int nodeCount=0;
                    JsonNode nodeRoot = null;

                    for (var node : contents) {

                        System.out.printf("(%d) ", ++nodeCount);
                        nodeBody = node.toPrettyString();

                        nodeRoot = node.path("videoRenderer");
                        if(!nodeRoot.isMissingNode()){
                            YouTubeVideo ytVideo = new YouTubeVideo(
                                node.at("/videoRenderer/title/runs/0/text").asText()
                                ,node.at("/videoRenderer/videoId").asText()
                                ,node.at("/videoRenderer/lengthText/simpleText").asText()
                                ,node.at("/videoRenderer/publishedTimeText/simpleText").asText()
                                ,node.at("/videoRenderer/shortViewCountText/simpleText").asText()
                                ,node.at("/videoRenderer/ownerText/runs/0/text").asText()
                                ,node.at("/videoRenderer/ownerText/runs/0/navigationEndpoint/commandMetadata/webCommandMetaData/url").asText()
                                ,node.at("/videoRenderer/thumbnail/thumbnails/0/url").asText()
                            );
                            fetchedVideos.add(ytVideo);
                            continue;
                        }

                        nodeRoot = node.path("officialCardViewModel");
                        if(!nodeRoot.isMissingNode()){
                            nodeRoot = nodeRoot
                                       .path("contents")
                                       .get(0)
                                       .path("horizontalShelfViewModel")
                                       .path("items");
                            for (var item : nodeRoot) {
                                System.out.printf("  - %s\n"
                                    ,item
                                    .path("lockupViewModel")
                                    .path("metadata")
                                    .path("lockupMetadataViewModel")
                                    .path("title")
                                    .path("content")
                                    .toString()
                                );
                            }
                            continue;
                        }
                        System.out.println("Skipping shorts");
                    }

                }

            }
        } catch(Exception e) {
            System.out.println(
                e.getClass() + " " + e.getMessage()
            );
            System.out.println(nodeBody);
        }

        return(fetchedVideos);
    }

}

                // } catch (Exception exception) {
                //     System.out.println(exception.getClass());
                //     System.out.println(exception.getMessage());
                // }


                    // int nodeCount=0;
                    // for( var node : contents){

                    //     System.out.println( 
                    //         nodeRoot .path("title") .path("runs") .get(0) .path("text") .toString() );
                    //     System.out.println(++nodeCount);
                    //     if (nodeCount == 3){
                    //         System.out.println(node.toString());
                    //         break;
                    //     }

                    //     System.out.println(
                    //         node.at("/videoRenderer/videoId")
                    //     );
                    //     System.out.println(
                    //         node.at("/videoRenderer/title/runs/0/text")
                    //     );
                    //     System.out.println(
                    //         node.at("/videoRenderer/publishedTimeText/simpleText")
                    //     );
                    //     System.out.println(
                    //         node.at("/videoRenderer/lengthText/simpleText")
                    //     );
                    //     System.out.println(
                    //         node.at("/videoRenderer/shortViewCountText/simpleText")
                    //     );
                    //     System.out.println(
                    //         node.at("/videoRenderer/ownerText/runs/0/text")
                    //     );
                    //     System.out.println(
                    //         node.at("/videoRenderer/ownerText/runs/0/navigationEndpoint/commandMetadata/webCommandMetaData/url")
                    //     );
                    //     System.out.println(
                    //         node.at("/videoRenderer/thumbnail/thumbnails/0/url")
                    //     );
                    //     System.out.println("-------------------------------------------");
                    // }


                    //     System.out.println(
                    //         node.at("/videoRenderer/videoId")
                    //     );
                    //     System.out.println(
                    //         node.at("/videoRenderer/title/runs/0/text")
                    //     );
                    //     System.out.println(
                    //         node.at("/videoRenderer/publishedTimeText/simpleText")
                    //     );
                    //     System.out.println(
                    //         node.at("/videoRenderer/lengthText/simpleText")
                    //     );
                    //     System.out.println(
                    //         node.at("/videoRenderer/shortViewCountText/simpleText")
                    //     );
                    //     System.out.println(
                    //         node.at("/videoRenderer/ownerText/runs/0/text")
                    //     );
                    //     System.out.println(
                    //         node.at("/videoRenderer/ownerText/runs/0/navigationEndpoint/commandMetadata/webCommandMetaData/url")
                    //     );
                    //     System.out.println(
                    //         node.at("/videoRenderer/thumbnail/thumbnails/0/url")
                    //     );
                            