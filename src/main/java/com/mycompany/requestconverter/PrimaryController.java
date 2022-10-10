package com.mycompany.requestconverter;

import com.mycompany.requestconverter.service.Content;
import com.mycompany.requestconverter.service.ConvertList;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PrimaryController {

   @FXML
    private ResourceBundle resources;
    
    @FXML
    private URL location;
    
    @FXML
    private Button saveFilePath;
    
    @FXML
    private MenuItem about;
    
    @FXML
    private MenuItem checkUpdate;
    
    @FXML
    private Button choiceFile;
    
    @FXML
    private TextField fathersName;
    
    @FXML
    private Label fileNameView;
    
    @FXML
    private Label showFileSavePath;
    
    @FXML
    private TextField firstName;
    
    @FXML
    private MenuItem instruction;
    
    @FXML
    private MenuBar menu;
    
    @FXML
    private MenuItem menuCloseButton;
    
    @FXML
    private Menu menuFile;
    
    @FXML
    private ChoiceBox<String> opfr;
    
    @FXML
    private CheckBox remember;
    
    @FXML
    private ChoiceBox<?> requestFile;
    
    @FXML
    private Button start;
    
    @FXML
    private VBox statusBar;
    
    @FXML
    private TextField surname;
    
    @FXML
    private ChoiceBox<?> upfr;
    
    @FXML
    void initialize() throws IOException {
        
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));
        
        Content content = new Content();
        List<String> list = content.getContent();
        
        choiceFile.setOnAction(event -> {
            
            File selectedFile = fileChooser.showOpenDialog(stage);
            fileNameView.setText(selectedFile.getAbsolutePath());
            System.out.println("Всё работает!");
        });
        
        saveFilePath.setOnAction(event -> {
            File selectedDirectory = directoryChooser.showDialog(stage);
            showFileSavePath.setText(selectedDirectory.getAbsolutePath());
        });
        
        ObservableList<String> oList = FXCollections.observableArrayList(list);
        
        opfr.setItems(oList);
        opfr.setValue(oList.get(0));
        opfr.setOnAction(event -> {
            String [][] array = ConvertList.listToTwoArray(list);
            for(int i = 0; i < array.length; i++) {
                for(int j = 0; j < 4; j++) {
                    System.out.println(array[i][j]);
                }
            }
        });
        
        assert saveFilePath != null : "fx:id=\"SaveFilePath\" as not injected: check your FXML file 'primary.fxml'.";
        assert about != null : "fx:id=\"about\" was not injected: check your FXML file 'primary.fxml'.";
        assert checkUpdate != null : "fx:id=\"checkUpdate\" was not injected: check your FXML file 'primary.fxml'.";
        assert choiceFile != null : "fx:id=\"choiceFile\" was not injected: check your FXML file 'primary.fxml'.";
        assert fathersName != null : "fx:id=\"fathersName\" was not injected: check your FXML file 'primary.fxml'.";
        assert fileNameView != null : "fx:id=\"fileNameView\" was not injected: check your FXML file 'primary.fxml'.";
        assert firstName != null : "fx:id=\"firstName\" was not injected: check your FXML file 'primary.fxml'.";
        assert instruction != null : "fx:id=\"instruction\" was not injected: check your FXML file 'primary.fxml'.";
        assert menu != null : "fx:id=\"menu\" was not injected: check your FXML file 'primary.fxml'.";
        assert menuCloseButton != null : "fx:id=\"menuCloseButton\" was not injected: check your FXML file 'primary.fxml'.";
        assert menuFile != null : "fx:id=\"menuFile\" was not injected: check your FXML file 'primary.fxml'.";
        assert opfr != null : "fx:id=\"opfr\" was not injected: check your FXML file 'primary.fxml'.";
        assert remember != null : "fx:id=\"remember\" was not injected: check your FXML file 'primary.fxml'.";
        assert requestFile != null : "fx:id=\"requestFile\" was not injected: check your FXML file 'primary.fxml'.";
        assert start != null : "fx:id=\"start\" was not injected: check your FXML file 'primary.fxml'.";
        assert statusBar != null : "fx:id=\"statusBar\" was not injected: check your FXML file 'primary.fxml'.";
        assert surname != null : "fx:id=\"surname\" was not injected: check your FXML file 'primary.fxml'.";
        assert upfr != null : "fx:id=\"upfr\" was not injected: check your FXML file 'primary.fxml'.";
        
    }
}
