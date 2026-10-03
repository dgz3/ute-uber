package com.dgz3;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.dgz3.component.jsontab.JsonTab;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class UberUte extends Application {

    @Override
    public void start(Stage stage) {

        /*
        TODO initialize DB connection
         */


        TabPane tabPane = new TabPane(
            /* return spec tabs here */
            /*
            1. mp3
            2. mp4
            3. json
            4. rss
            5. ???
             */
            new JsonTab(stage).getTab()
        );

        /* TODO: add commandline params */
        stage.setMinHeight(300);
        stage.setMinWidth(400);

        stage.setScene(new Scene(tabPane));
        stage.show();
    }

    public static void main(String[] args) {

        /* TODO parse args */

        launch();
    }

    void runError()
    {
        Properties buildProps = new Properties();
        try(InputStream input = getClass().getResourceAsStream("/build.properties"))
        {
            buildProps.load(input);

            System.out.println(
                buildProps.getProperty("app.name")
            );
        }catch(Exception exception){
            System.err.printf(
                "Error [%s] : %s\n"
                ,exception.getClass()
                ,exception.getMessage()
            );
        }
    }
}



/*
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var btn = new Button("httpclient");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override 
            public void handle(ActionEvent actionEvent) {
                HttpClient httpClient = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.youtube.com/results?search_query=mr+brightside+the+killers"))
                    .GET()
                    .build();

                try {
                    HttpResponse<String> resp = httpClient.send(request,HttpResponse.BodyHandlers.ofString());

                    System.out.println(resp.statusCode());

                    Document doc = Jsoup.parse(resp.body());

                    Element elem = doc.selectFirst("script[nonce]:containsData(ytInitialData)");

                    System.out.println("hello 1");

                    if (elem != null) {
                        System.out.println("hello 2");
                        // System.out.println(elem.data().substring(index,50));

                        ObjectMapper mapper = new ObjectMapper();
                        int index = elem.data().indexOf("{");               // strip js 'var x = ' 
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

                                nodeRoot = node.path("videoRenderer");
                                if(!nodeRoot.isMissingNode()){
                                    System.out.println(
                                        nodeRoot
                                        .path("title")
                                        .path("runs")
                                        .get(0)
                                        .path("text")
                                        .toString()
                                    );
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



                            // int nodeCount=0;
                            // for( var node : contents){



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

                        }

                    }

                } catch (Exception exception) {
                    System.out.println(exception.getClass());
                    System.out.println(exception.getMessage());
                }
            }
        });

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label,btn), 640, 480);
        stage.setScene(scene);
        stage.show();

*/