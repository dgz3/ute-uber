package com.dgz3.controllers.tabjson;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SubTabContentController 
{
    @FXML 
    private GridPane subGridPane;

    @FXML
    private TextField fileTextField;

    @FXML 
    private Button fileChooserButton;

    @FXML 
    private Button fileLoaderButton;

    private Runnable onLoadCreateNewTab;

    public void setOnLoadCreateNewTab(Runnable callback)
    {
        this.onLoadCreateNewTab = callback;
    }

/* This was originally for debugging new tabs; delete later
    @FXML 
    private void createNewTab()
    {
        onLoadCreateNewTab.run();
    } */

    @FXML 
    private void loadFile()
    {
        setTabToClosable();
        onLoadCreateNewTab.run();
    }

    private void setTabToClosable()
    {
        Parent parent = subGridPane.getParent();
        while( (parent != null) && !(parent instanceof TabPane) ){
            parent = parent.getParent();
        }

        if(parent instanceof TabPane){
            TabPane tabPane = (TabPane) parent;
            Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
            if(currentTab != null){
                currentTab.setClosable(true);
            }
        }
    }
}
