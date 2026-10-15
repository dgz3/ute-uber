package com.dgz3.controllers.tabjson;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;

public class TabJsonController 
{
    @FXML
    private TabPane tabPane;

    private final String FXML_SUB_TAB_CONTENT_PATH 
        = "/com/dgz3/views/tabjson/sub-tab-content.fxml";

    @FXML
    public void initialize()
    {
        createNewTab();
    }

    private void createNewTab()
    {
        System.out.println(getClass().getResource(FXML_SUB_TAB_CONTENT_PATH));

        FXMLLoader loader = new FXMLLoader(
            getClass().getResource(FXML_SUB_TAB_CONTENT_PATH)
        );

        GridPane content = null; 
        SubTabContentController subTabContentController = null;
        try {
            content = loader.load();
            subTabContentController = loader.getController();
        } catch(Exception e) {
            System.err.printf(
                "[Error <%s>] Could not load fxml file %s.\n\tSee message: %s\n"
                ,e.getClass()
                ,FXML_SUB_TAB_CONTENT_PATH
                ,e.getMessage()
            );
        }

        if (content != null){
            subTabContentController.setOnLoadCreateNewTab( () -> {
                createNewTab();
            });
            Tab newTab = new Tab("New Tab");
            newTab.setClosable(false);
            newTab.setContent(content);
            tabPane.getTabs().add(newTab);
        }
    }
}
