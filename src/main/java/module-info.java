module com.demo.demo {

    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.xml.bind;
    requires com.google.gson;
    requires java.prefs;
    requires preferences;
    requires preferences.xml;


    opens com.demo.demo to javafx.fxml;
    exports com.demo.demo;

}