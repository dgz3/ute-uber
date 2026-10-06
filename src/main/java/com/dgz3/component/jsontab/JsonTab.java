package com.dgz3.component.jsontab;

import com.dgz3.component.jsontab.component.JsonFileTab;
import com.dgz3.pattern.Observer;

import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class JsonTab extends Tab implements Observer
{
    private static final String TAB_NAME = "JSON";
    private final Stage stage;
    private final TabPane tabPane;
    private ObservableList<Tab> tabs;

    public JsonTab(Stage _stage)
    {
        super(TAB_NAME);
        stage = _stage;

        var newTab = new JsonFileTab(stage);
        newTab.addObserver(this);
        tabPane = new TabPane(newTab);

        this.setClosable(false);
        this.setContent(tabPane);
        tabs = tabPane.getTabs();
    }

    @Override 
    public void update(Object o)
    {
        var newTab = new JsonFileTab(stage);
        newTab.addObserver(this);
        tabs.add(newTab);
    }

    public String DEBUG_BORDER(String color)
    {
        String format = 
            "-fx-border-color: %s;"+ 
            "-fx-border-width: 1;" +
            "-fx-border-style: solid;";
        return(
            String.format( format, color )
        );
    }
}