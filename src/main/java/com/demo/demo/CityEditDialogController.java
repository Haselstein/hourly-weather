package com.demo.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static com.demo.demo.MainApp.getCityData;
import static com.demo.demo.CityOverviewController.getUrlContent;

public class CityEditDialogController {

    @FXML
    private TextField cityNameField;
    @FXML
    private Label getCityName;

    private Stage dialogStage;
    private City city;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCity(City city) {
        this.city = city;

        cityNameField.setText(city.getCityName());

    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            city.setCityName(getCityName.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    @FXML
    private void handleSearch() { // Поиск города
        // #3 "Мягкий" поиск + выдавать списком результат поиска и выбирать город из него

        String output = getUrlContent("https://api.openweathermap.org/data/2.5/forecast?q=" + cityNameField.getText() + "&cnt=8&appid=88764e0a54d47f9fec0133fb1e57df11&units=metric");

        // Здесь реализовать список результата поиска
        if (output.isEmpty() || output.isBlank()) {
            getCityName.setText("Такой город не найден");
        }
        else {
            getCityName.setText(cityNameField.getText());
        }

    }

    private boolean isInputValid() {

        String errorMessage = "";

        for (City city : getCityData()) {
            if (cityNameField.getText().equals(city.getCityName())) {
                errorMessage = "Такой город уже есть в списке";
            }
        }

        if (cityNameField.getText() == null || cityNameField.getText().length() == 0 || getCityName.equals("Такой город не найден")) {
            errorMessage += "Некорректные данные или город не выбран\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка выбора города");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }

    }

}
