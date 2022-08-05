package com.demo.demo.controller;

import com.demo.demo.City;
import com.demo.demo.MainApp;
import com.google.gson.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class CityOverviewController {

    private static final String API_KEY = "88764e0a54d47f9fec0133fb1e57df11";

    @FXML
    private TableView<City> cityTable;
    @FXML
    private TableColumn<City, String> cityName;
    @FXML
    private Label cityNameLabel;

    // @TODO Убрать переменные и сразу вставлять текст в ячейки
    @FXML
    private Label temp_9;
    @FXML
    private Label humidity_9;

    @FXML
    private Label temp_12;
    @FXML
    private Label humidity_12;

    @FXML
    private Label temp_15;
    @FXML
    private Label humidity_15;

    @FXML
    private Label temp_18;
    @FXML
    private Label humidity_18;

    @FXML
    private Label temp_21;
    @FXML
    private Label humidity_21;

    @FXML
    private Label temp_24;
    @FXML
    private Label humidity_24;

    @FXML
    private Label temp_3;
    @FXML
    private Label humidity_3;

    @FXML
    private Label temp_6;
    @FXML
    private Label humidity_6;

    private MainApp mainApp;

    public CityOverviewController() {
    }

    @FXML
    private void initialize() {

        List<Label> tempList = new ArrayList<>();

        cityName.setCellValueFactory(cellData -> cellData.getValue().cityNameProperty());

        showCityDetails(null);

        // Отслеживание изменений и их вывод
        cityTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCityDetails(newValue));

    }

    public void setMainApp(MainApp mainApp) {

        this.mainApp = mainApp;

        // Добавление в таблицу данных из наблюдаемого списка
        cityTable.setItems(mainApp.getCityData());

    }

    static String getUrlContent(String urlAdress) {

        StringBuffer content = new StringBuffer();

        try {

            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }

            bufferedReader.close();

        }
        catch (Exception e) {
            System.out.println("Введён неверный город");
        }

        return content.toString();

    }

    public String gmt(int timezone, String time) {
        // Перевод времени в соответствии с часовым поясом

        String result;
        double a = timezone / 3600.;

        String[] timePartString = time.split(":");

        int newHour = (int) (Integer.parseInt(String.valueOf(timePartString[0])) - a);
        newHour = 3*(Math.round(newHour/3));

        //int newHour = (int) (3*(Math.round(a/3))) + Integer.parseInt(String.valueOf(timePartString[0]));

        // #1
        // Сделать правильную логику значений, выходящих за текущий день

        // Также разграничить логикой значения, которые отличаются знаком в таймзоне
        // То есть -7 часов и +7 часов не должны быть "одинаковыми"
        if (newHour < 0)// Предыдущий день
            newHour += 24;
        if (newHour >= 24)// Следующий день
            newHour -= 24;

        if (newHour < 10)
            timePartString[0] = "0" + newHour;
        else
            timePartString[0] = String.valueOf(newHour);

        result = String.join(":", timePartString);

        return result;

    }

    private void showCityDetails(City city) {

        if (city != null) {

            // Отображение выбранного города справа вверху
            cityNameLabel.setText(city.getCityName());

            String getCityName = cityNameLabel.getText().trim();

            String output = getUrlContent(
                    "https://api.openweathermap.org/data/2.5/forecast?q=" +
                            getCityName +
                            "&cnt=8" +
                            "&appid=" +
                            API_KEY +
                            "&units=" +
                            "metric");

            JsonObject jobj = new Gson().fromJson(output, JsonObject.class);
            //System.out.println(jobj.getAsJsonObject().toString());

            JsonArray jsonList = new JsonArray();
            jsonList = jobj.get("list").getAsJsonArray();

            // Временная зона выбранного города
            JsonObject jsonCity = new JsonObject();
            jsonCity = jobj.get("city").getAsJsonObject();

            int timeZone = jsonCity.get("timezone").getAsInt();

            for (int i = 0; i < 8; i++) {

                /*JsonObject jsonMain = new JsonObject();
                jsonMain = jsonList.get(i).getAsJsonObject();*/

                JsonObject jsonMain = jsonList.get(i).getAsJsonObject();

                /*JsonObject temp = new JsonObject();
                temp = jsonMain.get("main").getAsJsonObject();*/

                JsonObject temp = jsonMain.get("main").getAsJsonObject();

                //int a = temp.get("temp").getAsInt(); // Проще, но с большей погрешностью
                int a = (int) Math.round(temp.get("temp").getAsDouble());
                int b = temp.get("humidity").getAsInt();

                if (jsonMain.get("dt_txt").toString().contains(gmt(timeZone, "09:00:00"))) {
                    temp_9.setText(String.valueOf(a));
                    humidity_9.setText(String.valueOf(b));
                }
                if (jsonMain.get("dt_txt").toString().contains(gmt(timeZone, "12:00:00"))) {
                    temp_12.setText(String.valueOf(a));
                    humidity_12.setText(String.valueOf(b));
                }
                if (jsonMain.get("dt_txt").toString().contains(gmt(timeZone, "15:00:00"))) {
                    temp_15.setText(String.valueOf(a));
                    humidity_15.setText(String.valueOf(b));
                }
                if (jsonMain.get("dt_txt").toString().contains(gmt(timeZone, "18:00:00"))) {
                    temp_18.setText(String.valueOf(a));
                    humidity_18.setText(String.valueOf(b));
                }
                if (jsonMain.get("dt_txt").toString().contains(gmt(timeZone, "21:00:00"))) {
                    temp_21.setText(String.valueOf(a));
                    humidity_21.setText(String.valueOf(b));
                }
                if (jsonMain.get("dt_txt").toString().contains(gmt(timeZone, "00:00:00"))) {
                    temp_24.setText(String.valueOf(a));
                    humidity_24.setText(String.valueOf(b));
                }
                if (jsonMain.get("dt_txt").toString().contains(gmt(timeZone, "03:00:00"))) {
                    temp_3.setText(String.valueOf(a));
                    humidity_3.setText(String.valueOf(b));
                }
                if (jsonMain.get("dt_txt").toString().contains(gmt(timeZone, "06:00:00"))) {
                    temp_6.setText(String.valueOf(a));
                    humidity_6.setText(String.valueOf(b));
                }

            }

        } else {
            // Если город не выбран, то ничего не отображать
            cityNameLabel.setText("");

            temp_9.setText("");
            humidity_9.setText("");

            temp_12.setText("");
            humidity_12.setText("");

            temp_15.setText("");
            humidity_15.setText("");

            temp_18.setText("");
            humidity_18.setText("");

            temp_21.setText("");
            humidity_21.setText("");

            temp_24.setText("");
            humidity_24.setText("");

            temp_3.setText("");
            humidity_3.setText("");

            temp_6.setText("");
            humidity_6.setText("");
        }

    }

    @FXML
    private void handleDeleteCity() {

        int selectedIndex = cityTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            cityTable.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Выбор?");
            alert.setHeaderText("Нет выбранного города");
            alert.setContentText("Пожалуйста, выберите город для удаления.");

            alert.showAndWait();
        }

    }

    @FXML
    private void handleNewCity() {

        City tempCity = new City();
        boolean okClicked = mainApp.showCityEditDialog(tempCity);
        if (okClicked) {
            mainApp.getCityData().add(tempCity);
        }

    }

    @FXML
    private void handleEditCity() {

        City selectedCity = cityTable.getSelectionModel().getSelectedItem();
        if (selectedCity != null) {
            boolean okClicked = mainApp.showCityEditDialog(selectedCity);
            if (okClicked) {
                showCityDetails(selectedCity);
            }

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Выбор?");
            alert.setHeaderText("Нет выбранного города");
            alert.setContentText("Пожалуйста, выберите город из таблицы.");

            alert.showAndWait();
        }

    }

}