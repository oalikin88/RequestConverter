package com.mycompany.requestconverter;

import com.mycompany.requestconverter.connection.DBConnection;
import com.mycompany.requestconverter.data.ClientDAO;
import com.mycompany.requestconverter.data.Record;
import com.mycompany.requestconverter.data.DataHistory;
import com.mycompany.requestconverter.data.Request;
import com.mycompany.requestconverter.data.Settings;
import com.mycompany.requestconverter.service.Content;
import com.mycompany.requestconverter.service.ConvertList;
import com.mycompany.requestconverter.service.CustomListManipulation;
import com.mycompany.requestconverter.service.DateCompareList;
import com.mycompany.requestconverter.service.RequestFormirovationService;
import com.mycompany.requestconverter.service.ZipFileService;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
    private ObservableList<String> oList;
    private List<Record> opfrList;
    private List<Record> records;
    private String[][] array;
    private List<String> rList;
    private ObservableList<String> requestsObserverableList;
    private FileChooser fileChooser;
    private String str;
    private boolean surnameNotNull;
    private boolean firstNameNotNull;
    private boolean fatherNameNotNull;
    private Settings settings;
    private Content content;
    @FXML
    void initialize() throws IOException, URISyntaxException  {
        content = new Content();
        list = content.getContent();
        requestList = content.getRequests();
        Task task = new Task() {
            @Override
            protected Void call() throws Exception {
                
                
                System.out.println("Подключение к базе данных");
                statusBarInfo.setText("Попытка подключения к базе данных");
                DBConnection dBConnection = new DBConnection();
                try (Connection connection = dBConnection.getConnection()) {
                    ClientDAO sprHistoryClient = new ClientDAO();
                    ClientDAO requestHistoryClient = new ClientDAO();
                    sprs = sprHistoryClient.getLastChangeFromSpr();
                    requests = requestHistoryClient.getLastChangeFromRequest();
                    List<String> sprHistoryStrings = content.getSprHistoryContent();
                    List<String> requestHistoryStrings = content.getRequestHistoryContent();
                    ConvertList convertSprList = new ConvertList();
                    ConvertList convertRequestList = new ConvertList();
                    List<DataHistory> sprHistory = convertSprList.getDataHistory(sprHistoryStrings);
                    List<DataHistory> requestHistory = convertRequestList.getDataHistory(requestHistoryStrings);
                    compareDataHistoryList1 = DateCompareList.compareDataHistoryList(sprs, sprHistory);
                    compareDataHistoryList2 = DateCompareList.compareDataHistoryList(requests, requestHistory);

                    if (compareDataHistoryList1 == -1 || compareDataHistoryList2 == -1) {
                        Platform.runLater(() -> statusBarInfo.setText("Требуется обновить базу данных"));
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
                        Platform.runLater(() -> statusBarInfo.setText("Готов к работе"));
                    }

                }  catch(SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
            }
        };

       Platform.runLater(task);
           
        // Обработка requestList            
        rList = CustomListManipulation.getRequestList(requestList);
        requestsObserverableList = FXCollections.observableArrayList(rList);
        requestFile.setItems(requestsObserverableList);
        requestFile.setValue(requestsObserverableList.get(0));
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
        String element = opfr.getItems().get(0);
        // получение списка УПФР из общего массива
        List<Record> upfrRecords = CustomListManipulation.getUpfrList(records, element);
        ObservableList<String> upfrList = FXCollections.observableArrayList(upfrRecords.stream().map(e -> e.getName()).collect(Collectors.toList()));
        upfr.setItems(upfrList);
        upfr.setValue(upfrList.get(0));
        opfr.setOnAction(event -> {
            String element2 = opfr.getItems().get(0);
            // получение списка упфр при смене элемента в choicebox ОПФР
            List<Record> target = CustomListManipulation.getUpfrList(records, element2);
            ObservableList<String> upfrList2 = FXCollections.observableArrayList(target.stream().map(e -> e.getName()).collect(Collectors.toList()));
            upfr.setItems(upfrList2);
            if(upfrList2 != null) {
                upfr.setValue(upfrList2.get(0));
            }
        }
        );

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

        start.disableProperty().bind(
                Bindings.isEmpty(surname.textProperty())
                        .or(Bindings.isEmpty(firstName.textProperty()))
                        .or(Bindings.isEmpty(fathersName.textProperty()))
        );       
    }
   
    @FXML
    void properties(ActionEvent event) {
       
        settings = new Settings();
        Map<String, String> mapSettings = null;
        try {
            mapSettings = settings.prepareSettings();
        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        settings.getSettings(mapSettings);
        Dialog<ButtonType> dialog = new Dialog();
        DialogPane dialogPane = dialog.getDialogPane();
        dialog.setTitle("Настройки");
        dialog.setHeaderText("Редактирование настроек");
        VBox vbox = new VBox();
        dialogPane.setContent(vbox);
        Label labelUrl = new Label("URL сервера");
        TextField fieldUrl = new TextField();
        Label labelPort = new Label("Порт");
        TextField fieldPort = new TextField();
        Label labelDataBaseName = new Label("Название БД");
        TextField fieldDataBaseName = new TextField();
        Label labelUsername = new Label("Имя пользователя");
        TextField fieldUsername = new TextField();
        Label labelPassword = new Label("Пароль");
        TextField fieldPassword = new TextField();
        fieldUrl.setText(settings.getUrl());
        fieldDataBaseName.setText(settings.getDbName());
        fieldUsername.setText(settings.getUsername());
        fieldPassword.setText(settings.getPassword());
        fieldPort.setText(settings.getPort());
        StackPane stackPane1 = new StackPane();
        StackPane stackPane2 = new StackPane();
        StackPane stackPane3 = new StackPane();
        StackPane stackPane4 = new StackPane();
        StackPane stackPane5 = new StackPane();
        StackPane stackPane6 = new StackPane();
        StackPane stackPane7 = new StackPane();
        StackPane stackPane8 = new StackPane();
        StackPane stackPane9 = new StackPane();
        StackPane stackPane10 = new StackPane();
        
        stackPane1.getChildren().add(labelUrl);
        stackPane2.getChildren().add(fieldUrl);
        stackPane3.getChildren().add(labelPort);
        stackPane4.getChildren().add(fieldPort);
        stackPane5.getChildren().add(labelDataBaseName);
        stackPane6.getChildren().add(fieldDataBaseName);
        stackPane7.getChildren().add(labelUsername);
        stackPane8.getChildren().add(fieldUsername);
        stackPane9.getChildren().add(labelPassword);
        stackPane10.getChildren().add(fieldPassword);
        StackPane.setMargin(labelUrl, new Insets(15, 0, 5, 0));
        StackPane.setMargin(labelPort, new Insets(15, 0, 5, 0));
        StackPane.setMargin(labelDataBaseName, new Insets(15, 0, 5, 0));
        StackPane.setMargin(labelUsername, new Insets(15, 0, 5, 0));
        StackPane.setMargin(labelPassword, new Insets(15, 0, 5, 0));
        StackPane.setAlignment(labelUrl, Pos.CENTER_LEFT);
        StackPane.setAlignment(labelPort, Pos.CENTER_LEFT);
        StackPane.setAlignment(labelDataBaseName, Pos.CENTER_LEFT);
        StackPane.setAlignment(labelUsername, Pos.CENTER_LEFT);
        StackPane.setAlignment(labelPassword, Pos.CENTER_LEFT);
        vbox.getChildren().addAll(stackPane1, stackPane2, stackPane3, stackPane4, stackPane5, stackPane6, stackPane7, stackPane8, stackPane9, stackPane10);
        dialog.getDialogPane().setMinWidth(500.0);
        dialog.getDialogPane().getButtonTypes().addAll(
                new ButtonType("Применить", ButtonBar.ButtonData.OK_DONE),
                new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE));
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent()) {
        if (result.orElseThrow().getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            Settings newSetting = new Settings();
            newSetting.setDbName(fieldDataBaseName.getText());
            newSetting.setPort(fieldPort.getText());
            newSetting.setUrl(fieldUrl.getText());
            newSetting.setUsername(fieldUsername.getText());
            newSetting.setPassword(fieldPassword.getText());
            Map<String, String> changeSettingsMap = settings.changeSettings(mapSettings, newSetting);
            newSetting.saveSettings(changeSettingsMap);
            Platform.runLater(() -> {
                try {
                    initialize();
                } catch (IOException ex) {
                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        } else if (result.orElseThrow().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) {
        }

    }
    // Кнопка преобразовать
    @FXML
    void submit(ActionEvent event) throws InterruptedException {

        Stage stage = new Stage();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл для конвертирования");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/desktop"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Все файлы", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите директорию куда сохранить файл");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/desktop"));
        File selectedDirectory = directoryChooser.showDialog(stage);
        str = selectedDirectory.getAbsolutePath() + "\\";
        // инициализация имени
        String fName = firstName.getText();
        // инициализация отчества
        String fathName = fathersName.getText();
        // инициализация фамилии
        String sName = surname.getText();
        // Получение кода района
        String val = RequestFormirovationService.getRequestCode(array, upfr.getValue());
        // Получение пути сохранения файла
        StringBuilder out = new StringBuilder();
        out.delete(0, out.length());
        out.append(str);
        out.append(val);
        out.append("_");
        if (remember.selectedProperty().getValue()) {
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
            Path path = Path.of(selectedFile.getAbsolutePath());
            ZipFileService.zipSingleFile(path, out.toString());
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Сообщение");
            alert.setHeaderText(null);
            alert.setContentText("Запрос успешно сконвертирован");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void actionGetUpdate(ActionEvent event) throws IOException, URISyntaxException, ClassNotFoundException, SQLException {
       
        if (compareDataHistoryList1 == -1) {
            
            ClientDAO client = new ClientDAO();
            recordList = client.findAllRecords();
            content.writeSprData(recordList);
            content.writeSprHistory(sprs);
            Platform.runLater(() -> statusBarInfo.setText("База данных успешно обновлена"));
            list = content.getContent();
            array = ConvertList.listToTwoArray(list);
            records = CustomListManipulation.getRecords(array);
            opfrList = CustomListManipulation.getOpfr(records);
            oList = FXCollections.observableArrayList(opfrList.stream().map(e -> e.getName()).collect(Collectors.toList()));
            opfr.setItems(oList);
            opfr.setValue(oList.get(0));
            Platform.runLater(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
                statusBarInfo.setText("Готов к работе");
            });
        }

        if (compareDataHistoryList2 == -1) {
            
            ClientDAO client = new ClientDAO();
            requestsList = client.findAllRequests();
            content.writeRequestData(requestsList);
            content.writeRequestHistory(requests);
            Platform.runLater(() -> statusBarInfo.setText("База данных успешно обновлена"));
            requestList = content.getRequests();
            rList = CustomListManipulation.getRequestList(requestList);
            requestsObserverableList = FXCollections.observableArrayList(rList);
            requestFile.setItems(requestsObserverableList);
            requestFile.setValue(requestsObserverableList.get(0));
            Platform.runLater(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
                statusBarInfo.setText("Готов к работе");
            });
        }
    }

    @FXML
    void instruction(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Инструкция по работе с приложением \"Конвертор запросов ПФР\"");
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("/com/mycompany/requestconverter/html/help.html").toExternalForm());
        BorderPane borderPane = new BorderPane(webView);
        webView.setPrefSize(960.0, 600.0);
        Scene scene = new Scene(borderPane, 960, 600);
        stage.setScene(scene);
        stage.show();

    }
    
    
      @FXML
    void actionAbout(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog();
        DialogPane dialogPane = dialog.getDialogPane();
        
        dialog.getDialogPane().setMinHeight(250.0);
        dialog.getDialogPane().setMinWidth(500.0);
        dialog.setTitle("О программе");
        dialog.setHeaderText(null);
        TextFlow textFlow = new TextFlow();
        VBox vBox = new VBox();
        Text name = new Text("Конвертор запросов ПФР");
        Text author = new Text("Разработка: Аликин Олег Сергеевич");
        Text info = new Text("Отдел эксплуатации и сопровождения информационных подсистем");
        Text email = new Text("email: alikino@31.sfr.gov.ru");
        Text copyright = new Text("© 2022 Отделение ПФР по Белгородской области");
        info.setWrappingWidth(450);
        textFlow.getChildren().add(vBox);
        vBox.getChildren().addAll(name, author, info, email, copyright);
        vBox.setSpacing(15.0);
        dialogPane.setContent(textFlow);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Optional<ButtonType> result = dialog.showAndWait();
    }

}
