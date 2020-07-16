package com.city.info.bot.service;

import com.city.info.bot.model.City;

import java.util.List;
import java.util.Optional;

public interface CityService {

    List<City> getAllCities();

    Optional<City> getCityByName(String cityName);

    void saveCity(City city);

    void updateCityInfo(String info, String name);

    void removeCity(String cityToRemove);
}
