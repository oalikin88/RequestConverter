package com.mycompany.requestconverter;

import com.mycompany.requestconverter.service.Content;
import com.mycompany.requestconverter.service.ConvertList;
import com.mycompany.requestconverter.service.UpfrList;
import com.mycompany.requestconverter.service.ZipFileService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
    private ChoiceBox<String> requestFile;

    @FXML
    private Button start;

    @FXML
    private VBox statusBar;

    @FXML
    private TextField surname;

    @FXML
    private ChoiceBox<String> upfr;

    @FXML
    void initialize() throws IOException {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));
        StringBuilder str = new StringBuilder();
        Content content = new Content();
        List<String> list = content.getContent();
        List<String> requestList = content.getRequests();
        
        List<String> rList = new ArrayList<>();
        for (String s : requestList) {
            int i = s.indexOf(";");
            rList.add(s.substring(0, i));
        }
         ObservableList<String> requests = FXCollections.observableArrayList(rList);
        requestFile.setItems(requests);
        requestFile.setValue(requests.get(0));
        StringBuilder sb = new StringBuilder();
        requestFile.setOnAction(event -> {
            sb.delete(0, sb.capacity());
            sb.append(requestFile.getValue());
            for (String s : requestList) {
                int i = s.indexOf(";");
                if(s.substring(0, i).equals(sb.toString())) {
                    
                    sb.delete(0, sb.capacity());
                    sb.append(s.substring(++i, s.length()));
                }
        }
        });
        
        
        
        choiceFile.setOnAction(event -> {

            File selectedFile = fileChooser.showOpenDialog(stage);
            fileNameView.setText(selectedFile.getAbsolutePath());
            fileNameView.setTooltip(new Tooltip(selectedFile.getAbsolutePath()));
        });
        
        

        saveFilePath.setOnAction(event -> {
            File selectedDirectory = directoryChooser.showDialog(stage);
            showFileSavePath.setText(selectedDirectory.getAbsolutePath());
            showFileSavePath.setTooltip(new Tooltip(selectedDirectory.getAbsolutePath()));
            str.append(showFileSavePath.getText());
        });

       

        String[][] array = ConvertList.listToTwoArray(list);
        List<String> opfrList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 2 && array[i][j].equals("000")) {
                    opfrList.add(array[i][3]);

                }
            }
        }

        ObservableList<String> oList = FXCollections.observableArrayList(opfrList);
        opfr.setItems(oList);
        opfr.setValue(oList.get(0));
        String value = opfr.getValue();

        int element = opfrList.indexOf(opfr.getValue());

        ObservableList<String> upfrList = FXCollections.observableArrayList(UpfrList.getUpfrList(array, element));

        for (int i = element; i <= element; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println(array[i][j]);
            }
        }

        upfr.setItems(upfrList);
        upfr.setValue(upfrList.get(0));
        StringBuilder val = new StringBuilder();
        opfr.setOnAction(event -> {
            int b = opfrList.indexOf(opfr.getValue());
            String s = (opfr.getValue());
            String buf = "";
            List<String> target = new ArrayList<>();
            for (int i = 0; i < array.length; i++) {
                if (array[i][3].equals(s)) {
                    buf = array[i][0];
                }
            }
            for (int i = 0; i < array.length; i++) {
                if (array[i][0].equals(buf) && !array[i][2].equals("000")) {

                    target.add(array[i][3]);
                }
            }

            ObservableList<String> upfrList2 = FXCollections.observableArrayList(target);
            upfr.setItems(upfrList2);
            upfr.setValue(upfrList2.get(0));
              
     
        }
        );
      
        upfr.setOnAction(event -> {
            val.delete(0, val.capacity());
               for(int i = 0; i < array.length; i++) {
            for(int j = 0; j < 4; j++) {
                if(array[i][j].equals(upfr.getValue())) {
                    val.append(array[i][j-3]);
                    val.append(array[i][j-2]);
                    val.append(array[i][j-1]);
                    
                }
            }
        }
        });
        
        start.setOnAction(event -> {
            try {

                Path path = Path.of(fileNameView.getText());
                ZipFileService.zipSingleFile(path, "outFile.zip");

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(upfr.getValue());
            System.out.println(str);
            System.out.println(val);
            System.out.println(sb);
            System.out.println("Done");

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
