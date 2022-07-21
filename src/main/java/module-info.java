module com.demo.demo {

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires com.google.gson;


    opens com.demo.demo to javafx.fxml;
    exports com.demo.demo;

}