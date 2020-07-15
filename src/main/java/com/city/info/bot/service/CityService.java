package com.city.info.bot.service;

import com.city.info.bot.model.City;

import java.util.List;
import java.util.Optional;

public interface CityService {

    List<City> getAllCities();

    City saveCity(City city);

    Optional<City> getCityByName(String cityName);

    void updateCityInfo(String info, String name);
}
