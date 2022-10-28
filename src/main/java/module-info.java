module com.mycompany.requestconverter {
    requires javafx.controls;
    requires javafx.fxml;
     requires java.sql;

    opens com.mycompany.requestconverter to javafx.fxml;
    exports com.mycompany.requestconverter;
}
