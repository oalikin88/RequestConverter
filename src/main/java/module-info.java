module com.mycompany.requestconverter {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.requestconverter to javafx.fxml;
    exports com.mycompany.requestconverter;
}
