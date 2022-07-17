package com.demo.demo;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

// Вспомогательный класс для обёртывания списка адресатов.
// Используется для сохранения списка адресатов в XML.
@XmlRootElement(name = "cities")
public class CityListWrapper {

    private List<City> cities;

    @XmlElement(name = "city")
    public List<City> getCity() {
        return cities;
    }

    public void setCity(List<City> cities) {
        this.cities = cities;
    }

}
