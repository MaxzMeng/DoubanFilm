package me.maxandroid.doubanfilm.api.city;

import java.util.List;

public class Province extends CityModel {
    private List<City> cities;

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
