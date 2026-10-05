package com.dgz3.component.jsontab;

import java.io.File;

import com.dgz3.component.jsontab.component.JsonFileChooserButton;
import com.dgz3.pattern.Observer;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class JsonTab extends Tab implements Observer
{
    private static final String TAB_NAME = "JSON";

    private final Stage stage;
    private final TabPane tabPane;
    private Label fileLabel;
    private TextField fileTextField;
    private JsonFileChooserButton jsonFileSelector;

    private ObservableList<Tab> tabs;

    public JsonTab(Stage _stage)
    {
        super(TAB_NAME);
        stage = _stage;
        tabPane = new TabPane( createNewTab() );

        this.setClosable(false);
        this.setContent(tabPane);
        tabs = tabPane.getTabs();
    }

    private Tab createNewTab()
    {
        Tab newTab = new Tab("New Tab");

        Text gridTitle = new Text("Select a file");
        gridTitle.setFont(Font.font("Ariel", FontWeight.NORMAL, 20));

        fileLabel = new Label("File selected");

        fileTextField = new TextField();
        fileTextField.setDisable(true);

        jsonFileSelector = new JsonFileChooserButton(stage);
        jsonFileSelector.addObserver(this);

        HBox buttonBox = new HBox(
            jsonFileSelector,new Button("Load")
        );
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setSpacing(10);

        GridPane selectorGridPane = new GridPane();
        selectorGridPane.setAlignment(Pos.CENTER);
        selectorGridPane.setHgap(10);
        selectorGridPane.setVgap(10);
        selectorGridPane.setPadding(new Insets(5,5,5,5));
        // selectorGridPane.setGridLinesVisible(true);                             /* debug */ 

        selectorGridPane.add(gridTitle, 0, 0, 2, 1);
        selectorGridPane.add(fileLabel, 0, 1);
        selectorGridPane.add(fileTextField, 1, 1);
        selectorGridPane.add(
            buttonBox,1,2
        );

        newTab.setClosable(false);
        newTab.setContent(
            selectorGridPane
        );
        return(newTab);
    }

    @Override 
    public void update(Object o)
    {
        if (o != null){
            fileTextField.setText(((File)o).getName());
        }
    }

    public void addNewTab() 
    { 
        tabs.add( createNewTab() ); 
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