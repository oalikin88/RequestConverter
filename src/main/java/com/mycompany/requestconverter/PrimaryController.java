package com.mycompany.requestconverter;

import com.mycompany.requestconverter.data.ClientDAO;
import com.mycompany.requestconverter.data.DataHistory;
import com.mycompany.requestconverter.data.Department;
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
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
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
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
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
    private Spinner ordinalNumber;
    
    @FXML
    private Label labelOrdinalNumber;
    
    @FXML
    private Label labelSubRequest;
    
    @FXML
    private Label departmentNumberLabel;
    
    private String fName;
    private List<DataHistory> sprHistory;
    private List<DataHistory> sprVdHistory;
    private List<DataHistory> requests;
//    private List<Record> recordList;
    private List<Request> requestsListFromDB;
    private int compareDataHistoryList1;
    private int compareDataHistoryList2;
    private int compareDataHistoryList3;
    private List<String> listSpr;
    private List<String> listSprVd;
    private List<Request> requestList;
    private List<SubRequest> subRequestList;
    private ObservableList<String> opfrList;
//    private List<Record> opfrListSpr;
    private List<Region> regions;
    private List<Department> departments;
    private List<String> rList;
   
    private FileChooser fileChooser;
    private String str;
    private Settings settings;
    private Content content;
    private static Map<String, String> stateCode;
    private ObservableList<String> requestValueList;
    private ObservableList<String> subRequestValueList;
//    private List<Record> upfrRecords;
    private String element;
    private ObservableList<String> upfrList;
    private ObservableList<String> upfrListParentElement;
    private IntegerSpinnerValueFactory valueFactory;
    private Region selectedRegion;
    private Tooltip attention;
    private boolean vdeksLNR;
    @FXML
    void initialize() throws IOException, URISyntaxException, ClassNotFoundException, SQLException {
        
        attention = new Tooltip("Поле не может быть пустым, а также не должно содержать следующих знаков: ; * \\ \" | / : ? < >");
        surname.setTooltip(attention);
        fathersName.setTooltip(attention);
        firstName.setTooltip(attention);
        start.disableProperty().set(true);
        dateSending.visibleProperty().set(false);
        departmentNumberLabel.visibleProperty().set(false);
        departmentNumber.visibleProperty().set(false);
        ordinalNumber.setEditable(true);
        ordinalNumber.visibleProperty().set(false);
        labelOrdinalNumber.visibleProperty().set(false);
        sendToBank.visibleProperty().set(false);
        snils.visibleProperty().set(false);
        snils.setPromptText("XXX-XXX-XXX XX");


        content = new Content();

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
        subRequestList = content.getSubRequests();
        requestList = content.finalizeRequest(requestList, subRequestList);
        String computerName = content.getComputerName();
        

    
        
        
        Task task = new Task() {
            @Override
            protected Void call() throws Exception { 

//                System.out.println("Подключение к базе данных");
//                statusBarInfo.setText("Попытка подключения к базе данных");
//                DBConnection dBConnection = new DBConnection();
//                try ( Connection connection = dBConnection.getConnection()) {
//                    sprHistory = spr.getLastChangeFromHistory();
//                    sprVdHistory = sprVd.getLastChangeFromHistory();
//                    ClientDAO requestHistoryClient = new ClientDAO();
//                    requests = requestHistoryClient.getLastChangeFromRequest();
//                    List<String> sprHistoryStrings = spr.getHistory();
//                    List<String> sprVdHistoryStrings = sprVd.getHistory();
//                    List<String> requestHistoryStrings = content.getRequestHistoryContent();
//
//                    ConvertList convertSprList = new ConvertList();
//                    ConvertList convertSprVdList = new ConvertList();
//                    ConvertList convertRequestList = new ConvertList();
//
//                    List<DataHistory> sprConvertHistory = convertSprList.getDataHistory(sprHistoryStrings);
//                    List<DataHistory> sprVdConvertHistory = convertSprVdList.getDataHistory(sprVdHistoryStrings);
//                    List<DataHistory> requestHistory = convertRequestList.getDataHistory(requestHistoryStrings);
//
//                    compareDataHistoryList1 = DateCompareList.compareDataHistoryList(PrimaryController.this.sprHistory, sprConvertHistory);
//                    compareDataHistoryList3 = DateCompareList.compareDataHistoryList(PrimaryController.this.sprVdHistory, sprVdConvertHistory);
//                    compareDataHistoryList2 = DateCompareList.compareDataHistoryList(requests, requestHistory);
//
//                    if (compareDataHistoryList1 == -1 || compareDataHistoryList2 == -1 || compareDataHistoryList3 == -1) {
//                        Platform.runLater(() -> statusBarInfo.setText("Требуется обновить базу данных"));
//                        Dialog<ButtonType> dialog = new Dialog<>();
//                        DialogPane dialogPane = dialog.getDialogPane();
//                        dialog.setTitle("Сообщение");
//                        dialog.setHeaderText("Доступно обновление базы данных");
//                        dialogPane.setContentText("Вы желаете обновить базу данных?");
//                        dialog.getDialogPane().getButtonTypes().addAll(
//                                new ButtonType("Да", ButtonBar.ButtonData.OK_DONE),
//                                new ButtonType("Нет", ButtonBar.ButtonData.CANCEL_CLOSE));
//                        Optional<ButtonType> result = dialog.showAndWait();
//                        if (result.isPresent()) {
//                            if (result.orElseThrow().getButtonData() == ButtonBar.ButtonData.OK_DONE) {
//                                getUpdate.fire();
//                            }
//                        }
//
//                    } else {
//                        Platform.runLater(() -> statusBarInfo.setText("Готов к работе"));
//                    }
//
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
            }
        };

        Platform.runLater(task);

        // Обработка requestList
        Pattern inputTextFieldPattern = Pattern.compile("[;*\"|/:?<>]");
        requestValueList = FXCollections.observableArrayList(requestList.stream().map(e -> e.getName()).collect(Collectors.toList()));
        request.setItems(requestValueList);
        request.setValue(requestValueList.get(0));
       
        subRequestValueList = FXCollections.observableArrayList(requestList.get(0).getSubRequests().stream().map(e -> e.getSubRequestName()).collect(Collectors.toList()));
        if(subRequestValueList.size() == 0) {
            subRequestValueList.add("Для этого запроса отсутствуют подзапросы");
        }
        subRequest.setItems(subRequestValueList);
        subRequest.setValue(subRequestValueList.get(0));
        menuCloseButton.setOnAction(event -> {
            Platform.exit();
        });

        // получение списка ОПФР из общего массива
        
        if (content.getRegionsContent().size() == 0) {
            ClientDAO clientDAO = new ClientDAO();
            List<Region> findAllRegions = clientDAO.findAllRegions();
            content.writeRegionData(findAllRegions, Content.pathToRegions);
        }

        regions = content.getRegionsContent().stream().sorted((e1, e2) -> e1.getRegionCode().compareTo(e2.getRegionCode())).collect(Collectors.toList());

        if (content.getDepartmentsContent().size() == 0) {
            ClientDAO clientDAO = new ClientDAO();
            List<Department> findAllDepartments = clientDAO.findAllDepartments();
            content.writeDepartmentData(findAllDepartments, Content.pathToDepartments);
        }

        departments = content.getDepartmentsContent().stream().sorted((e1, e2) -> e1.getRegionCode().compareTo(e2.getRegionCode())).collect(Collectors.toList());
        List<Region> finalizeRegions = content.finalizeRegions(regions, departments);
        opfrList = FXCollections.observableArrayList(finalizeRegions.stream().map(e -> e.getRegionName()).collect(Collectors.toList()));
 
        

        opfr.setItems(opfrList);
        opfr.setValue(opfrList.get(0));
        element = opfr.getValue();
    //    List<Region> collect = regions.stream().filter(e -> e.getRegionName().equals(element)).collect(Collectors.toList());

        // получение списка УПФР из общего массива
        List<Department> deps = finalizeRegions.get(0).getDepartments();
       // upfrRecords = CustomListManipulation.getUpfrList(recordsSpr, element);
        upfrList = FXCollections.observableArrayList(deps.stream().map(e -> e.getDepartmentName()).collect(Collectors.toList()));
     //   upfrListParentElement = FXCollections.observableArrayList(opfrListSpr.stream().filter(e -> e.getName().contains(element)).map(m -> m.getName()).collect(Collectors.toSet()));
      //  upfrList.addAll(upfrListParentElement);
        upfr.setItems(upfrList);
        upfr.setValue(upfrList.get(0));

        opfr.setOnAction(event -> {
            String element2;
            if (null != opfr.getSelectionModel().getSelectedItem()) {
                element2 = opfr.getSelectionModel().getSelectedItem();

            } else {
                element2 = opfr.getItems().get(0);
            }

            selectedRegion = regions.stream().filter(e -> e.getRegionName().equals(element2)).collect(Collectors.toList()).get(0);
            // получение списка упфр при смене элемента в choicebox ОПФР
         //   List<Record> target = CustomListManipulation.getUpfrList(recordsSpr, element2);
            ObservableList<String> upfrList2 = null;
            ObservableList<String> upfrList1 = null;
            if(selectedRegion.getDepartments().isEmpty()) {
                upfrList2 = FXCollections.observableArrayList(Arrays.asList("Отсутствуют районы для выбранного региона"));
            } else {
                upfrList2 = FXCollections.observableArrayList(selectedRegion.getDepartments().stream().map(e -> e.getDepartmentName()).collect(Collectors.toList()));
            }
            
            //Подгрузка в список кодировок УПФРов родительского элемента ОПФР 
            //upfrList1 = FXCollections.observableArrayList(opfrListSpr.stream().filter(e -> e.getName().contains(element2)).map(m -> m.getName()).collect(Collectors.toSet()));
            //upfrList2.addAll(upfrList1);

            upfr.setItems(upfrList2);
            if (upfrList2 != null) {
                upfr.setValue(upfrList2.get(0));
            }
        }
                
        
                
        );
        
        request.setOnAction(event -> {
            
            String element2;
            if (null != request.getSelectionModel().getSelectedItem()) {
                element2 = request.getSelectionModel().getSelectedItem();

            } else {
                element2 = request.getItems().get(0);
            }

            Request selectedRequest = requestList.stream().filter(e -> e.getName().equals(element2)).collect(Collectors.toList()).get(0);
            if(selectedRequest.getRequestCode().equals("вдэкс") && selectedRequest.getName().contains("ЛНР")) {
                vdeksLNR = true;
            } else {
                vdeksLNR = false;
            }
            // получение списка упфр при смене элемента в choicebox ОПФР
         //   List<Record> target = CustomListManipulation.getUpfrList(recordsSpr, element2);
            ObservableList<String> subrequestList = null;
            
            subrequestList = FXCollections.observableArrayList(selectedRequest.getSubRequests().stream().map(e -> e.getSubRequestName()).collect(Collectors.toList()));
            //Подгрузка в список кодировок УПФРов родительского элемента ОПФР 
            //upfrList1 = FXCollections.observableArrayList(opfrListSpr.stream().filter(e -> e.getName().contains(element2)).map(m -> m.getName()).collect(Collectors.toSet()));
            //upfrList2.addAll(upfrList1);
            if(subrequestList.size() == 0) {
                subrequestList.add("Для этого запроса отсутствуют подзапросы");
            }
            
            String selectedReqCode = requestList.stream().filter(e -> e.getName().equals(request.getSelectionModel().getSelectedItem())).collect(Collectors.toList()).get(0).getRequestCode();
            
            
            switch (selectedReqCode) {
                case "(сул_045)":
                    if(sendToBank.isVisible()) {
                       sendToBank.visibleProperty().set(false); 
                    }
                    if(departmentNumber.visibleProperty().getValue()) {
                        departmentNumber.visibleProperty().set(false);
                        departmentNumberLabel.visibleProperty().set(false);
                    }
                    if(opfr.getItems().size() < 2) {
                        requestValueList = FXCollections.observableArrayList(requestList.stream().map(e -> e.getName()).collect(Collectors.toList()));
                        request.setItems(requestValueList);
                        request.setValue(requestValueList.get(0));
                    }
                    if(opfr.isDisabled()) {
                        opfr.setDisable(false);
                        upfr.setDisable(false);
                    }
                    subRequest.visibleProperty().set(false);
                    dateSending.setValue(null);
                    dateSending.visibleProperty().set(true);
                    labelSubRequest.setText("Дата отправки:");
                    labelOrdinalNumber.visibleProperty().set(true);
                    ordinalNumber.getEditor().clear();
                    valueFactory = new IntegerSpinnerValueFactory(0, 999999);
                    ordinalNumber.setValueFactory(valueFactory);
                    ordinalNumber.visibleProperty().set(true);
                    snils.textProperty().setValue("");
                    surname.textProperty().setValue("");
                    firstName.textProperty().setValue("");
                    fathersName.textProperty().setValue("");

                    start.disableProperty().bind(Bindings.isEmpty(surname.textProperty())
                            .or(Bindings.createBooleanBinding(() -> inputTextFieldPattern.matcher(firstName.getText()).find(), firstName.textProperty()))
                            .or(Bindings.createBooleanBinding(() -> inputTextFieldPattern.matcher(surname.getText()).find(), surname.textProperty()))
                            .or(Bindings.createBooleanBinding(() -> inputTextFieldPattern.matcher(fathersName.getText()).find(), fathersName.textProperty()))
                            .or(Bindings.isEmpty(firstName.textProperty()))
                            .or(Bindings.isEmpty(fathersName.textProperty()))
                            .or(Bindings.createBooleanBinding(() -> surname.getText().isBlank(), surname.textProperty()))
                            .or(Bindings.createBooleanBinding(() -> firstName.getText().isBlank(), firstName.textProperty()))
                            .or(Bindings.createBooleanBinding(() -> fathersName.getText().isBlank(), fathersName.textProperty()))
                    );
                    break;
                case "DNR":
                case "ЛНР":
                case "XO":
                case "ZO":
                    if(sendToBank.isVisible()) {
                       sendToBank.visibleProperty().set(false); 
                    }
                    if(departmentNumber.visibleProperty().getValue()) {
                        departmentNumber.visibleProperty().set(false);
                        departmentNumberLabel.visibleProperty().set(false);
                    }
                    surname.visibleProperty().set(false);
                    firstName.visibleProperty().set(false);
                    fathersName.visibleProperty().set(false);
                    snils.visibleProperty().set(true);
                    surnameLabel.setText("Снилс:");
                    opfr.setDisable(true);
                    upfr.setDisable(true);
                    surname.textProperty().setValue("");
                    firstName.textProperty().setValue("");
                    fathersName.textProperty().setValue("");
                    snils.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                            if (!newValue.matches("^\\d{3}-\\d{3}-\\d{3} \\d{2}$")) {
                                snils.setText(newValue.replaceAll("[^\\d-\\s]", ""));
                            }
                            if ((snils.getText().length() == 3 || snils.getText().length() == 7) && oldValue.length() < newValue.length()) {
                                snils.setText(snils.getText() + "-");
                            }
                            if (snils.getText().length() == 11 && oldValue.length() < newValue.length()) {

                                snils.setText(snils.getText() + " ");
                            }
                            if (snils.getText().length() >= 15) {
                                snils.setText(oldValue);
                            }
                        }
                    });

                    start.disableProperty().bind(Bindings.createBooleanBinding(() -> snils.getText().length() < 14, snils.textProperty()));
                    break;
                case "096000000_звд_ЗО":
                case "096_зрк_ЗО":
                case "095_045_МК_запрос":
                case "091000000_вдэкс_ХО":
                    if(sendToBank.isVisible()) {
                       sendToBank.visibleProperty().set(false); 
                    }
                    opfr.setDisable(true);
                    upfr.setDisable(true);
                      if(departmentNumber.visibleProperty().getValue()) {
                        departmentNumber.visibleProperty().set(false);
                        departmentNumberLabel.visibleProperty().set(false);
                    }
                    break;
                
                case "096_вдэкс":
                    if(sendToBank.isVisible()) {
                       sendToBank.visibleProperty().set(false); 
                    }
                    opfr.setDisable(true);
                    upfr.setDisable(true);
                    if(!departmentNumber.visibleProperty().getValue()) {
                        departmentNumber.visibleProperty().set(true);
                        departmentNumberLabel.visibleProperty().set(true);
                    }
                    break;
                case "доставка":
                    if(sendToBank.isVisible()) {
                       sendToBank.visibleProperty().set(false); 
                    }
                    if(opfr.isDisabled()) {
                        opfr.setDisable(false);
                        upfr.setDisable(false);
                    }
                    opfrList = FXCollections.observableArrayList(finalizeRegions.stream().filter(j -> j.getRegionCode().equals("093")).map(e -> e.getRegionName()).collect(Collectors.toList()));
                    opfr.setItems(opfrList);
                    opfr.setValue(opfrList.get(0));
                    if(!departmentNumber.visibleProperty().getValue()) {
                        departmentNumber.visibleProperty().set(true);
                        departmentNumberLabel.visibleProperty().set(true);
                    }
                    break;
                case "зрк":
                    if(sendToBank.isVisible()) {
                       sendToBank.visibleProperty().set(false); 
                    }
                    if(departmentNumber.visibleProperty().getValue()) {
                        departmentNumber.visibleProperty().set(false);
                        departmentNumberLabel.visibleProperty().set(false);
                    }
                     if(opfr.isDisabled()) {
                        opfr.setDisable(false);
                        upfr.setDisable(false);
                        }
                        opfrList = FXCollections.observableArrayList(finalizeRegions.stream().filter(j -> j.getRegionCode().equals("094")).map(e -> e.getRegionName()).collect(Collectors.toList()));
                        opfr.setItems(opfrList);
                        opfr.setValue(opfrList.get(0));
                    break;
                case "межведком_запрос":
                    if(sendToBank.isVisible()) {
                       sendToBank.visibleProperty().set(false); 
                    }
                    if(departmentNumber.visibleProperty().getValue()) {
                        departmentNumber.visibleProperty().set(false);
                        departmentNumberLabel.visibleProperty().set(false);
                    }
                     if(opfr.isDisabled()) {
                        opfr.setDisable(false);
                        upfr.setDisable(false);
                        }
                        opfrList = FXCollections.observableArrayList(finalizeRegions.stream().filter(j -> j.getRegionCode().equals("093")).map(e -> e.getRegionName()).collect(Collectors.toList()));
                        opfr.setItems(opfrList);
                        opfr.setValue(opfrList.get(0));
                    break;
                   
                case "вдэкс":
                    if(sendToBank.isVisible()) {
                       sendToBank.visibleProperty().set(false); 
                    }
                    if(request.getSelectionModel().getSelectedItem().contains("ЛНР")) {
                        if(opfr.isDisabled()) {
                        opfr.setDisable(false);
                        upfr.setDisable(false);
                        }
                        opfrList = FXCollections.observableArrayList(finalizeRegions.stream().filter(j -> j.getRegionCode().equals("094")).map(e -> e.getRegionName()).collect(Collectors.toList()));
                        opfr.setItems(opfrList);
                        opfr.setValue(opfrList.get(0));
                        if(!departmentNumber.visibleProperty().getValue()) {
                        departmentNumber.visibleProperty().set(true);
                        departmentNumberLabel.visibleProperty().set(true);
                        
                    }
                      
                    } else {
                         if(opfr.isDisabled()) {
                        opfr.setDisable(false);
                        upfr.setDisable(false);
                    }
                     if(opfr.getItems().size() < 2) {
                        opfrList = FXCollections.observableArrayList(finalizeRegions.stream().map(e -> e.getRegionName()).collect(Collectors.toList()));
                        opfr.setItems(opfrList);
                        opfr.setValue(opfrList.get(0));
                    }
                   
                    if(departmentNumber.visibleProperty().getValue()) {
                        departmentNumber.visibleProperty().set(false);
                        departmentNumberLabel.visibleProperty().set(false);
                    }
                    ordinalNumber.visibleProperty().set(false);
                    snils.visibleProperty().set(false);
                    surname.visibleProperty().set(true);
                    firstName.visibleProperty().set(true);
                    fathersName.visibleProperty().set(true);
                    surnameLabel.setText("Фамилия:");
                    ordinalNumber.getEditor().clear();
                    labelOrdinalNumber.visibleProperty().set(false);
                    dateSending.visibleProperty().set(false);
                    dateSending.setValue(null);
                    subRequest.visibleProperty().set(true);
                    labelSubRequest.setText("Подзапрос:");
                    opfr.setDisable(false);
                    upfr.setDisable(false);
                    snils.textProperty().setValue("");
                    surname.textProperty().setValue("");
                    firstName.textProperty().setValue("");
                    fathersName.textProperty().setValue("");

                    start.disableProperty().bind(Bindings.isEmpty(surname.textProperty())
                            .or(Bindings.createBooleanBinding(() -> inputTextFieldPattern.matcher(firstName.getText()).find(), firstName.textProperty()))
                            .or(Bindings.createBooleanBinding(() -> inputTextFieldPattern.matcher(surname.getText()).find(), surname.textProperty()))
                            .or(Bindings.createBooleanBinding(() -> inputTextFieldPattern.matcher(fathersName.getText()).find(), fathersName.textProperty()))
                            .or(Bindings.isEmpty(firstName.textProperty()))
                            .or(Bindings.isEmpty(fathersName.textProperty()))
                            .or(Bindings.createBooleanBinding(() -> surname.getText().isBlank(), surname.textProperty()))
                            .or(Bindings.createBooleanBinding(() -> firstName.getText().isBlank(), firstName.textProperty()))
                            .or(Bindings.createBooleanBinding(() -> fathersName.getText().isBlank(), fathersName.textProperty()))
                    );
                    } 
                      break;
                case "зилс":
                    sendToBank.visibleProperty().set(true);
                     if(opfr.isDisabled()) {
                        opfr.setDisable(false);
                        upfr.setDisable(false);
                    }
                     if(opfr.getItems().size() < 2) {
                        opfrList = FXCollections.observableArrayList(finalizeRegions.stream().map(e -> e.getRegionName()).collect(Collectors.toList()));
                        opfr.setItems(opfrList);
                        opfr.setValue(opfrList.get(0));
                    }
                   
                    if(departmentNumber.visibleProperty().getValue()) {
                        departmentNumber.visibleProperty().set(false);
                        departmentNumberLabel.visibleProperty().set(false);
                    }
                    ordinalNumber.visibleProperty().set(false);
                    snils.visibleProperty().set(false);
                    surname.visibleProperty().set(true);
                    firstName.visibleProperty().set(true);
                    fathersName.visibleProperty().set(true);
                    surnameLabel.setText("Фамилия:");
                    ordinalNumber.getEditor().clear();
                    labelOrdinalNumber.visibleProperty().set(false);
                    dateSending.visibleProperty().set(false);
                    dateSending.setValue(null);
                    subRequest.visibleProperty().set(true);
                    labelSubRequest.setText("Подзапрос:");
                    opfr.setDisable(false);
                    upfr.setDisable(false);
                    snils.textProperty().setValue("");
                    surname.textProperty().setValue("");
                    firstName.textProperty().setValue("");
                    fathersName.textProperty().setValue("");

                    start.disableProperty().bind(Bindings.isEmpty(surname.textProperty())
                            .or(Bindings.createBooleanBinding(() -> inputTextFieldPattern.matcher(firstName.getText()).find(), firstName.textProperty()))
                            .or(Bindings.createBooleanBinding(() -> inputTextFieldPattern.matcher(surname.getText()).find(), surname.textProperty()))
                            .or(Bindings.createBooleanBinding(() -> inputTextFieldPattern.matcher(fathersName.getText()).find(), fathersName.textProperty()))
                            .or(Bindings.isEmpty(firstName.textProperty()))
                            .or(Bindings.isEmpty(fathersName.textProperty()))
                            .or(Bindings.createBooleanBinding(() -> surname.getText().isBlank(), surname.textProperty()))
                            .or(Bindings.createBooleanBinding(() -> firstName.getText().isBlank(), firstName.textProperty()))
                            .or(Bindings.createBooleanBinding(() -> fathersName.getText().isBlank(), fathersName.textProperty()))
                    );
                    break;
                default:
                    if(sendToBank.isVisible()) {
                       sendToBank.visibleProperty().set(false); 
                    }
                    if(opfr.isDisabled()) {
                        opfr.setDisable(false);
                        upfr.setDisable(false);
                    }
                     if(opfr.getItems().size() < 2) {
                        opfrList = FXCollections.observableArrayList(finalizeRegions.stream().map(e -> e.getRegionName()).collect(Collectors.toList()));
                        opfr.setItems(opfrList);
                        opfr.setValue(opfrList.get(0));
                    }
                   
                    if(departmentNumber.visibleProperty().getValue()) {
                        departmentNumber.visibleProperty().set(false);
                        departmentNumberLabel.visibleProperty().set(false);
                    }
                    ordinalNumber.visibleProperty().set(false);
                    snils.visibleProperty().set(false);
                    surname.visibleProperty().set(true);
                    firstName.visibleProperty().set(true);
                    fathersName.visibleProperty().set(true);
                    surnameLabel.setText("Фамилия:");
                    ordinalNumber.getEditor().clear();
                    labelOrdinalNumber.visibleProperty().set(false);
                    dateSending.visibleProperty().set(false);
                    dateSending.setValue(null);
                    subRequest.visibleProperty().set(true);
                    labelSubRequest.setText("Подзапрос:");
                    opfr.setDisable(false);
                    upfr.setDisable(false);
                    snils.textProperty().setValue("");
                    surname.textProperty().setValue("");
                    firstName.textProperty().setValue("");
                    fathersName.textProperty().setValue("");

                    start.disableProperty().bind(Bindings.isEmpty(surname.textProperty())
                            .or(Bindings.createBooleanBinding(() -> inputTextFieldPattern.matcher(firstName.getText()).find(), firstName.textProperty()))
                            .or(Bindings.createBooleanBinding(() -> inputTextFieldPattern.matcher(surname.getText()).find(), surname.textProperty()))
                            .or(Bindings.createBooleanBinding(() -> inputTextFieldPattern.matcher(fathersName.getText()).find(), fathersName.textProperty()))
                            .or(Bindings.isEmpty(firstName.textProperty()))
                            .or(Bindings.isEmpty(fathersName.textProperty()))
                            .or(Bindings.createBooleanBinding(() -> surname.getText().isBlank(), surname.textProperty()))
                            .or(Bindings.createBooleanBinding(() -> firstName.getText().isBlank(), firstName.textProperty()))
                            .or(Bindings.createBooleanBinding(() -> fathersName.getText().isBlank(), fathersName.textProperty()))
                    );
                    break;

            }
            
            
            
            
            
            
            
//            subRequest.visibleProperty().bind(
//                Bindings.equal("дат", request.getSelectionModel().selectedItemProperty()));
            
            
//            if(selectedRequest.getRequestCode().equals("дат")) {
//                subRequest.visibleProperty().set(false);
//            }
            subRequest.setItems(subrequestList);
           
                subRequest.setValue(subrequestList.get(0));
            
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
        
        
            start.disableProperty().bind(Bindings.isEmpty(surname.textProperty())
                 .or(Bindings.createBooleanBinding(() -> inputTextFieldPattern.matcher(firstName.getText()).find(), firstName.textProperty()))
                 .or(Bindings.createBooleanBinding(() -> inputTextFieldPattern.matcher(surname.getText()).find(), surname.textProperty()))
                 .or(Bindings.createBooleanBinding(() -> inputTextFieldPattern.matcher(fathersName.getText()).find(), fathersName.textProperty()))
                 .or(Bindings.isEmpty(firstName.textProperty()))
                 .or(Bindings.isEmpty(fathersName.textProperty()))
                 .or(Bindings.createBooleanBinding(() -> surname.getText().isBlank(), surname.textProperty()))
                 .or(Bindings.createBooleanBinding(() -> firstName.getText().isBlank(), firstName.textProperty()))
                 .or(Bindings.createBooleanBinding(() -> fathersName.getText().isBlank(), fathersName.textProperty()))
                
                 );
            
         
            



        
   
        


//        request.getSelectionModel().selectedItemProperty().addListener((Override, t, t1) -> {
//            if (t1.contains(requestValueList.get(1))) {
//                recordsSprVd = sprVd.getRecordsFromLocal();
//                opfrListSpr = CustomListManipulation.getOpfr(recordsSprVd);
//                opfrList = FXCollections.observableArrayList(opfrListSpr.stream().map(e -> e.getName()).sorted((o1, o2) -> o1.compareTo(o2)).collect(Collectors.toList()));
//                //opfr.getItems().addAll(oListSprVd);
//                opfr.setItems(opfrList);
//
//                opfr.setValue(opfrList.get(0));
//                element = opfr.getValue();
//                upfrRecords = CustomListManipulation.getUpfrList(recordsSprVd, element);
//                upfrList = FXCollections.observableArrayList(upfrRecords.stream().map(e -> e.getName()).collect(Collectors.toList()));
//                upfrListParentElement = FXCollections.observableArrayList(opfrListSpr.stream().filter(e -> e.getName().contains(element)).map(m -> m.getName()).collect(Collectors.toSet()));
//                upfrList.addAll(upfrListParentElement);
//                upfr.setItems(upfrList);
//                upfr.setValue(upfrList.get(0));
//
//                opfr.setOnAction(event -> {
//                    String element2;
//                    if (null != opfr.getSelectionModel().getSelectedItem()) {
//                        element2 = opfr.getSelectionModel().getSelectedItem();
//
//                    } else {
//                        element2 = opfr.getItems().get(0);
//                    }
//
//                    // получение списка упфр при смене элемента в choicebox ОПФР
//                    List<Record> target = CustomListManipulation.getUpfrList(recordsSprVd, element2);
//                    ObservableList<String> upfrList2 = null;
//                    ObservableList<String> upfrList1 = null;
//
//                    upfrList2 = FXCollections.observableArrayList(target.stream().map(e -> e.getName()).collect(Collectors.toList()));
//                    //Подгрузка в список кодировок УПФРов родительского элемента ОПФР 
//                    upfrList1 = FXCollections.observableArrayList(opfrListSpr.stream().filter(e -> e.getName().contains(element2)).map(m -> m.getName()).collect(Collectors.toSet()));
//                    upfrList2.addAll(upfrList1);
//
//                    upfr.setItems(upfrList2);
//                    if (upfrList2 != null) {
//                        upfr.setValue(upfrList2.get(0));
//                    }
//                }
//                );
//
//            } else {
//
//                opfrListSpr = CustomListManipulation.getOpfr(recordsSpr);
//                opfrList = FXCollections.observableArrayList(opfrListSpr.stream().map(e -> e.getName()).collect(Collectors.toList()));
//                opfr.setItems(opfrList);
//                opfr.setValue(opfrList.get(0));
//                element = opfr.getValue();
//                upfrRecords = CustomListManipulation.getUpfrList(recordsSpr, element);
//                upfrList = FXCollections.observableArrayList(upfrRecords.stream().map(e -> e.getName()).collect(Collectors.toList()));
//                upfrListParentElement = FXCollections.observableArrayList(opfrListSpr.stream().filter(e -> e.getName().contains(element)).map(m -> m.getName()).collect(Collectors.toSet()));
//                upfrList.addAll(upfrListParentElement);
//                upfr.setItems(upfrList);
//                upfr.setValue(upfrList.get(0));
//
//                opfr.setOnAction(event -> {
//                    String element2;
//                    if (null != opfr.getSelectionModel().getSelectedItem()) {
//                        element2 = opfr.getSelectionModel().getSelectedItem();
//                    } else {
//                        element2 = opfr.getItems().get(0);
//                    }
//                    // получение списка упфр при смене элемента в choicebox ОПФР
//                    List<Record> target = CustomListManipulation.getUpfrList(recordsSpr, element2);
//                    ObservableList<String> upfrList2 = null;
//                    ObservableList<String> upfrList1 = null;
//
//                    upfrList2 = FXCollections.observableArrayList(target.stream().map(e -> e.getName()).collect(Collectors.toList()));
//                    //Подгрузка в список кодировок УПФРов родительского элемента ОПФР 
//                    upfrList1 = FXCollections.observableArrayList(opfrListSpr.stream().filter(e -> e.getName().contains(element2)).map(m -> m.getName()).collect(Collectors.toSet()));
//                    upfrList2.addAll(upfrList1);
//
//                    upfr.setItems(upfrList2);
//                    if (upfrList2 != null) {
//                        upfr.setValue(upfrList2.get(0));
//                    }
//                }
//                );
//
//            }
//        });

        // Работа с суффиксом
//        ObservableList<String> observableArrayKeys = FXCollections.observableArrayList(stateCode.values().stream().collect(Collectors.toList()));


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
        str = selectedDirectory.getAbsolutePath() + "\\";
        
        StringBuilder out = new StringBuilder();
        out.delete(0, out.length());
        out.append(str);
        
        if(snils.isVisible()) {
            
            out.append(snils.textProperty().getValue());
            out.append("_");
            if(!subRequest.getValue().contains("отсутствуют подзапросы")) {
                out.append(subRequestList.stream().filter(e -> e.getSubRequestName().equals(subRequest.getValue())).collect(Collectors.toList()).get(0).getSubRequestCode());
                out.append("_");
            }
            out.append(requestList.stream().filter(e -> e.getName().equals(request.getValue())).collect(Collectors.toList()).get(0).getRequestCode());
        
        } else {
        // инициализация имени
        String fName = firstName.getText();
        // инициализация отчества
        String fathName = fathersName.getText();
        // инициализация фамилии
        String sName = surname.getText();
        // Получение кода района
        String val;
        String dep = "";
        
        Region selectedReg = regions.stream().filter(e -> e.getRegionName().equals(opfr.getValue())).collect(Collectors.toList()).get(0);
        if(selectedReg.getDepartments().isEmpty()) {
            val = selectedReg.getRegionCode();
        } else {
         Department resultDepartment = departments.stream().filter(e -> e.getDepartmentName().equals(upfr.getValue())).filter(j -> j.getRegionCode().equals(selectedReg.getRegionCode())).collect(Collectors.toList()).get(0);
        val = resultDepartment.getRegionCode();
        dep = resultDepartment.getDepartmentCode();
        }
        
//        if (request.getValue().equals("Запросы выплатных дел")) {
//            val = RequestFormirovationService.getRequestCode(sprVd.getInputContent(), opfr.getValue(), upfr.getValue());
//            isVd = true;
//        } else {
//            val = RequestFormirovationService.getRequestCode(spr.getInputContent(), opfr.getValue(), upfr.getValue());
//            isVd = false;
//        }
        Request resultRequest = requestList.stream()
                .filter(e -> e.getName()
                        .equals(request.getValue()))
                .collect(Collectors.toList())
                .get(0);
        
  
            switch (resultRequest.getRequestCode()) {
                case "096000000_звд_ЗО":
                    val = resultRequest.getRequestCode();
                    out.append(val);
                    out.append("_");
                    out.append(sName);
                    out.append(" ");
                    out.append(fName);
                    out.append(".");
                    out.append(fathName);
                    break;
                case "сул_045":
                    String day;
                    String month;
                    if(dateSending.getValue().getDayOfMonth() < 10) {
                        day = "0" + dateSending.getValue().getDayOfMonth();
                    } else {
                        day = "" + dateSending.getValue().getDayOfMonth();
                    }

                    if(dateSending.getValue().getMonthValue() < 10) {
                        month = "0" + dateSending.getValue().getMonthValue();
                    } else {
                        month = "" + dateSending.getValue().getMonthValue();
                    }
                    val = val + "_" + resultRequest.getRequestCode() + "_" + day + month
                            + "_" + ordinalNumber.getEditor().getText();
                    out.append(val);
                    break;
                case "091000000_вдэкс_ХО":
                    val = resultRequest.getRequestCode();
                    out.append(val);
                    out.append("_");
                    out.append(sName);
                    out.append(" ");
                    out.append(fName);
                    out.append(".");
                    out.append(fathName);
                    break;
                case "096_вдэкс":
                    val = resultRequest.getRequestCode();
                    out.append(val);
                    out.append("_");
                    out.append(sName);
                    out.append(" ");
                    out.append(fName);
                    out.append(".");
                    out.append(fathName);
                    out.append("_" + departmentNumber.textProperty().getValue());
                    break;
                case "096_зрк_ЗО":
                    val = resultRequest.getRequestCode();
                    out.append(val);
                    out.append("_");
                    out.append(sName);
                    out.append(" ");
                    out.append(fName);
                    out.append(".");
                    out.append(fathName);
                    out.append("_запрос");
                    break;
                case "095_045_МК_запрос":
                    val = resultRequest.getRequestCode();
                    out.append(val);
                    out.append("_");
                    out.append(sName);
                    out.append(" ");
                    out.append(fName);
                    out.append(".");
                    out.append(fathName);
                    break;
                case "доставка":
                     if(resultRequest.getSubRequests().size() > 0) {
                        SubRequest resultSubRequest = resultRequest.getSubRequests()
                            .stream()
                            .filter(e -> e.getSubRequestName()
                                    .equals(subRequest.getValue()))
                            .collect(Collectors.toList())
                            .get(0);
                    val = val + dep;
                    
                    val = val + "_" + resultRequest.getRequestCode() + "_" + resultSubRequest.getSubRequestCode();
                   } else {
                       val = val + dep;
                       
                       val = val + "_" + resultRequest.getRequestCode();
                   }
                    out.append(val);
                
                    out.append("_");
                    out.append(sName);
                    out.append(" ");
                    out.append(fName);
                    out.append(".");
                    out.append(fathName);
                    out.append("_" + departmentNumber.textProperty().getValue());
                    break;
                default:

           if(resultRequest.getSubRequests().size() > 0) {
                SubRequest resultSubRequest = resultRequest.getSubRequests()
                    .stream()
                    .filter(e -> e.getSubRequestName()
                            .equals(subRequest.getValue()))
                    .collect(Collectors.toList())
                    .get(0);
            val = val + dep;
            if(sendToBank.selectedProperty().getValue()) {
                            val = val + "сбр";
                        }
            val = val + "_" + resultRequest.getRequestCode() + "_" + resultSubRequest.getSubRequestCode();
           } else {
               val = val + dep;
               if(sendToBank.selectedProperty().getValue()) {
                            val = val + "сбр";
                        }
               val = val + "_" + resultRequest.getRequestCode();
           }
         

        
        // Получение пути сохранения файла
        
       

//

//
//        
//        
//        if (sendToBank.selectedProperty().getValue()) {
//            out.append("(ЦБ)");
//        }
//        
//       // out.append("_");

//        //out.append(RequestFormirovationService.getRequestValue(requestList, requestFile.getValue()));
//

//        }
        out.append(val);
        out.append("_");
        out.append(sName);
        out.append(" ");
        out.append(fName);
        out.append(".");
        out.append(fathName);
            }
        
      
        }
        if(vdeksLNR) {
            out.append("_");
            out.append(departmentNumber.textProperty().getValue());
        }
        out.append(".zip");

        System.out.println(out);

        try {

            ZipFileService.zipMultipleFiles(selectedFiles, out.toString());
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

//        if (compareDataHistoryList1 == -1) {
//
//            // подумать как автоматически подтягивать нужный справочник
//            List<Record> inputFromSprDB = spr.getRecordsFromDB();
//
//            spr.writeSpr(inputFromSprDB);
//            spr.writeHistory(sprHistory);
//            Platform.runLater(() -> statusBarInfo.setText("База данных успешно обновлена"));
//            recordsSpr = spr.getRecordsFromLocal();
//            opfrListSpr = CustomListManipulation.getOpfr(recordsSpr);
//            opfrList = FXCollections.observableArrayList(opfrListSpr.stream().map(e -> e.getName()).collect(Collectors.toList()));
//            opfr.setItems(opfrList);
//            opfr.setValue(opfrList.get(0));
//            Platform.runLater(() -> {
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                statusBarInfo.setText("Готов к работе");
//            });
//        }
//
//        if (compareDataHistoryList3 == -1) {
//
//            List<Record> inputFromSprVdDB = sprVd.getRecordsFromDB();
//            sprVd.writeSpr(inputFromSprVdDB);
//            sprVd.writeHistory(sprVdHistory);
//
//            Platform.runLater(() -> statusBarInfo.setText("База данных успешно обновлена"));
//            recordsSprVd = sprVd.getRecordsFromLocal();
//            opfrListSpr = CustomListManipulation.getOpfr(recordsSprVd);
//            opfrList = FXCollections.observableArrayList(opfrListSpr.stream().map(e -> e.getName()).collect(Collectors.toList()));
//            opfr.setItems(opfrList);
//            opfr.setValue(opfrList.get(0));
//            Platform.runLater(() -> {
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                statusBarInfo.setText("Готов к работе");
//            });
//        }
//
//        if (compareDataHistoryList2 == -1) {
//
//            ClientDAO client = new ClientDAO();
//            requestsListFromDB = client.findAllRequests();
//            content.writeRequestData(requestsListFromDB);
//            content.writeRequestHistory(requests);
//            Platform.runLater(() -> statusBarInfo.setText("База данных успешно обновлена"));
//            requestList = content.getRequests();
//            rList = CustomListManipulation.getRequestList(requestList);
//            subRequestValueList = FXCollections.observableArrayList(rList);
//            requestFile.setItems(subRequestValueList);
//            requestFile.setValue(subRequestValueList.get(0));
//            Platform.runLater(() -> {
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                statusBarInfo.setText("Готов к работе");
//            });
//        }
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
