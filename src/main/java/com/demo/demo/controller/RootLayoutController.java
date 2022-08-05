package com.demo.demo.controller;

import com.demo.demo.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RootLayoutController {

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleAbout() {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Погодник");
        alert.setHeaderText("О приложении");
        alert.setContentText("Версия приложения: v1.0.0\nАвтор: Михаил Верхорубов");

        alert.showAndWait();

    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

}
