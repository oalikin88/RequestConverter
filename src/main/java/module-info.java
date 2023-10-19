module com.mycompany.requestconverter {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires javafx.base;
    requires java.naming;
    requires java.base;
  

    opens com.mycompany.requestconverter to javafx.fxml;
    exports com.mycompany.requestconverter;
    
}
