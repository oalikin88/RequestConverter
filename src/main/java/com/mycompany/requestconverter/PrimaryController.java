package com.mycompany.requestconverter;

import com.mycompany.requestconverter.data.Record;
import com.mycompany.requestconverter.service.Content;
import com.mycompany.requestconverter.service.ConvertList;
import com.mycompany.requestconverter.service.CustomListManipulation;
import com.mycompany.requestconverter.service.RequestFormirovationService;
import com.mycompany.requestconverter.service.Validator;
import com.mycompany.requestconverter.service.ZipFileService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.Style;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private Label invalidDetails;

    @FXML
    private ChoiceBox<String> upfr;
    
    
    private String fName;
    

  
    
    @FXML
    void initialize() throws IOException {

        //Group root = new Group();
        // инициализация списков
        List<String> list = Content.getContent();
        List<String> requestList = Content.getRequests();

        // инициализация кнопок открыть файл и сохранить файл
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл для конвертирования");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/desktop"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Все файлы", "*.*"));
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите директорию куда сохранить файл");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/desktop"));

        // Обработка requestList 
        List<String> rList = CustomListManipulation.getRequestList(requestList);
        
        ObservableList<String> requests = FXCollections.observableArrayList(rList);
        requestFile.setItems(requests);
        requestFile.setValue(requests.get(0));
       
       
       
        
        choiceFile.setOnAction(event -> {
              

            File selectedFile = fileChooser.showOpenDialog(stage);
            fileNameView.setText(selectedFile.getAbsolutePath());
            fileNameView.setTooltip(new Tooltip(selectedFile.getAbsolutePath()));
        });

        saveFilePath.setOnAction(event -> {
            File selectedDirectory = directoryChooser.showDialog(stage);
            showFileSavePath.setText(selectedDirectory.getAbsolutePath());
            showFileSavePath.setTooltip(new Tooltip(selectedDirectory.getAbsolutePath()));

        });

        menuCloseButton.setOnAction(event -> {
            Platform.exit();
        });

        // инициализация двумерного массива из списка spr
        String[][] array = ConvertList.listToTwoArray(list);
        // получение списка ОПФР из общего массива
        List<Record> records = CustomListManipulation.getRecords(array);
        
        List<Record> opfrList = CustomListManipulation.getOpfr(records);
        ObservableList<String> oList = FXCollections.observableArrayList(opfrList.stream().map(e -> e.getName()).collect(Collectors.toList()));
        opfr.setItems(oList);
        opfr.setValue(oList.get(0));

        String element = opfr.getValue();
        // получение списка УПФР из общего массива
        List<Record> upfrRecords = CustomListManipulation.getUpfrList(records, element);
        ObservableList<String> upfrList = FXCollections.observableArrayList(upfrRecords.stream().map(e -> e.getName()).collect(Collectors.toList()));

        upfr.setItems(upfrList);
        upfr.setValue(upfrList.get(0));

        opfr.setOnAction(event -> {
            String element2 = opfr.getValue();
            // получение списка упфр при смене элемента в choicebox ОПФР
            List<Record> target = CustomListManipulation.getUpfrList(records, element2);
            ObservableList<String> upfrList2 = FXCollections.observableArrayList(target.stream().map(e -> e.getName()).collect(Collectors.toList()));
            upfr.setItems(upfrList2);
            upfr.setValue(upfrList2.get(0));
        }
        );
        //root.getChildren().add(surname);
        surname.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                start.fire();
            }
        });

        firstName.textProperty().addListener(new ChangeListener<String>() {
            int maxLength = 1;

            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (firstName.getText().length() > maxLength) {
                    String s = firstName.getText().substring(0, maxLength);
                    firstName.setText(s);
                }
            }
        });

        firstName.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                start.fire();
            }
        });
        
        fathersName.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                start.fire();
            }
        });
        fathersName.textProperty().addListener(new ChangeListener<String>() {
            int maxLength = 1;

            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (fathersName.getText().length() > maxLength) {
                    String s = fathersName.getText().substring(0, maxLength);
                    fathersName.setText(s);
                }
            }
        });
        
        
//        start.setOnAction(event -> {
//
//            // инициализация имени
//            String fName = "";
//            
//            // инициализация отчества
//            String fathName = fathersName.getText();
//            // инициализация фамилии
//            String sName = surname.getText();
//
//            // Ошибка если поля с ФИО пустые - доработать и вынести в отдельный метод
////            if (fName.isEmpty() || fathName.isEmpty() || sName.isEmpty()) {
////                Alert alert = new Alert(AlertType.WARNING);
////                alert.setTitle("Ошибка");
////                alert.setHeaderText("Осталось незаполенное поле");
////                alert.setContentText("Careful with the next step!");
////                alert.showAndWait();
////                return;
////            }
//
//            // Получение кода района
//            String val = RequestFormirovationService.getRequestCode(array, upfr.getValue());
//
//            // Получение пути сохранения файла
//            String str = showFileSavePath.getText() + "\\";
//
//            StringBuilder out = new StringBuilder();
//            out.delete(0, out.length());
//
//            out.append(str);
//            out.append(val);
//            out.append("_");
//            out.append(RequestFormirovationService.getRequestValue(requestList, requestFile.getValue()));
//            out.append("_");
//            out.append(sName);
//            out.append(" ");
//            out.append(fName);
//            out.append(". ");
//            out.append(fathName);
//            out.append(".zip");
//            System.out.println(out);
//
////            try {
////
////                Path path = Path.of(fileNameView.getText());
////                ZipFileService.zipSingleFile(path, out.toString());
////                Alert alert = new Alert(AlertType.INFORMATION);
////                alert.setTitle("Сообщение");
////                alert.setHeaderText(null);
////                alert.setContentText("Запрос успешно сконвертирован");
////
////                alert.showAndWait();
////
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//
//        });

 
    }
    
  @FXML
    void submit(ActionEvent event) throws InterruptedException {
            
      
        TextField buf = null;
        
        try{
            
          TextField[] array = {firstName, surname, fathersName};
        for(TextField s : array) {
            if(s.getText().isBlank() || s.getText().isEmpty()) {
                buf = s;
                throw new NullPointerException();
            }
        
        }
        }
        catch(NullPointerException e) {    
                //surname.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0, 0.7), new CornerRadii(5.0), Insets.EMPTY)));
                
                Validator.startWork(buf);
                invalidDetails.setText("Заполните поле \"Имя\"");
                invalidDetails.setTextFill(Paint.valueOf(Color.RED.toString()));
                 FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(900), invalidDetails);   
                fadeOutTransition.setFromValue(1.0);
                fadeOutTransition.setToValue(0.0);
                fadeOutTransition.play();            
        
    }
        
        
     System.out.println(fName);
    }
   

}
