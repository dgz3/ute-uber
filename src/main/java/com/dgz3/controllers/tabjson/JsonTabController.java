package com.dgz3.controllers.tabjson;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class JsonTabController 
{
    @FXML
    private TabPane tabPane;

    private final String FXML_SUB_TAB_PATH 
        = "/com/dgz3/views/tabjson/sub-tab.fxml";

    @FXML
    public void initialize()
    {
        createNewTab();
    }

    private void createNewTab()
    {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource(FXML_SUB_TAB_PATH)
        );

        Tab content = null; 
        SubTabController subTabController = null;
        try {
            content = loader.load();
            subTabController = loader.getController();
        } catch(Exception e) {
            System.err.printf(
                "[Error <%s>] Could not load fxml file %s.\n\tSee message: %s\n"
                ,e.getClass()
                ,FXML_SUB_TAB_PATH
                ,e.getMessage()
            );
        }

        if (content != null){
            subTabController.setOnLoadCreateNewTab( () -> {
                createNewTab();
            });
            content.setText("New Tab");
            content.setClosable(false);
            tabPane.getTabs().add(content);
        }
    }
}
