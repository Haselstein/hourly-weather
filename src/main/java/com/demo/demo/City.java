package com.demo.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class City {

    private final StringProperty cityName;

    public City() {
        this(null);
    }


    public City(String cityName){
        this.cityName = new SimpleStringProperty(cityName);
    }


    public String getCityName() {
        return cityName.get();
    }

    public void setCityName(String cityName) {
        this.cityName.set(cityName);
    }

    public StringProperty cityNameProperty() {
        return cityName;
    }

}
