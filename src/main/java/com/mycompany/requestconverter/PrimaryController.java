package com.mycompany.requestconverter;

import com.mycompany.requestconverter.data.Spravochnik;
import com.mycompany.requestconverter.data.ClientDAO;
import com.mycompany.requestconverter.data.ConvertExecutor;
import com.mycompany.requestconverter.data.ConvertOptions;
import com.mycompany.requestconverter.data.Department;
import com.mycompany.requestconverter.data.Fields;
import com.mycompany.requestconverter.data.Region;
import com.mycompany.requestconverter.data.Request;
import com.mycompany.requestconverter.data.Settings;
import com.mycompany.requestconverter.data.SubRequest;
import com.mycompany.requestconverter.service.Content;
import com.mycompany.requestconverter.service.ZipFileService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
    private Label surnameLabel;
    @FXML
    private Label showFileSavePath;
    @FXML
    private TextField firstName;
    @FXML
    private TextField snils;
    @FXML
    private MenuBar menu;
    @FXML
    private MenuItem menuCloseButton;
    @FXML
    private Menu menuFile;
    @FXML
    private ComboBox<String> opfr;
    @FXML
    private ComboBox<String> request;
    @FXML
    private ComboBox<String> spravochnik;
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
    private Label labelSuff;
    @FXML
    private TextField departmentNumber;
    @FXML
    private ComboBox<String> subRequest;
    @FXML
    private ComboBox<String> upfr;
    @FXML
    private CheckBox sendToBank;
    @FXML
    private DatePicker dateSending;
    @FXML
    private Label dateSendingLabel;
    @FXML
    private Label snilsLabel;
    @FXML
    private Spinner<Integer> ordinalNumber;
    @FXML
    private Label labelOrdinalNumber;
    @FXML
    private Label labelSubRequest;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label fathersNameLabel;
    @FXML
    private Label opfrLabel;
    @FXML
    private Label upfrLabel;
    @FXML
    private Label departmentNumberLabel;
    

    private List<Request> requestList;
    private List<SubRequest> subRequestList;
    private ObservableList<String> opfrList;
    private List<Region> regions;
    private List<Department> departments;
    private FileChooser fileChooser;
    private String str;
    private Settings settings;
    private Content content;
    private ObservableList<String> requestValueList;
    private ObservableList<String> spravochnikValueList;
    private String element;
    private ObservableList<String> upfrList;
    private ObservableList<String> subrequestListValue;
    private Region selectedRegion;
    private Tooltip attention;
    private boolean vdeksLNR;
    private List<Spravochnik> spravochnikContent;
    private List<Fields> fields;
    private Request defaultRequest;
    private List<Node> uiFields = new ArrayList<>();
    private Spravochnik choisedSpravochnik;
    private Set<Region> finalizeRegions = new HashSet<>();
    private Set<Region> currentRegions = new HashSet<>();
    private Department selectedDepartment;
    
    
    @FXML
    void initialize() throws IOException, URISyntaxException, ClassNotFoundException, SQLException {
        
        uiFields.add(surname);
        uiFields.add(firstName);
        uiFields.add(surname);
        uiFields.add(fathersName);
        uiFields.add(snils);
        uiFields.add(snilsLabel);
        uiFields.add(dateSending);
        uiFields.add(dateSendingLabel);
        uiFields.add(sendToBank);
        uiFields.add(subRequest);
        uiFields.add(surnameLabel);
        uiFields.add(firstNameLabel);
        uiFields.add(fathersNameLabel);
        uiFields.add(opfrLabel);
        uiFields.add(upfrLabel);
        uiFields.add(opfr);
        uiFields.add(upfr);
        uiFields.add(labelSubRequest);
        uiFields.add(labelOrdinalNumber);
        uiFields.add(ordinalNumber);
        uiFields.add(departmentNumber);
        uiFields.add(departmentNumberLabel);
        
        attention = new Tooltip("Поле не может быть пустым, а также не должно содержать следующих знаков: ; * \\ \" | / : ? < >");
        surname.setTooltip(attention);
        fathersName.setTooltip(attention);
        firstName.setTooltip(attention);
       // start.disableProperty().set(true);
        dateSending.visibleProperty().set(false);
        dateSendingLabel.visibleProperty().set(false);
        departmentNumberLabel.visibleProperty().set(false);
        departmentNumber.visibleProperty().set(false);
        ordinalNumber.setEditable(true);
        
        ordinalNumber.visibleProperty().set(false);
        labelOrdinalNumber.visibleProperty().set(false);
        sendToBank.visibleProperty().set(false);
        snils.visibleProperty().set(false);
        snils.setPromptText("XXX-XXX-XXX XX");
        labelSubRequest.visibleProperty().set(false);
        subRequest.visibleProperty().set(false);
        opfr.visibleProperty().set(false);
        opfrLabel.visibleProperty().set(false);
        upfr.visibleProperty().set(false);
        upfrLabel.visibleProperty().set(false);
        surname.visibleProperty().set(false);
        surnameLabel.visibleProperty().set(false);
        firstName.visibleProperty().set(false);
        firstNameLabel.visibleProperty().set(false);
        fathersName.visibleProperty().set(false);
        fathersNameLabel.visibleProperty().set(false);
        snilsLabel.visibleProperty().set(false);


        content = new Content();
        
        if(content.getSpravochnikContent().size() == 0) {
            ClientDAO clientDAO = new ClientDAO();
            List<Spravochnik> findAllCatalogs = clientDAO.findAllSpr();
            content.writeCatalogData(findAllCatalogs, Content.pathToCatalog);
        }
        
         if(content.getFields().size() == 0) {
            ClientDAO clientDAO = new ClientDAO();
            List<Fields> findAllFields = clientDAO.findAllFields();
            content.writeFieldsData(findAllFields, Content.pathToFields);
        }
        
        if(content.getRequests().size() == 0) {
            ClientDAO clientDAO = new ClientDAO();
            List<Request> findAllRequests = clientDAO.findAllRequests();
            content.writeRequestData(findAllRequests, Content.pathToRequest);
        }
        requestList = content.getRequests();
        
        if(content.getSubRequests().size() == 0) {
            ClientDAO clientDAO = new ClientDAO();
            List<SubRequest> findAllSubRequests = clientDAO.findAllSubRequests();
            content.writeSubRequestData(findAllSubRequests, Content.pathToSubRequest);
        }
        
         if (content.getRegionsContent().size() == 0) {
            ClientDAO clientDAO = new ClientDAO();
            List<Region> findAllRegions = clientDAO.findAllRegions();
            content.writeRegionData(findAllRegions, Content.pathToRegions);
        }

        
        if (content.getDepartmentsContent().size() == 0) {
            ClientDAO clientDAO = new ClientDAO();
            List<Department> findAllDepartments = clientDAO.findAllDepartments();
            content.writeDepartmentData(findAllDepartments, Content.pathToDepartments);
        }
        regions = content.getRegionsContent().stream().collect(Collectors.toList());
        departments = content.getDepartmentsContent().stream().collect(Collectors.toList());
        finalizeRegions = content.finalizeRegions(regions, departments);
        subRequestList = content.getSubRequests();
        fields = content.getFields();
        requestList = content.finalizeRequest(requestList, subRequestList);
        requestList = content.addFieldsToRequests(requestList, fields);
        spravochnikContent = content.getSpravochnikContent();
        String computerName = content.getComputerName();
        
        

    
        
        
        Task task = new Task() {
            @Override
            protected Void call() throws Exception { 


                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
            }
        };

        Platform.runLater(task);
        
        
         menuCloseButton.setOnAction(event -> {
            Platform.exit();
        });

        
        choisedSpravochnik = spravochnikContent.get(0);
        spravochnikValueList = FXCollections.observableArrayList(spravochnikContent.stream().map(e -> e.getName()).collect(Collectors.toList()));
        spravochnik.setItems(spravochnikValueList);
        spravochnik.setValue(spravochnikValueList.get(0));
        
        
        Spravochnik getSpr = spravochnikContent.stream().filter(e -> e.getName().equals(spravochnik.getSelectionModel().getSelectedItem())).collect(Collectors.toList()).get(0);
        List<Request> defaultRequestList = requestList.stream().filter(e -> e.getSpravochnik().getId() == getSpr.getId()).collect(Collectors.toList());
        defaultRequest = defaultRequestList.get(0);
        requestValueList = FXCollections.observableArrayList(defaultRequestList.stream().map(el -> el.getName()).collect(Collectors.toList()));
        request.setItems(requestValueList);
        request.setValue(requestValueList.get(0));
       
           for (Fields field : defaultRequest.getPattern().getFields()) {
            for (Node node : uiFields) {
                if (node.getId().equals(field.getField())) {
                    node.visibleProperty().set(true);
                }
            }
        }
           
        if (opfr.isVisible()) {
            
            currentRegions = finalizeRegions.stream().filter(el -> el.getSpravochnikId() == getSpr.getId()).collect(Collectors.toSet());
            opfrList = FXCollections.observableArrayList(currentRegions.stream().map(e -> e.getRegionName()).collect(Collectors.toList()));
            Collections.sort(opfrList);
            opfr.setItems(opfrList);
            opfr.setValue(opfr.getItems().get(0));
            element = opfr.getSelectionModel().getSelectedItem();
            //selectedRegion =
            List<Region> collect1 = finalizeRegions.stream().filter(e -> e.getRegionName().equals(element)).collect(Collectors.toList());
            List<Region> collect2 = collect1.stream().filter(e -> e.getSpravochnikId() == choisedSpravochnik.getId()).collect(Collectors.toList());
            selectedRegion = collect2.get(0);
        }

        if (upfr.isVisible()) {

            // получение списка УПФР в соответствии с выбранным значением ОПФР
            upfrList = FXCollections.observableArrayList(selectedRegion.getDepartments().stream().map(e -> e.getDepartmentName()).collect(Collectors.toList()));
            Collections.sort(upfrList);
            upfr.setItems(upfrList);
            upfr.setValue(upfrList.get(0));
        }
       


        spravochnik.setOnAction(event -> {
        
            String choice;
            
            if (null != spravochnik.getSelectionModel().getSelectedItem()) {
                choice = spravochnik.getSelectionModel().getSelectedItem();
            } else {
                choice = spravochnik.getItems().get(0);
            }
            
            choisedSpravochnik = spravochnikContent.stream().filter(e -> e.getName().equals(choice)).collect(Collectors.toList()).get(0);
            Request selectedRequest = requestList.stream().filter(el -> el.getSpravochnik().getId() == choisedSpravochnik.getId()).collect(Collectors.toList()).get(0);
            defaultRequest = selectedRequest;
            requestValueList = FXCollections.observableArrayList(requestList.stream().filter(e -> e.getSpravochnik().getId() == choisedSpravochnik.getId()).map(el -> el.getName()).collect(Collectors.toList()));
            request.setItems(requestValueList);
            request.setValue(requestValueList.get(0));
            currentRegions = finalizeRegions.stream().filter(el -> el.getSpravochnikId() == choisedSpravochnik.getId()).collect(Collectors.toSet());
            opfrList = FXCollections.observableArrayList(currentRegions.stream().map(e -> e.getRegionName()).collect(Collectors.toList()));
            Collections.sort(opfrList);
                 
                
                opfr.setItems(opfrList);
                opfr.setValue(opfr.getItems().get(0));
                opfr.valueProperty().addListener((ov, t, t1) -> {
                    if(t1 != null && !t1.equals(t)) {
                  
                        element = opfr.getSelectionModel().getSelectedItem();
                        List<Region> collect1 = currentRegions.stream().filter(e -> e.getRegionName().equals(element)).collect(Collectors.toList());
                        selectedRegion = collect1.get(0);
                    
                    }
                });
                

                // получение списка УПФР в соответствии с выбранным значением ОПФР
                upfrList = FXCollections.observableArrayList(selectedRegion.getDepartments().stream().map(e -> e.getDepartmentName()).collect(Collectors.toList()));
                Collections.sort(upfrList);
                upfr.setItems(upfrList);
                upfr.setValue(upfrList.get(0));
            
            
        });
            

        
        request.setOnAction(event -> {

            String sprSelected = spravochnik.getSelectionModel().getSelectedItem();
            String element2;
            if (null != request.getSelectionModel().getSelectedItem()) {
                element2 = request.getSelectionModel().getSelectedItem();

            } else {
                element2 = request.getItems().get(0);
            }

            Spravochnik spr = spravochnikContent.stream().filter(e -> e.getName().equals(sprSelected)).collect(Collectors.toList()).get(0);
            Request selectedRequest = requestList.stream().filter(e -> e.getName().equals(element2)).filter(el -> el.getSpravochnik().getId() == spr.getId()).collect(Collectors.toList()).get(0);
            defaultRequest = selectedRequest;

            for (Node node : uiFields) {
                if (node.visibleProperty().get() == true) {
                    node.visibleProperty().set(false);
                }
            }
            for (Fields field : defaultRequest.getPattern().getFields()) {
                for (Node node : uiFields) {
                    if (node.getId().equals(field.getField())) {
                        node.visibleProperty().set(true);
                    }
                }
            }

            if (subRequest.isVisible()) {

                subrequestListValue = FXCollections.observableArrayList(defaultRequest.getSubRequests().stream().map(e -> e.getSubRequestName()).collect(Collectors.toList()));
                subRequest.setItems(subrequestListValue);
                subRequest.setValue(subrequestListValue.get(0));
            }

            

            if (opfr.isVisible()) {
                if(null == opfr.getValue()){
                opfrList = FXCollections.observableArrayList(currentRegions.stream().map(e -> e.getRegionName()).collect(Collectors.toList()));
                opfr.setItems(opfrList);
                opfr.setValue(opfr.getItems().get(0));
                opfr.valueProperty().addListener((ov, t, t1) -> {
                    if(!t1.equals(t)) {
                        element = opfr.getSelectionModel().getSelectedItem();
                        List<Region> collect1 = currentRegions.stream().filter(e -> e.getRegionName().equals(element)).collect(Collectors.toList());
                        selectedRegion = collect1.get(0);
                    }
                });
                
            }
            }
                
            

            if (upfr.isVisible()) {

                // получение списка УПФР в соответствии с выбранным значением ОПФР
                upfrList = FXCollections.observableArrayList(selectedRegion.getDepartments().stream().map(e -> e.getDepartmentName()).collect(Collectors.toList()));

                upfr.setItems(upfrList);
                upfr.setValue(upfrList.get(0));
            }


            
        });

      
        
        
          opfr.setOnAction(event -> {
          
              if(null != opfr.getValue()) {
                  element = opfr.getSelectionModel().getSelectedItem();
                    List<Region> collect1 = currentRegions.stream().filter(e -> e.getRegionName().equals(element)).collect(Collectors.toList());
                    selectedRegion = collect1.get(0);
                    upfrList = FXCollections.observableArrayList(selectedRegion.getDepartments().stream().map(e -> e.getDepartmentName()).collect(Collectors.toList()));
                    upfr.setItems(upfrList);
                    upfr.setValue(upfrList.get(0));
                    
              }
              
          });
                
        
        
        
        
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
        PasswordField fieldPassword = new PasswordField();

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
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        } else if (result.orElseThrow().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) {
        }

    }

    // Кнопка преобразовать
    @FXML
    void submit(ActionEvent event) throws InterruptedException, IOException {

        Stage stage = new Stage();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файлы для конвертирования");
        File homeDir = new File(System.getProperty("user.home") + "/desktop");
        if (!homeDir.exists()) {
            homeDir.mkdirs();
        }
        fileChooser.setInitialDirectory(homeDir);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Все файлы", "*.*"));

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите директорию куда сохранить файл");
        File cacheFile = new File("directory.txt");
        if (cacheFile.exists()) {
            try ( InputStream inputStream = new FileInputStream(cacheFile)) {
                byte[] bytes = new byte[(int) cacheFile.length()];
                inputStream.read(bytes);
                File directory = new File(new String(bytes));
                if (directory.exists()) {
                    directoryChooser.setInitialDirectory(directory);
                } else {
                    directoryChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/desktop"));
                }
            }
        }

        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            try ( OutputStream outputStream = new FileOutputStream(cacheFile)) {
                byte[] bytes = selectedDirectory.getParent().getBytes();
                outputStream.write(bytes);
            }
        }
        String subReqValue;
        str = selectedDirectory.getAbsolutePath() + "\\";
        Optional<Department> optDep = selectedRegion.getDepartments().stream().filter(e -> e.getDepartmentName().equals(upfr.getValue())).findFirst();
        Optional<SubRequest> optSubReq = defaultRequest.getSubRequests().stream().filter(e -> e.getSubRequestName().equals(subRequest.getValue())).findFirst();
        if(optDep.isEmpty()) {
            selectedDepartment = null;
        } else {
            selectedDepartment = optDep.get();
        }
        if(optSubReq.isEmpty()) {
            subReqValue = null;
        } else {
            subReqValue = optSubReq.get().getSubRequestCode();
        }
        
        String day = "";
        String month = "";
        
        LocalDate dateSendingDocs;
        if(dateSending.getValue() != null) {
            dateSendingDocs = dateSending.getValue();
            day = dateSendingDocs.getDayOfMonth() < 10 ? day + "0" + String.valueOf(dateSendingDocs.getDayOfMonth()) : String.valueOf(dateSendingDocs.getDayOfMonth());
            month = dateSendingDocs.getMonthValue() < 10 ? month + "0" + dateSendingDocs.getMonthValue() : String.valueOf(dateSendingDocs.getMonthValue());
        } else {
            day = null;
            month = null;
        }
        
        ConvertOptions.ConvertOptionsBuilder options = ConvertOptions.builder()
                .surname(surname.getText())
                .name(firstName.getText())
                .middlename(fathersName.getText())
                .request(defaultRequest.getRequestCode())
                .subRequest(subReqValue)
                .oo(selectedRegion.getRegionCode())
                .to(selectedDepartment.getTerritoryCode())
                .ro(selectedDepartment.getDepartmentCode())
                .snils(snils.getText())
                .day(day)
                .month(month)
                .number(ordinalNumber.getEditor().getText())
                .departmentNumber(departmentNumber.getText());
        
        ConvertExecutor convertExecute = new ConvertExecutor();
        String convertToFileName = convertExecute.convertToFileName(options.pattern(defaultRequest.getPattern().getPattern()).build());
        str = str + convertToFileName;
                
        


        try {

            ZipFileService.zipMultipleFiles(selectedFiles, str);
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


    }

    @FXML
    void instruction(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Инструкция по работе с приложением \"Конвертер запросов CФР\"");
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
    void actionAbout(ActionEvent event) throws IOException {
        Dialog<ButtonType> dialog = new Dialog();
        DialogPane dialogPane = dialog.getDialogPane();
        dialog.getDialogPane().setMinHeight(250.0);
        dialog.getDialogPane().setMinWidth(500.0);
        Properties prop = new Properties();
        prop.load(App.class.getClassLoader().getResourceAsStream("version.properties"));
        String ver = prop.getProperty("version");
        dialog.setTitle("О программе");
        dialog.setHeaderText(null);
        TextFlow textFlow = new TextFlow();
        LocalDateTime date = LocalDateTime.now();

        VBox vBox = new VBox();
        Text name = new Text("Конвертер запросов СФР ver." + ver);
        Text author = new Text("Разработка: Аликин Олег Сергеевич");
        Text info = new Text("Отдел эксплуатации и сопровождения информационных подсистем");
        Text email = new Text("email: alikino@31.sfr.gov.ru");
        Text copyright = new Text("© 2008 - " + date.getYear() + " Отделение СФР по Белгородской области");
        info.setWrappingWidth(450);
        textFlow.getChildren().add(vBox);
        vBox.getChildren().addAll(name, author, info, email, copyright);
        vBox.setSpacing(15.0);
        dialogPane.setContent(textFlow);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Optional<ButtonType> result = dialog.showAndWait();
    }

}
