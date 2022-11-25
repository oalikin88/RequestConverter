module com.mycompany.requestconverter {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires java.base;
    requires javafx.base;
    requires java.naming;
  

    opens com.mycompany.requestconverter to javafx.fxml;
    exports com.mycompany.requestconverter;
    
}
