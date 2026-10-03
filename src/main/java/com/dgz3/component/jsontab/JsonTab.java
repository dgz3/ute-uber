package com.dgz3.component.jsontab;

import com.dgz3.component.jsontab.component.JsonFileChooserButton;

import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class JsonTab {

    public String DEBUG_BORDER(String color)
    {
        String format = "-fx-border-color: %s; -fx-border-width: 1; -fx-border-style: solid;";
        return(
            String.format( format, color )
        );
    }

    private final String TAB_NAME = "JSON";

    private Tab tab;
    private JsonFileChooserButton jsonFileSelector;

    public JsonTab(Stage stage)
    {
        jsonFileSelector = new JsonFileChooserButton(stage);

        TabPane pt = new TabPane(
                new Tab("1")
                ,new Tab("2")
                ,new Tab("3")
                ,new Tab("4")
                ,new Tab("5")
                ,new Tab("6")
                ,new Tab("7")
                ,new Tab("8")
                ,new Tab("9")
                ,new Tab("10")
                ,new Tab("1")
                ,new Tab("2")
                ,new Tab("3")
                ,new Tab("4")
                ,new Tab("5")
                ,new Tab("6")
                ,new Tab("7")
                ,new Tab("8")
                ,new Tab("9")
                ,new Tab("10")
                ,new Tab("1")
                ,new Tab("2")
                ,new Tab("3")
                ,new Tab("4")
                ,new Tab("5")
                ,new Tab("6")
                ,new Tab("7")
                ,new Tab("8")
                ,new Tab("9")
                ,new Tab("10")
                ,new Tab("1")
                ,new Tab("2")
                ,new Tab("3")
                ,new Tab("4")
                ,new Tab("5")
                ,new Tab("6")
                ,new Tab("7")
                ,new Tab("8")
                ,new Tab("9")
                ,new Tab("10")
                ,new Tab("2")
                ,new Tab("3")
                ,new Tab("4")
                ,new Tab("5")
                ,new Tab("6")
                ,new Tab("7")
                ,new Tab("8")
                ,new Tab("9")
                ,new Tab("10")
                ,new Tab("1")
                ,new Tab("2")
                ,new Tab("3")
                ,new Tab("4")
                ,new Tab("5")
                ,new Tab("6")
                ,new Tab("7")
                ,new Tab("8")
                ,new Tab("9")
                ,new Tab("10")
                ,new Tab("1")
                ,new Tab("2")
                ,new Tab("3")
                ,new Tab("4")
                ,new Tab("5")
                ,new Tab("6")
                ,new Tab("7")
                ,new Tab("8")
                ,new Tab("9")
                ,new Tab("10")
                ,new Tab("1")
                ,new Tab("2")
                ,new Tab("3")
                ,new Tab("4")
                ,new Tab("5")
                ,new Tab("6")
                ,new Tab("7")
                ,new Tab("8")
                ,new Tab("9")
                ,new Tab("10")
            );
            pt.getTabs().add(new Tab("new tab test"));

        VBox vbox = new VBox(

            pt

        );

        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(true);
        vbox.setStyle(DEBUG_BORDER("red"));

        tab = new Tab(TAB_NAME);
        tab.setClosable(false);
        tab.setContent(vbox);
    }

    public Tab getTab() { return tab; }
}

        // HBox hbox = new HBox(
        //     new Button("button1")
        //     ,new Button("button2")
        //     ,new Button("button3")
        // );
        // hbox.setAlignment(Pos.CENTER);
        // hbox.setStyle(DEBUG_BORDER("green"));

        // Button btn = new Button("button4");
        // btn.setStyle("-fx-max-width: infinity;");

        // FileInputStream input = new FileInputStream(selectedFile);