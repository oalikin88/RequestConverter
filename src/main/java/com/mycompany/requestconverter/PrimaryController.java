package com.mycompany.requestconverter;

import com.mycompany.requestconverter.service.Content;
import com.mycompany.requestconverter.service.ConvertList;
import com.mycompany.requestconverter.service.CustomtListManipulation;
import com.mycompany.requestconverter.service.RequestFormirovationService;
import com.mycompany.requestconverter.service.ZipFileService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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

        // инициализация списков
        List<String> list = Content.getContent();
        List<String> requestList = Content.getRequests();

        // инициализация кнопок открыть файл и сохранить файл
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл для конвертирования");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/desktop"));
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите директорию куда сохранить файл");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/desktop"));

        StringBuilder sb = new StringBuilder();

        // Обработка requestList 
        List<String> rList = CustomtListManipulation.getRequestList(requestList);

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

        // инициализация двумерного массива из списка spr
        String[][] array = ConvertList.listToTwoArray(list);
        // получение списка ОПФР из общего массива
        List<String> opfrList = CustomtListManipulation.getOpfrList(array);
        ObservableList<String> oList = FXCollections.observableArrayList(opfrList);
        opfr.setItems(oList);
        opfr.setValue(oList.get(0));

        int element = opfrList.indexOf(opfr.getValue());
        // получение списка УПФР из общего массива
        ObservableList<String> upfrList = FXCollections.observableArrayList(CustomtListManipulation.getUpfrList(array, element));

        upfr.setItems(upfrList);
        upfr.setValue(upfrList.get(0));
        opfr.setOnAction(event -> {
            // получение списка упфр при смене элемента в choicebox ОПФР
            List<String> target = CustomtListManipulation.getChangeUpfrList(array, opfr.getValue());
            ObservableList<String> upfrList2 = FXCollections.observableArrayList(target);
            upfr.setItems(upfrList2);
            upfr.setValue(upfrList2.get(0));

        }
        );

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

        start.setOnAction(event -> {

            // инициализация имени
            String fName = firstName.getText();
            // инициализация отчества
            String fathName = fathersName.getText();
            // инициализация фамилии
            String sName = surname.getText();

            // Ошибка если поля с ФИО пустые - доработать и вынести в отдельный метод
            if (fName.isEmpty() || fathName.isEmpty() || sName.isEmpty()) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Осталось незаполенное поле");
                alert.setContentText("Careful with the next step!");
                alert.showAndWait();
                return;
            }

            // Получение кода района
            String val = RequestFormirovationService.getRequestCode(array, upfr.getValue());

            // Получение пути сохранения файла
            String str = showFileSavePath.getText() + "\\";

            StringBuilder out = new StringBuilder();
            out.delete(0, out.length());

            out.append(str);
            out.append(val);
            out.append("_");
            out.append(RequestFormirovationService.getRequestValue(requestList, requestFile.getValue()));
            out.append("_");
            out.append(sName);
            out.append(" ");
            out.append(fName);
            out.append(". ");
            out.append(fathName);
            out.append(".zip");
            System.out.println(out);

            try {

                Path path = Path.of(fileNameView.getText());
                ZipFileService.zipSingleFile(path, out.toString());
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Сообщение");
                alert.setHeaderText(null);
                alert.setContentText("Запрос успешно сконвертирован");

                alert.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        System.out.println("Done");

    }
}
