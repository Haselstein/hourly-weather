package com.demo.demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private static ObservableList<City> cityData = FXCollections.observableArrayList();
    //private ObservableList<City> cityData = FXCollections.observableArrayList();

    public MainApp() {
        load();
    }

    /*public ObservableList<City> getCityData() {
        return cityData;
    }*/
    public static ObservableList<City> getCityData() {
        return cityData;
    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Погодник");

        String root = System.getProperty("user.dir");
        String FileName="weather.png";
        String filePath = root+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"images"+File.separator+FileName;

        this.primaryStage.getIcons().add(new Image(filePath));

        //this.primaryStage.getIcons().add(new Image("C:/Users/verho/IdeaProjects/demo/src/main/resources/images/weather.png"));

        initRootLayout();

        showCityOverview();

    }


    public void initRootLayout() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void showCityOverview() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("CityOverview.fxml"));
            AnchorPane cityOverview = loader.load();

            // Помещение сведений о погоде в центр корневого макета
            rootLayout.setCenter(cityOverview);

            // Доступ контроллеру к главному приложению
            CityOverviewController controller = loader.getController();
            controller.setMainApp(this);

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }


    public boolean showCityEditDialog(City city) {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("CityEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Создаёт диалоговое окно Stage
            Stage dialogStage = new Stage();
            dialogStage.setResizable(false);
            dialogStage.setTitle("Изменение города");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передача информации в контроллер
            CityEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCity(city);

            // Отображение диалогового окна и ожидание закрытия приложения
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }


    public static void load() {
        // Загрузка списка городов из файла

        String root = System.getProperty("user.dir");
        String FileName="save.txt";
        String filePath = root+File.separator+FileName;

        try(FileReader reader = new FileReader(filePath)) {

            Scanner scan = new Scanner(reader);
            int i = 1;

            while (scan.hasNextLine()) {
                cityData.add(new City(scan.nextLine()));
                i++;
            }
        }

        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static void save() {
        // Сохранение списка городов в файл

        String root = System.getProperty("user.dir");
        String FileName="save.txt";
        String filePath = root+File.separator+FileName;

        try(FileWriter writer = new FileWriter(filePath)) {

            for (City city : getCityData())
                writer.write(city.getCityName() + "\n");

            writer.flush();

        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }

    }


    public static void main(String[] args) {
        launch(args);
        save();
    }

}