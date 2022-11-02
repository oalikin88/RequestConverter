package com.mycompany.requestconverter;

import com.mycompany.requestconverter.data.ClientDAO;
import com.mycompany.requestconverter.data.Record;
import com.mycompany.requestconverter.data.DataHistory;
import com.mycompany.requestconverter.data.Request;
import com.mycompany.requestconverter.service.Content;
import com.mycompany.requestconverter.service.ConvertList;
import com.mycompany.requestconverter.service.CustomListManipulation;
import com.mycompany.requestconverter.service.DateComareList;
import com.mycompany.requestconverter.service.RequestFormirovationService;
import com.mycompany.requestconverter.service.ZipFileService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private MenuItem getUpdate;

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
    private Label statusBarInfo;

    @FXML
    private ChoiceBox<String> upfr;

    private String fName;
    private List<DataHistory> sprs;
    private List<DataHistory> requests;
    private List<Record> recordList;
    private List<Request> requestsList;
    private int compareDataHistoryList1;
    private int compareDataHistoryList2;
    private List<String> list;
    private List<String> requestList;
    ObservableList<String> oList;
    List<Record> opfrList;
    List<Record> records;
    String[][] array;
    List<String> rList;
    ObservableList<String> requestsObserverableList;

    @FXML
    void initialize() throws IOException, ParseException {

        //Group root = new Group();
        // инициализация списков
        list = Content.getContent();
        requestList = Content.getRequests();
        ClientDAO sprHistoryClient = new ClientDAO();
        ClientDAO requestHistoryClient = new ClientDAO();
        sprs = sprHistoryClient.getLastChangeFromSpr();
        requests = requestHistoryClient.getLastChangeFromRequest();
        System.out.println(sprs);
        System.out.println("*****************");
        System.out.println(requests);
        System.out.println("*****************");
        List<String> sprHistoryStrings = Content.getSprHistoryContent();
        List<String> requestHistoryStrings = Content.getRequestHistoryContent();
        ConvertList convertSprList = new ConvertList();
        ConvertList convertRequestList = new ConvertList();
        List<DataHistory> sprHistory = convertSprList.getDataHistory(sprHistoryStrings);
        List<DataHistory> requestHistory = convertRequestList.getDataHistory(requestHistoryStrings);
        System.out.println(sprHistory);
        compareDataHistoryList1 = DateComareList.compareDataHistoryList(sprs, sprHistory);
        compareDataHistoryList2 = DateComareList.compareDataHistoryList(requests, requestHistory);
        System.out.println(requestHistory);

        if (compareDataHistoryList1 == -1 || compareDataHistoryList2 == -1) {
            statusBarInfo.setText("Требуется обновить базу данных");
            Dialog<ButtonType> dialog = new Dialog<>();
            DialogPane dialogPane = dialog.getDialogPane();
            dialog.setTitle("Сообщение");
            dialog.setHeaderText("Доступно обновление базы данных");
            dialogPane.setContentText("Вы желаете обновить базу данных?");
              dialog.getDialogPane().getButtonTypes().addAll(
                    new ButtonType("Да", ButtonBar.ButtonData.OK_DONE),
                    new ButtonType("Нет", ButtonBar.ButtonData.CANCEL_CLOSE));

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent()) {
                if (result.orElseThrow().getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    getUpdate.fire();
                }
                }

        } else {
            statusBarInfo.setText("Готов к работе");
        }

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
        rList = CustomListManipulation.getRequestList(requestList);

        requestsObserverableList = FXCollections.observableArrayList(rList);
        requestFile.setItems(requestsObserverableList);
        requestFile.setValue(requestsObserverableList.get(0));

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
        array = ConvertList.listToTwoArray(list);
        // получение списка ОПФР из общего массива
        records = CustomListManipulation.getRecords(array);

        opfrList = CustomListManipulation.getOpfr(records);
        oList = FXCollections.observableArrayList(opfrList.stream().map(e -> e.getName()).collect(Collectors.toList()));
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

        // Валидация фамилии
//        surname.focusedProperty().addListener((arg0, oldValue, newValue) -> {
//            if (!newValue) { //when focus lost
//            if(!surname.getText().matches("[1-5]\\.[0-9]|6\\.0")){
//                //when it not matches the pattern (1.0 - 6.0)
//                //set the textField empty
//                surname.setText("");
//            }
//        }
//        });

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

        start.setOnAction(event -> {

            // инициализация имени
            String fName = firstName.getText();

            // инициализация отчества
            String fathName = fathersName.getText();
            // инициализация фамилии
            String sName = surname.getText();

            // Ошибка если поля с ФИО пустые - доработать и вынести в отдельный метод
//            if (fName.isEmpty() || fathName.isEmpty() || sName.isEmpty()) {
//                Alert alert = new Alert(AlertType.WARNING);
//                alert.setTitle("Ошибка");
//                alert.setHeaderText("Осталось незаполенное поле");
//                alert.setContentText("Careful with the next step!");
//                alert.showAndWait();
//                return;
//            }
            // Получение кода района
            String val = RequestFormirovationService.getRequestCode(array, upfr.getValue());

            // Получение пути сохранения файла
            String str = showFileSavePath.getText() + "\\";

            StringBuilder out = new StringBuilder();
            out.delete(0, out.length());

            out.append(str);
            out.append(val);
            out.append("_");
            if(remember.selectedProperty().getValue()) {
                out.append("(н)");
            }
            out.append(RequestFormirovationService.getRequestValue(requestList, requestFile.getValue()));
            out.append("_");
            out.append(sName);
            out.append(" ");
            out.append(fName);
            out.append(".");
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

    }

    @FXML
    void submit(ActionEvent event) throws InterruptedException {

    }

    @FXML
    void actionGetUpdate(ActionEvent event) throws IOException {

        if (compareDataHistoryList1 == -1) {
            Content.eraseSprData();
            Content.eraseSprHistory();
            ClientDAO client = new ClientDAO();
            recordList = client.findAllRecords();
            Content.writeSprData(recordList);
            Content content = new Content();
            content.writeSprHistory(sprs);
            statusBarInfo.setText("База данных успешно обновлена");
            list = Content.getContent();
            array = ConvertList.listToTwoArray(list);
            records = CustomListManipulation.getRecords(array);
            opfrList = CustomListManipulation.getOpfr(records);
            oList = FXCollections.observableArrayList(opfrList.stream().map(e -> e.getName()).collect(Collectors.toList()));
            opfr.setItems(oList);
            opfr.setValue(oList.get(0));
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    statusBarInfo.setText("Готов к работе.");
                }
            }, 500);
            
        }

        if (compareDataHistoryList2 == -1) {
            Content.eraseRequestData();
            Content.eraseRequestHistory();
            ClientDAO client = new ClientDAO();
            requestsList = client.findAllRequests();
            Content.writeRequestData(requestsList);
            Content content = new Content();
            content.writeRequestHistory(requests);
            statusBarInfo.setText("База данных успешно обновлена");
            requestList = Content.getRequests();
            rList = CustomListManipulation.getRequestList(requestList);
            requestsObserverableList = FXCollections.observableArrayList(rList);
            requestFile.setItems(requestsObserverableList);
            requestFile.setValue(requestsObserverableList.get(0));
             Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    statusBarInfo.setText("Готов к работе.");
                }
            }, 500);
        }

    }

}
