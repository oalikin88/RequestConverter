module com.mycompany.requestconverter {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
     requires java.sql;
     requires javafx.base;
     

    opens com.mycompany.requestconverter to javafx.fxml;
    exports com.mycompany.requestconverter;
}
